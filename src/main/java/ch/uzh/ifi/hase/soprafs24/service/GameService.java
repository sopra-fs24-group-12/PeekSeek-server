package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.google.StreetviewImageDownloader;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.SummaryRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotingPostDTO;


import ch.uzh.ifi.hase.soprafs24.websocket.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.lang.Math;

//TODO: Handle case with less than 3 participants remaining
//TODO: authorize participants/admin
//TODO: submit current location of player if not submitted


@Service
@Transactional
public class GameService {
    private final WebsocketService websocketService;
    private final SummaryRepository summaryRepository;

    @Autowired
    public GameService(WebsocketService websocketService,
                       @Qualifier("summaryRepository") SummaryRepository summaryRepository) {
        this.websocketService = websocketService;
        this.summaryRepository = summaryRepository;
    }

    public Round getRoundInformation(String token, Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        authorizeGameParticipant(game, token);

        return game.getRounds().get(game.getCurrentRound());
    }

    public Game getGameInformation(String token, Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        authorizeGameParticipant(game, token);

        return game;
    }

    // TODO: adjust participant class to include distribution of points and adjust DTO
    public List<Participant> getLeaderboard(String token, Long gameId) {  // get a list of all participants sorted by score
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        authorizeGameParticipant(game, token);

        List<Participant> participants = new ArrayList<>(game.getParticipants().values());
        participants.sort(Comparator.comparing(Participant::getScore).reversed()); //TODO: Check in which  direction it sorts
        return participants;
    }

    public Long startGame(Lobby lobby) {
        if (lobby.getJoinedParticipants() < 3) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You need at least 3 participants to start the game");
        }

        List<String> quests = lobby.getQuests();
        if (quests == null || quests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You need to have at least one quest to start the game");
        }

        Game createdGame = new Game();

        List<Round> rounds = new ArrayList<>(quests.size());
        for (String quest : quests) {
            Round round = new Round();
            round.setId(Game.rounds_count++);
            round.setQuest(quest);
            round.setRoundTime(lobby.getRoundDurationSeconds());
            round.setRemainingSeconds(lobby.getRoundDurationSeconds());
            rounds.add(round);
        }

        createdGame.setRounds(rounds);
        createdGame.setRoundDurationSeconds(lobby.getRoundDurationSeconds());
        createdGame.setAdminId(lobby.getAdminId());
        createdGame.setGameLocation(lobby.getGameLocation());
        createdGame.setNumberRounds(rounds.size());
        createdGame.setLobbyPassword(lobby.getPassword());

        Map<String, Participant> participants = new HashMap<>(lobby.getParticipants());
        createdGame.setParticipants(participants);

        lobby.resetLobby();

        GameRepository.addGame(createdGame);

        startNextRound(createdGame.getId());

        return createdGame.getId();
    }

    public void startNextRound(Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        if (game.getCurrentRound() != -1) {
            Round previousRound = game.getRounds().get(game.getCurrentRound());
            if (previousRound.getRoundStatus() != RoundStatus.FINISHED) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The round is not over yet");
            }

            Map<String, Participant> participants = game.getParticipants();

            for (Participant participant : participants.values()) {
                participant.setHasSubmitted(false);
            }
        }

        Integer currentRoundIdx = game.getCurrentRound();
        Integer numberRounds = game.getNumberRounds();

        if (currentRoundIdx == numberRounds - 1) {
            Long summaryId = generateSummary(game);
            websocketService.sendMessage("/topic/games/" + gameId, new GameEndDTO(summaryId));
            game.setGameStatus(GameStatus.SUMMARY);
            GameRepository.deleteGame(gameId);
            return;
        }

        game.setCurrentRound(currentRoundIdx + 1);
        game.getRounds().get(game.getCurrentRound()).setRoundStatus(RoundStatus.PLAYING);

        Round round = game.getRounds().get(game.getCurrentRound());

        websocketService.sendMessage("/topic/games/" + gameId, new NextRoundDTO());

        startTimer(round, gameId);
    }


    private Long generateSummary(Game game) {
        List<Quest> winningSubmissions = new ArrayList<>();

        Summary summary = new Summary();
        summary.setCityName(game.getGameLocation());
        summary.setRoundsPlayed(game.getNumberRounds());
        summary.setPassword(game.getLobbyPassword());
        summary = summaryRepository.save(summary);
        summaryRepository.flush();

        for (Round round : game.getRounds()) {
            Quest quest = new Quest();
            quest.setDescription(round.getQuest());
            quest.setLink(generateSubmissionLink(round.getWinningSubmission().getSubmittedLocation().getLat(),
                    round.getWinningSubmission().getSubmittedLocation().getLng()));
            quest.setName(game.getParticipantByToken(round.getWinningSubmission().getToken()).getUsername());
            quest.setSummary(summary);
            quest.setImage(round.getWinningSubmission().getImage());
            winningSubmissions.add(quest);
        }

        summary.setQuests(winningSubmissions);
        summary = summaryRepository.save(summary);
        summaryRepository.flush();

        return summary.getId();
    }

    private String generateSubmissionLink(String lat, String lng) {
        String base = "https://www.google.com/maps/place/";
        base += lat + "," + lng;
        return base;
    }

    public void postSubmission(Long gameId, String token, SubmissionPostDTO submissionPostDTO) throws IOException {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        authorizeGameParticipant(game, token);

        Participant participant = game.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The token does not exist");
        }

        if (participant.getHasSubmitted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already submitted in this round");
        }

        int submissionTime = getSubmissionTime(participant, game);

        Submission submission = new Submission();
        participant.setHasSubmitted(true);

        SubmissionData submissionData = new SubmissionData();
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setLat(submissionPostDTO.getPitch());
        submissionData.setLng(submissionPostDTO.getLng());

        byte[] image = StreetviewImageDownloader.retrieveStreetViewImage(submissionData);

        submission.setImage(image);
        submission.setSubmissionTimeSeconds(submissionTime);
        submission.setSubmittedLocation(submissionData);
        submission.setToken(participant.getToken());

        Round currentRound = game.getRounds().get(game.getCurrentRound());
        currentRound.addSubmission(submission);
    }

    public void postVoting(Long gameId, String token, VotingPostDTO votingPostDTO) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        authorizeGameParticipant(game, token);

        Round round = game.getRounds().get(game.getCurrentRound());
        if (round.getRoundStatus() != RoundStatus.VOTING) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The current round is not in the voting phase");
        }

        Participant participant = game.getParticipantByToken(token);
        for(Long submissionId : votingPostDTO.getVotes().keySet()){
            Submission submission = round.getSubmissions().get(submissionId);
            // TODO: change code if automatic submission implemented
            if (submission == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The submission with this ID does not exist");
            }
            if (submission.getToken().equals(participant.getToken())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot vote for your own submission");
            }
            if (Objects.equals(votingPostDTO.getVotes().get(submissionId), "winner")) {
                submission.setNumberVotes(submission.getNumberVotes() + 1);
            } else if (Objects.equals(votingPostDTO.getVotes().get(submissionId), "ban")){
                submission.setNumberBanVotes(submission.getNumberBanVotes() + 1);
            }
        }
    }

    private static int getSubmissionTime(Participant participant, Game game) {
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }

        Round round = game.getRounds().get(game.getCurrentRound());
        if (round.getRoundStatus() != RoundStatus.PLAYING) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The current round is not in the submission phase");
        }

        if (participant.getHasSubmitted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already submitted this round");
        }

        return round.getRoundTime() - round.getRemainingSeconds();
    }

    private void setWinningSubmission(Round round, List<Submission> submissions) {
        submissions.sort(Comparator.comparing(Submission::getNumberVotes).reversed()); //TODO: Check sorting order
        Submission winningSubmission = submissions.get(0);
        List<Submission> winningSubmissions = new ArrayList<>();
        winningSubmissions.add(winningSubmission);
        if (submissions.size() > 1){
            for (Submission submission : submissions){
                if (Objects.equals(submission.getNumberVotes(), winningSubmission.getNumberVotes())){
                    winningSubmissions.add(submission);
                }
            }
            winningSubmissions.sort(Comparator.comparing(Submission::getSubmissionTimeSeconds).reversed());
        }
        round.setWinningSubmission(winningSubmissions.get(0));
    }

    private int calculatePoints(Long gameId, Round round, Submission submission, int placement) {
        int totalPoints = 0;
        int timebonusPoints = 250;      // basis which is then multiplied with a factor < 1
        int placementPoints = 250;      // basis which is then multiplied with a factor < 1
        int votingPoints = 500;         // basis which is then multiplied with a factor < 1
        Game game = GameRepository.getGameById(gameId);
        Participant participant = game.getParticipants().get(submission.getToken());

        if(submission.getNoSubmission()){   // if the participant clicked "Can`t find that", they get 0 points
            return 0;
        }
        if(submission.getNumberBanVotes() > (round.getSubmissions().size() - 1) / 2){   // if the submission has more than half of the votes to be banned, they get 0 points
            return 0;
        }

        timebonusPoints *= (submission.getSubmissionTimeSeconds() / round.getRoundTime()); // timebonus is 0 if submissionTime == roundTime
        placementPoints *= ((round.getSubmissions().size() - placement) / round.getSubmissions().size()) + 0.25;    // +0.25 to avoid 0 points for the last place
        votingPoints *= (submission.getNumberVotes() / (round.getSubmissions().size() - 1));    // -1 because the participant cannot vote for themselves
        if(submission == round.getWinningSubmission()){          // if it is the winning submission, the voting points are multiplied by 1.5
            votingPoints *= 1.5;
            participant.setWinningSubmissions(participant.getWinningSubmissions() + 1);
            participant.setStreak(participant.getStreak() + 1);
        } else {
            participant.setStreak(0);
        }
        totalPoints = timebonusPoints + placementPoints + votingPoints;
        int streak = participant.getStreak();
        if(streak >= 2){          // streak bonus
            totalPoints *= Math.pow((1 + streak * 0.1), 1.3);   // the streak bonus is calculated by the formula (1 + streak * 0.1) ^ 1.3
        }
        participant.setScore(participant.getScore() + totalPoints);
        return totalPoints;
    }

    private void awardPoints(Round round, Long gameId) {
        Map<Long, Submission> submissionsMap = round.getSubmissions();
        List<Submission> submissions = new ArrayList<>(submissionsMap.values());
        setWinningSubmission(round, submissions);
        submissions.sort(Comparator.comparing(Submission::getSubmissionTimeSeconds));
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);
            submission.setAwardedPoints(calculatePoints(gameId, round, submission, i));
        }
    }

    private void startTimer(Round round, Long gameId) {
        Timer timer = new Timer();
        int timePerRound = round.getRoundTime();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                round.setRemainingSeconds(round.getRoundTime());
                timer.cancel();
                handleNextPhase(round, gameId);
            }
        }, timePerRound * 1000L);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                round.setRemainingSeconds(round.getRemainingSeconds() - 1);
                websocketService.sendMessage("/topic/games/" + gameId + "/timer",
                        new SecondsRemainingDTO(round.getRemainingSeconds()));
            }
        }, 0, 1000);
    }

    private void startVoting(Round round, Long gameId) {
        handleMissingSubmissions(round, gameId);
        websocketService.sendMessage("/topic/games/" + gameId, new StartVotingDTO());
        round.setRoundStatus(RoundStatus.VOTING);
        startTimer(round, gameId);
    }

    private void startSummary(Round round, Long gameId) {
        websocketService.sendMessage("/topic/games/" + gameId, new ShowSummaryDTO());
        awardPoints(round, gameId);
        round.setRoundStatus(RoundStatus.SUMMARY);
        startTimer(round, gameId);
    }

    private void endRound(Round round, Long gameId) {
        round.setRoundStatus(RoundStatus.FINISHED);
        startNextRound(gameId);
    }

    private void handleNextPhase(Round round, Long gameId) {
        RoundStatus status = round.getRoundStatus();
        if (status == RoundStatus.PLAYING) {
            startVoting(round, gameId);
        }
        else if (status == RoundStatus.VOTING) {
            startSummary(round, gameId);
        }
        else if (status == RoundStatus.SUMMARY) {
            endRound(round, gameId);
        }
    }

    private void handleMissingSubmissions(Round round, Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        Map<String, Participant> participants = game.getParticipants();

        for (Participant participant : participants.values()) {
            if (!participant.getHasSubmitted()) {
                SubmissionData submissionData = new SubmissionData();
                submissionData.setLat("47.3768866");
                submissionData.setLng("8.541694");
                submissionData.setPitch("50");
                submissionData.setHeading("50");

                Submission emptySubmission = new Submission();
                try {
                    emptySubmission.setImage(StreetviewImageDownloader.retrieveStreetViewImage(submissionData));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                emptySubmission.setToken(participant.getToken());
                emptySubmission.setNoSubmission(true);
                emptySubmission.setSubmittedLocation(submissionData);
                round.addSubmission(emptySubmission);
            }
        }
    }

    private void authorizeGameParticipant(Game game, String token) {
        Participant participant = game.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        }
    }
}
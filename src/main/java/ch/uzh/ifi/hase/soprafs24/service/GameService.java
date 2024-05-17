package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
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

import java.util.*;


@Service
@Transactional
public class GameService {
    private final WebsocketService websocketService;
    private final SummaryRepository summaryRepository;

    private final Map<Long, Timer> inactivityTimers = new HashMap<>();
    private final Map<Long, Timer> gameTimers = new HashMap<>();

    private static final String websocketTopicAddress = "/topic/games/";

    @Autowired
    public GameService(WebsocketService websocketService,
                       @Qualifier("summaryRepository") SummaryRepository summaryRepository) {
        this.websocketService = websocketService;
        this.summaryRepository = summaryRepository;
    }

    public Round getRoundInformation(String token, Long gameId) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        return game.getRounds().get(game.getCurrentRound());
    }

    public Game getGameInformation(String token, Long gameId) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        return game;
    }

    public List<Participant> getLeaderboard(String token, Long gameId) {  // get a list of all participants sorted by score
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        List<Participant> participants = new ArrayList<>(game.getParticipants().values());
        participants.sort(Comparator.comparing(Participant::getScore).reversed());
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
            round.setId(Game.getRoundsCount() + 1);
            Game.setRoundsCount(Game.getRoundsCount() + 1);
            round.setQuest(quest);
            round.setRoundTime(lobby.getRoundDurationSeconds());
            round.setRemainingSeconds(lobby.getRoundDurationSeconds());
            round.setGeoCodingData(lobby.getGameLocationCoordinates());
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
        createdGame.setActiveParticipants(createdGame.getParticipants().size());

        lobby.resetLobby();

        GameRepository.addGame(createdGame);

        startInactivityTimer(createdGame);

        startNextRound(createdGame.getId());

        return createdGame.getId();
    }

    public void updateActiveStatus(Long gameId, String token) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        game.updateActivityTime(token);
    }

    private void startInactivityTimer(Game game) {
        game.initializeActivityTime();
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<String> inactiveTokens = game.removeInactiveParticipants(5000);
                for (String token : inactiveTokens) {
                    leaveGame(game.getId(), token);
                }
            }
        };
        timer.schedule(task, 2000, 5000);

        inactivityTimers.put(game.getId(), timer);
    }

    private void stopInactivityTimer(Long gameId) {
        Timer timer = inactivityTimers.get(gameId);
        if (timer != null) {
            timer.cancel();
            inactivityTimers.remove(gameId);
        }
    }

    public void startNextRound(Long gameId) {
        Game game = getSpecificGame(gameId);

        if (game.getCurrentRound() != -1) {
            Round previousRound = game.getRounds().get(game.getCurrentRound());
            if (previousRound.getRoundStatus() != RoundStatus.FINISHED) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The round is not over yet");
            }

            Map<String, Participant> participants = game.getParticipants();

            for (Participant participant : participants.values()) {
                participant.setHasSubmitted(false);
                participant.setHasVoted(false);
                participant.setPointsThisRound(0);
            }
        }

        Integer currentRoundIdx = game.getCurrentRound();
        Integer numberRounds = game.getNumberRounds();

        if (currentRoundIdx == numberRounds - 1) {
            endGame(game, game.getId());
            return;
        }

        game.setCurrentRound(currentRoundIdx + 1);

        Round round = game.getRounds().get(game.getCurrentRound());

        round.setRoundStatus(RoundStatus.PLAYING);

        websocketService.sendMessage(websocketTopicAddress + gameId, new NextRoundDTO());

        startTimer(round, gameId);
    }

    private void endGame(Game game, Long gameId) {
        Long summaryId = generateSummary(game);
        websocketService.sendMessage(websocketTopicAddress + gameId, new GameEndDTO(summaryId));
        game.setGameStatus(GameStatus.SUMMARY);
        Timer timer = gameTimers.get(gameId);
        if (timer != null) {
            timer.cancel();
            gameTimers.remove(game.getId());
        }
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                stopInactivityTimer(gameId);
                GameRepository.deleteGame(gameId);
            }
        }, 2000);

    }

    public Long generateSummary(Game game) {
        List<Quest> winningSubmissions = new ArrayList<>();

        int roundsPlayed = 0;

        for (Round round : game.getRounds()) {
            if (round.getRoundStatus() == RoundStatus.FINISHED) {
                roundsPlayed++;
            }
        }

        Summary summary = new Summary();
        summary.setCityName(game.getGameLocation());
        summary.setRoundsPlayed(roundsPlayed);
        summary.setPassword(game.getLobbyPassword());
        summary = summaryRepository.save(summary);
        summaryRepository.flush();

        for (Round round : game.getRounds()) {
            if (round.getRoundStatus() == RoundStatus.FINISHED && !round.getWinningSubmission().getNoSubmission()) {
                Quest quest = new Quest();
                quest.setDescription(round.getQuest());
                quest.setLink(generateSubmissionLink(round.getWinningSubmission().getSubmittedLocation().getLat(),
                        round.getWinningSubmission().getSubmittedLocation().getLng()));
                quest.setName(game.getParticipantByToken(round.getWinningSubmission().getToken()).getUsername());
                quest.setSummary(summary);
                quest.setNoSubmission(round.getWinningSubmission().getNoSubmission());
                quest.setLat(round.getWinningSubmission().getSubmittedLocation().getLat());
                quest.setLng(round.getWinningSubmission().getSubmittedLocation().getLng());
                winningSubmissions.add(quest);
            }
        }

        summary.setResLatNe(game.getRounds().get(0).getGeoCodingData().getResLatNe());
        summary.setResLngNe(game.getRounds().get(0).getGeoCodingData().getResLngNe());
        summary.setResLatSw(game.getRounds().get(0).getGeoCodingData().getResLatSw());
        summary.setResLngSw(game.getRounds().get(0).getGeoCodingData().getResLngSw());
        summary.setLat(game.getRounds().get(0).getGeoCodingData().getLat());
        summary.setLng(game.getRounds().get(0).getGeoCodingData().getLng());
        summary.setQuests(winningSubmissions);
        summary = summaryRepository.save(summary);
        summaryRepository.flush();

        return summary.getId();
    }

    public String generateSubmissionLink(String lat, String lng) {
        String base = "https://www.google.com/maps/place/";
        base += lat + "," + lng;
        return base;
    }

    public void postSubmission(Long gameId, String token, SubmissionPostDTO submissionPostDTO) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        Participant participant = game.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The token does not exist");
        }

        if (participant.getHasSubmitted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already submitted in this round");
        }

        Round currentRound = game.getRounds().get(game.getCurrentRound());
        if (currentRound.getRoundStatus() != RoundStatus.PLAYING && isNotWithinBufferPeriod(currentRound)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The game is not in the submission phase");
        }


        int submissionTime = getSubmissionTime(participant, currentRound);

        Submission submission = new Submission();
        participant.setHasSubmitted(true);

        currentRound.setParticipantsDone(currentRound.getParticipantsDone() + 1);

        SubmissionData submissionData = new SubmissionData();
        submissionData.setLat(submissionPostDTO.getLat());
        submissionData.setLng(submissionPostDTO.getLng());
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setNoSubmission(submissionPostDTO.getNoSubmission());


        submission.setId(Round.getSubmissionCount() + 1);
        Round.setSubmissionCount(Round.getSubmissionCount() + 1);
        submission.setSubmissionTimeSeconds(submissionTime);
        submission.setSubmittedLocation(submissionData);
        submission.setToken(participant.getToken());
        submission.setUsername(participant.getUsername());
        submission.setNoSubmission(submissionPostDTO.getNoSubmission());

        currentRound.addSubmission(submission);

        if (Objects.equals(currentRound.getParticipantsDone(), game.getActiveParticipants()) && currentRound.getRemainingSeconds() > 5) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    endTimerPrematurely(currentRound, gameId);
                    timer.cancel();
                }
            }, 3000);
        }
    }

    public void leaveGame(Long gameId, String token) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        Participant participant = game.getParticipantByToken(token);

        if (participant.getLeftGame()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already left the game");
        }

        participant.setLeftGame(true);
        game.setActiveParticipants(game.getActiveParticipants() - 1);

        if (game.getActiveParticipants() < 3) {
            endGame(game, gameId);
        }

        websocketService.sendMessage(websocketTopicAddress + gameId, new ParticipantLeftDTO(participant.getUsername()));
    }

    public void postVoting(Long gameId, String token, VotingPostDTO votingPostDTO) {
        Game game = getSpecificGame(gameId);

        authorizeGameParticipant(game, token);

        Round round = game.getRounds().get(game.getCurrentRound());
        if (round.getRoundStatus() != RoundStatus.VOTING && isNotWithinBufferPeriod(round)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The current round is not in the voting phase");
        }

        Participant participant = game.getParticipantByToken(token);

        if (participant.getHasVoted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already voted in this round");
        }

        for (Long submissionId : votingPostDTO.getVotes().keySet()){
            Submission submission = round.getSubmissions().get(submissionId);
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
        participant.setHasVoted(true);
        round.setParticipantsDone(round.getParticipantsDone() + 1);

        if (Objects.equals(round.getParticipantsDone(), game.getActiveParticipants()) && round.getRemainingSeconds() > 5) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    endTimerPrematurely(round, gameId);
                    timer.cancel();
                }
            }, 3000);
        }
    }

    public static int getSubmissionTime(Participant participant, Round round) {
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }

        return round.getRoundTime() - round.getRemainingSeconds();
    }

    public void setWinningSubmission(Round round, List<Submission> submissions) {
        submissions.sort(Comparator.comparing(Submission::getNumberVotes).reversed().thenComparing(Submission::getSubmissionTimeSeconds));
        Submission winningCandidate = submissions.get(0);
        round.setWinningSubmission(winningCandidate);
        if (winningCandidate.getNumberVotes() == 0 && winningCandidate.getNumberBanVotes() > (round.getSubmissions().size() - 1) / 2) {
            winningCandidate.setNoSubmission(true);
        }
    }

    public int calculatePoints(Long gameId, Round round, Submission submission, int placement) {
        int totalPoints = 0;
        int timebonusPoints = 250;      // basis which is then multiplied with a factor < 1
        int placementPoints = 250;      // basis which is then multiplied with a factor < 1
        int votingPoints = 500;         // basis which is then multiplied with a factor < 1
        Game game = GameRepository.getGameById(gameId);
        Participant participant = game.getParticipants().get(submission.getToken());

        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }

        if (submission.getNoSubmission()) {   // if the participant clicked "Can`t find that", they get 0 points
            return 0;
        }
        if (submission.getNumberBanVotes() > (round.getSubmissions().size() - 1) / 2) {   // if the submission has more than half of the votes to be banned, they get 0 points
            return 0;
        }

        timebonusPoints *= (double)(round.getRoundTime() - submission.getSubmissionTimeSeconds()) / (double)round.getRoundTime(); // timebonus is 0 if submissionTime == roundTime
        placementPoints *= ((double)(round.getSubmissions().size() - placement) / (double)round.getSubmissions().size()) + 0.25;    // +0.25 to avoid 0 points for the last place
        votingPoints *= (double)submission.getNumberVotes() / (double)(round.getSubmissions().size() - 1);    // -1 because the participant cannot vote for themselves


        if (submission == round.getWinningSubmission()) {          // if it is the winning submission, the voting points are multiplied by 1.5
            votingPoints = (int) ((double) votingPoints * 1.5);
            participant.setWinningSubmissions(participant.getWinningSubmissions() + 1);
            participant.setStreak(participant.getStreak() + 1);
        } else {
            participant.setStreak(0);
        }

        totalPoints = timebonusPoints + placementPoints + votingPoints;
        int streak = participant.getStreak();
        if (streak >= 2) {          // streak bonus
            totalPoints *= Math.pow((1 + (double)streak * 0.1), 1.3);   // the streak bonus is calculated by the formula (1 + streak * 0.1) ^ 1.3
        }
        participant.setPointsThisRound(totalPoints);
        participant.setScore(participant.getScore() + totalPoints);

        return totalPoints;
    }

    private void awardPoints(Round round, Long gameId) {
        Map<Long, Submission> submissionsMap = round.getSubmissions();
        List<Submission> submissions = new ArrayList<>(submissionsMap.values());
        setWinningSubmission(round, submissions);
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);
            submission.setAwardedPoints(calculatePoints(gameId, round, submission, i + 1));
        }
    }

    private void startTimer(Round round, Long gameId) {
        Timer timer = new Timer();
        gameTimers.put(gameId, timer);

        int timeInCurrentPhase = (round.getRoundStatus() == RoundStatus.SUMMARY) ? round.getSummaryTime() : round.getRoundTime();
        round.setRemainingSeconds(timeInCurrentPhase);

        round.setLastPhaseChangeTime(System.currentTimeMillis());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handleNextPhase(round, gameId);
                timer.cancel();
            }
        }, timeInCurrentPhase * 1000L);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                websocketService.sendMessage(websocketTopicAddress + gameId + "/timer",
                        new SecondsRemainingDTO(round.getRemainingSeconds()));
                round.setRemainingSeconds(round.getRemainingSeconds() - 1);
            }
        }, 0, 1000);
    }

    public void endTimerPrematurely(Round round, Long gameId) {
        Timer timer = gameTimers.get(gameId);
        if (timer != null) {
            timer.cancel();
            handleNextPhase(round, gameId);
        }
    }

    private void startVoting(Round round, Long gameId) {
        handleMissingSubmissions(round, gameId);
        shuffleSubmissions(round);
        round.setRoundStatus(RoundStatus.VOTING);
        websocketService.sendMessage(websocketTopicAddress + gameId, new StartVotingDTO());
        startTimer(round, gameId);
    }

    private void startSummary(Round round, Long gameId) {
        awardPoints(round, gameId);
        round.setRoundStatus(RoundStatus.SUMMARY);
        websocketService.sendMessage(websocketTopicAddress + gameId, new ShowSummaryDTO());
        startTimer(round, gameId);
    }

    private void endRound(Round round, Long gameId) {
        round.setRoundStatus(RoundStatus.FINISHED);
        startNextRound(gameId);
    }

    private void handleNextPhase(Round round, Long gameId) {
        round.setParticipantsDone(0);
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

    public void handleMissingSubmissions(Round round, Long gameId) {
        Game game = getSpecificGame(gameId);

        Map<String, Participant> participants = game.getParticipants();

        for (Participant participant : participants.values()) {
            if (!participant.getHasSubmitted() && !participant.getLeftGame()) {
                SubmissionData submissionData = new SubmissionData();
                submissionData.setLat("47.3768866");
                submissionData.setLng("8.541694");
                submissionData.setPitch("50");
                submissionData.setHeading("50");

                Submission emptySubmission = new Submission();

                emptySubmission.setId(Round.getSubmissionCount() + 1);
                Round.setSubmissionCount(Round.getSubmissionCount() + 1);
                emptySubmission.setSubmissionTimeSeconds(round.getRoundTime());
                emptySubmission.setToken(participant.getToken());
                emptySubmission.setUsername(participant.getUsername());
                emptySubmission.setNoSubmission(true);
                emptySubmission.setSubmittedLocation(submissionData);
                round.addSubmission(emptySubmission);
            }
        }
    }

    private void shuffleSubmissions(Round round) {
        Collection<Submission> submissions = round.getSubmissions().values();

        List<Submission> validSubmissions = new ArrayList<>();
        List<Submission> invalidSubmissions = new ArrayList<>();

        for (Submission submission : submissions) {
            if (submission.getNoSubmission()) {
                invalidSubmissions.add(submission);
            } else {
                validSubmissions.add(submission);
            }
        }

        Collections.shuffle(validSubmissions);

        List<Submission> shuffledSubmissions = new ArrayList<>();
        shuffledSubmissions.addAll(validSubmissions);
        shuffledSubmissions.addAll(invalidSubmissions);

        round.setShuffledSubmissions(shuffledSubmissions);
    }

    public void authorizeGameParticipant(Game game, String token) {
        Participant participant = game.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        }
        if (participant.getLeftGame()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Participant has left the game");
        }
    }

    public Game getSpecificGame(Long id) {
        Game game = GameRepository.getGameById(id);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }
        return game;
    }

    private boolean isNotWithinBufferPeriod(Round round) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - round.getLastPhaseChangeTime()) > round.getBufferTime() * 1000L;
    }
}

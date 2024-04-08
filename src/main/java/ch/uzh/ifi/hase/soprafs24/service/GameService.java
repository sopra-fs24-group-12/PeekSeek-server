package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;

import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.lang.Math;

@Service
@Transactional
public class GameService {
    private final WebsocketService websocketService;

    @Autowired
    public GameService(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    public Round getRoundInformation(Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        return game.getRounds().get(game.getCurrentRound());
    }

    public Game getGameInformation(Long gameId) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }
        return game;
    }

    public Long startGame(Lobby lobby) {
        if (lobby.getJoinedParticipants() < 3) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You need at least 3 participants to start the game");
        }

        List<String> quests = lobby.getQuests();
        if (quests.isEmpty()) {
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

        Map<String, Participant> participants = new HashMap<>(lobby.getParticipants());
        createdGame.setParticipants(participants);

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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "All rounds have been played");
        }

        game.setCurrentRound(currentRoundIdx + 1);
        game.getRounds().get(game.getCurrentRound()).setRoundStatus(RoundStatus.PLAYING);

        Round round = game.getRounds().get(game.getCurrentRound());

        startTimer(round, gameId);
    }

    public void postSubmission(Long gameId, String token, SubmissionPostDTO submissionPostDTO) {
        Game game = GameRepository.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A game with this ID does not exist");
        }

        Participant participant = game.getParticipantByToken(token);
        int submissionTime = getSubmissionTime(participant, game);

        Submission submission = new Submission();
        participant.setHasSubmitted(true);

        SubmissionData submissionData = new SubmissionData();
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setLat(submissionPostDTO.getPitch());
        submissionData.setLng(submissionPostDTO.getLng());

        submission.setSubmissionTimeSeconds(submissionTime);
        submission.setSubmittedLocation(submissionData);
        submission.setToken(participant.getToken());

        Round currentRound = game.getRounds().get(game.getCurrentRound());
        currentRound.addSubmission(submission);
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
        submissions.sort(Comparator.comparing(Submission::getNumberVotes).reversed());
        Submission winningSubmission = submissions.get(0);
        round.setWinningSubmission(winningSubmission);
    }

    private int calculatePoints(Long gameId, Round round, Submission submission, int placement) {
        int totalPoints = 0;
        int timebonusPoints = 250;      // basis which is then multiplied with a factor < 1
        int placementPoints = 250;      // basis which is then multiplied with a factor < 1
        int votingPoints = 500;         // basis which is then multiplied with a factor < 1
        Game game = GameRepository.getGameById(gameId);
        Participant participant = game.getParticipants().get(submission.getToken());

        timebonusPoints *= (submission.getSubmissionTimeSeconds() / round.getRoundTime()); // timebonus is 0 if submissionTime == roundTime
        placementPoints *= ((round.getSubmissions().size() - placement) / round.getSubmissions().size()) + 0.25;    // +0.25 to avoid 0 points for the last place
        votingPoints *= (submission.getNumberVotes() / (round.getSubmissions().size() - 1));    // -1 because the participant cannot vote for themselves
        if(submission == round.getWinningSubmission()){          // if it is the winning submission, the voting points are multiplied by 1.5
            votingPoints *= 1.5;
            participant.setWinningSubmissions(participant.getWinningSubmissions() + 1);
            participant.setStreak(participant.getStreak() + 1);
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

    void startTimer(Round round, Long gameId) {
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
                websocketService.sendMessage("/games/" + gameId + "/timer", round.getRemainingSeconds());
            }
        }, 0, 1000);
    }

    private void startVoting(Round round, Long gameId) {
        round.setRoundStatus(RoundStatus.VOTING);
        startTimer(round, gameId);
    }

    private void startSummary(Round round, Long gameId) {
        awardPoints(round, gameId);
        round.setRoundStatus(RoundStatus.SUMMARY);
        startTimer(round, gameId);
    }

    private void endRound(Round round, Long gameId) {
        // TODO: set participants to has submitted false
        round.setRoundStatus(RoundStatus.FINISHED);
        // TODO: websocket message
        startNextRound(gameId);
    }

    private void handleNextPhase(Round round, Long gameId) {
        RoundStatus status = round.getRoundStatus();
        if (status == RoundStatus.PLAYING) {
            startVoting(round, gameId);
        } else if (status == RoundStatus.VOTING) {
            startSummary(round, gameId);
        } else if (status == RoundStatus.SUMMARY) {
            endRound(round, gameId);
        }
    }

}

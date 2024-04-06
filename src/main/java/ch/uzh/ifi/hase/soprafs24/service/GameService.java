package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.ParticipantRepository;
import ch.uzh.ifi.hase.soprafs24.repository.RoundRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Transactional
public class GameService {
    private final ParticipantRepository participantRepository;
    private final GameRepository gameRepository;
    private final WebsocketService websocketService;
    private final RoundRepository roundRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
                       @Qualifier("participantRepository") ParticipantRepository participantRepository,
                       @Qualifier("roundRepository") RoundRepository roundRepository,
                       WebsocketService websocketService) {
        this.participantRepository = participantRepository;
        this.gameRepository = gameRepository;
        this.websocketService = websocketService;
        this.roundRepository = roundRepository;
    }

    public Round getRoundInformation(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A game with this ID does not exist"));
        return game.getRounds().get(game.getCurrentRound());
    }

    public Game getGameInformation(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A game with this ID does not exist"));
        return game;
    }

    public void startNextRound(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A game with this ID does not exist"));
        if (game.getGameStatus() != GameStatus.SUMMARY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The round is not over yet");
        }

        List<Participant> participants = game.getParticipants();

        for (Participant participant : participants) {
            participant.setHasSubmitted(false);
        }

        game.setParticipants(participants);

        Integer currentRound = game.getCurrentRound();
        Integer numberRounds = game.getNumberRounds();
        if (currentRound == numberRounds - 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "All rounds have been played");
        }
        game.setCurrentRound(currentRound + 1);
        game.getRounds().get(currentRound).setRoundStatus(RoundStatus.PLAYING);
        game = gameRepository.save(game);
    }

    public void postSubmission(Long gameId, String token, SubmissionPostDTO submissionPostDTO) {
        Participant participant = participantRepository.findByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A game with this ID does not exist"));
        Round round = game.getRounds().get(game.getCurrentRound());
        if (round.getRoundStatus() != RoundStatus.PLAYING) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The current round is not in the submission phase");
        }
        if (participant.getHasSubmitted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already submitted this round");
        }

        int submissionTime = round.getRoundTime() - round.getRemainingSeconds();

        Submission submission = new Submission();
        game = gameRepository.save(game);

        participant.setHasSubmitted(true);
        participant = participantRepository.save(participant);

        SubmissionData submissionData = new SubmissionData();
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setLat(submissionPostDTO.getPitch());
        submissionData.setLng(submissionPostDTO.getLng());
        submission.setSubmissionTimeSeconds(submissionTime);
        submission.setRound(round.getId());
        submission.setSubmittedLocation(submissionData);
        submission.setParticipant(participant.getId());

        round.getSubmissions().add(submission);

        game = gameRepository.save(game);
    }

    void startTimer(Round round, Long gameId) {
        Timer timer = new Timer();
        int timePerRound = round.getRoundTime();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                round.setRemainingSeconds(round.getRoundTime());
                handleNextPhase(round, gameId);
            }
        }, timePerRound * 1000L);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                websocketService.sendMessage("/games/" + gameId + "/timer", round.getRemainingSeconds());
                round.setRemainingSeconds(round.getRemainingSeconds() - 1);
                roundRepository.save(round);
            }
        }, 0, 1000);
    }

    private void startVoting(Round round, Long gameId) {
        round.setRoundStatus(RoundStatus.VOTING);
        round = roundRepository.save(round);
        startTimer(round, gameId);
        // TODO: websocket message
    }

    private void startSummary(Round round) {
        round.setRoundStatus(RoundStatus.SUMMARY);
        round = roundRepository.save(round);
        // TODO: websocket message
    }

    private void endRound(Round round) {
        round.setRoundStatus(RoundStatus.FINISHED);
        round = roundRepository.save(round);
        // TODO: websocket message
    }

    private void handleNextPhase(Round round, Long gameId) {
        RoundStatus status = round.getRoundStatus();
        if (status == RoundStatus.PLAYING) {
            startVoting(round, gameId);
        } else if (status == RoundStatus.VOTING) {
            startSummary(round);
        } else if (status == RoundStatus.SUMMARY) {
            endRound(round);
        }
    }

}

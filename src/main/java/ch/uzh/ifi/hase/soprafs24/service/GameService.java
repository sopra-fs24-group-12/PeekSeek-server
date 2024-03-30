package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.ParticipantRepository;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.SubmissionPostDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class GameService {
    private final ParticipantRepository participantRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
                       @Qualifier("participantRepository") ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
        this.gameRepository = gameRepository;
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
        Integer currentRound = game.getCurrentRound();
        Integer numberRounds = game.getNumberRounds();
        if (currentRound == numberRounds - 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "All rounds have been played");
        }
        game.setCurrentRound(currentRound + 1);
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

        SubmissionData submissionData = new SubmissionData();
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setLat(submissionPostDTO.getPitch());
        submissionData.setLng(submissionPostDTO.getLng());
        submission.setSubmissionTimeSeconds(submissionTime);

        submission.setSubmittedLocation(submissionData);

        game = gameRepository.save(game);
    }
}

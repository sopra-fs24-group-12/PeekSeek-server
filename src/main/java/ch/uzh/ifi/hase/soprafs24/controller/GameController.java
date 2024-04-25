package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.rest.dto.RoundGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LeaderboardGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotingPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games/{id}/round")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RoundGetDTO getRoundInformation(@PathVariable Long id,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        Round currentRound = gameService.getRoundInformation(token, id);
        Game game = gameService.getGameInformation(token, id);
        return DTOMapper.INSTANCE.convertRoundToGameRoundGetDTO(currentRound,game);
    }

    @PostMapping("/games/{id}/nextRound")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startNextRound(@PathVariable Long id,
                               @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.startNextRound(id);
    }

    @PostMapping("/games/{id}/submission")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void postSubmission(@PathVariable Long id, @RequestBody SubmissionPostDTO submissionPostDTO,
                               @RequestHeader(value = "Authorization", required = false)
                               String token) throws IOException {
        gameService.postSubmission(id, token, submissionPostDTO);
    }
    
    @PostMapping("/games/{id}/voting")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void postVoting(@PathVariable Long id, @RequestBody VotingPostDTO votingPostDTO,
                               @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.postVoting(id, token, votingPostDTO);
    }

    @GetMapping("/games/{id}/winningSubmission")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SubmissionGetDTO getWinningSubmissionCurrent(@PathVariable Long id,
                                @RequestHeader(value = "Authorization", required = false) String token) {
        Round currentRound = gameService.getRoundInformation(token, id);
        Submission winningSubmission = currentRound.getWinningSubmission();
        return DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(winningSubmission);
    }

    @GetMapping("/games/{id}/submissions")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SubmissionGetDTO> getSubmissions(@PathVariable Long id,
                                @RequestHeader(value = "Authorization", required = false) String token) {
        List<SubmissionGetDTO> submissionGetDTOs = new ArrayList<>();
        Round currentRound = gameService.getRoundInformation(token, id);
        List<Submission> submissions = new ArrayList<>(currentRound.getSubmissions().values());
        for (Submission submission : submissions) {
            SubmissionGetDTO toAdd = DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(submission);
            toAdd.setUsername(""); // for now don't return username for API call on voting page
            submissionGetDTOs.add(toAdd);
        }
        return submissionGetDTOs;
    }

    @GetMapping("/games/{id}/winningSubmissions")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SubmissionGetDTO> getWinningSubmissions(@PathVariable Long id,
                                @RequestHeader(value = "Authorization", required = false) String token) {
        List<SubmissionGetDTO> submissionGetDTOs = new ArrayList<>();
        Game game = gameService.getGameInformation(token, id);
        List<Round> rounds = game.getRounds();
        for (Round round : rounds) {
            Submission winningSubmission = round.getWinningSubmission();
            submissionGetDTOs.add(DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(winningSubmission));
        }
        return submissionGetDTOs;
    }

    // TODO: rename LeaderboardGetDTO to make it more intuitive to store in list ("don't have a list of leaderboards")
    @GetMapping("/games/{id}/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LeaderboardGetDTO> getLeaderboard(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String token) {
        List<LeaderboardGetDTO> leaderboard = new ArrayList<>();
        List<Participant> participants = gameService.getLeaderboard(token, id);
        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            LeaderboardGetDTO toInsert = DTOMapper.INSTANCE.convertParticipantToLeaderboardGetDTO(participant);
            toInsert.setPosition(i + 1);
            leaderboard.add(toInsert);
        }
        return leaderboard;
    }

    @DeleteMapping("/games/{id}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveGame(@PathVariable Long id,
                           @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.leaveGame(id, token);
    }
}

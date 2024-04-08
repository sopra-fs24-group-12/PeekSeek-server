package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameRoundGetDTO;
<<<<<<< Updated upstream
import ch.uzh.ifi.hase.soprafs24.rest.dto.ParticipantGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionGetDTO;
=======
import ch.uzh.ifi.hase.soprafs24.rest.dto.LeaderboardGetDTO;
>>>>>>> Stashed changes
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotingPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

<<<<<<< Updated upstream
import java.io.IOException;
=======
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;
import java.util.ArrayList;

@RestController
public class GameController {
    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games/{id}/round")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameRoundGetDTO getRoundInformation(@PathVariable Long id) {
        Round currentRound = gameService.getRoundInformation(id);
        Game game = gameService.getGameInformation(id);
        return DTOMapper.INSTANCE.convertRoundToGameRoundGetDTO(currentRound,game);
    }

    @PostMapping("/games/{id}/nextRound")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startNextRound(@PathVariable Long id) {
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
    /*
    @PostMapping("/games/{id}/voting")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void postVoting(@PathVariable Long id, @RequestBody VotingPostDTO votingPostDTO,
                               @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.postVoting(id, token, votingPostDTO);
    }

<<<<<<< Updated upstream
    @GetMapping("/games/{id}/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParticipantGetDTO> getLeaderboard(@PathVariable Long id) {
        List<ParticipantGetDTO> participantGetDTOs = new ArrayList<>();
        List<Participant> participantListOrdered = gameService.getLeaderboard(id);
        for(Participant participant : participantListOrdered){
            participantGetDTOs.add(DTOMapper.INSTANCE.convertParticipantToParticipantGetDTO(participant));
        }
        return participantGetDTOs;
    }

    @GetMapping("/games/{id}/winningSubmission")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SubmissionGetDTO getWinningSubmissionCurrent(@PathVariable Long id) {
        Round currentRound = gameService.getRoundInformation(id);
        Submission winningSubmission = currentRound.getWinningSubmission();
        return DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(winningSubmission);
    }

    @GetMapping("/games/{id}/submissions")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SubmissionGetDTO> getSubmissions(@PathVariable Long id) {
        List<SubmissionGetDTO> submissionGetDTOs = new ArrayList<>();
        Round currentRound = gameService.getRoundInformation(id);
        List<Submission> submissions = new ArrayList<>(currentRound.getSubmissions().values());
        for (Submission submission : submissions) {
            submissionGetDTOs.add(DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(submission));
        }
        return submissionGetDTOs;
    }
=======

>>>>>>> Stashed changes

    @GetMapping("/games/{id}/winningSubmissions")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
<<<<<<< Updated upstream
    public List<SubmissionGetDTO> getWinningSubmissions(@PathVariable Long id) {
        List<SubmissionGetDTO> submissionGetDTOs = new ArrayList<>();
        Game game = gameService.getGameInformation(id);
        List<Round> rounds = game.getRounds();
        for (Round round : rounds) {
            Submission winningSubmission = round.getWinningSubmission();
            submissionGetDTOs.add(DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(winningSubmission));
        }
        return submissionGetDTOs;
    }

=======
    public List<Submission> getWinningSubmissions(@PathVariable Long id) {
        List<Submission> submissions = gameService.getWinningSubmissions(id);
        return DTOMapper.INSTANCE.convertGameToGameGetDTO(submissions,game);
    }
*/
    @GetMapping("/games/{id}/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LeaderboardGetDTO> getLeaderboard(@PathVariable Long id) {
        Game game = gameService.getGameInformation(id);
        //List<LeaderboardGetDTO> leaderboard = gameService.getLeaderboard(id, game);
        List<LeaderboardGetDTO> leaderboard = new ArrayList<>();
        List<Participant> participants = gameService.getLeaderboard(id, game);
        for (Participant participant : participants) {
>>>>>>> Stashed changes

            leaderboard.add(DTOMapper.INSTANCE.convertParticipantToLeaderboardGetDTO(participant));
            leaderboard.get(participants.indexOf(participant)).setPosition(participants.indexOf(participant));
        }
        return leaderboard;
    }
}

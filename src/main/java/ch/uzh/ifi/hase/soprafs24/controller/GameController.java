package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameRoundGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @PostMapping("/games/{id}/voting")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void postVoting(@PathVariable Long id, @RequestBody SubmissionPostDTO submissionPostDTO,
                               @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.postVoting(id, token, submissionPostDTO);
    }

    @GetMapping("/games/{id}/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameRoundGetDTO getLeaderboard(@PathVariable Long id) {
        Round currentRound = gameService.getLeaderboard(id);
        Game game = gameService.getGameInformation(id);
        return DTOMapper.INSTANCE.convertRoundToGameRoundGetDTO(currentRound,game);
    }
    /* --> waiting for implementation of calculation method
    @GetMapping("/games/{id}/winningSubmissions")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameRoundGetDTO getWinningSubmissions(@PathVariable Long id) {
        List<Submission> submissions = gameService.getSubmissions(id);
        Game game = gameService.getGameInformation(id);
        return DTOMapper.INSTANCE.convertGameToGameGetDTO(submissions,game);
    }
*/

}

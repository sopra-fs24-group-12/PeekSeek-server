package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameRoundGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                               @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.postSubmission(id, token, submissionPostDTO);
    }
}

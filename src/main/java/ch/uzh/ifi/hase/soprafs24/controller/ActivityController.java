package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameStartedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {
    private final LobbyService lobbyService;
    private final GameService gameService;

    ActivityController(LobbyService lobbyService, WebsocketService websocketService, GameService gameService) {
        this.lobbyService = lobbyService;
        this.gameService = gameService;
    }


    @PutMapping("/lobbies/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void setActiveLobby(@PathVariable Long id, @RequestBody(required = false) String body,
                          @RequestHeader(value = "Authorization", required = false) String token) {
        lobbyService.updateActiveStatus(id, token);
    }


    @PutMapping("/games/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void setActiveGame(@PathVariable Long id, @RequestBody(required = false) String body,
                          @RequestHeader(value = "Authorization", required = false) String token) {
        gameService.updateActiveStatus(id, token);
    }
}

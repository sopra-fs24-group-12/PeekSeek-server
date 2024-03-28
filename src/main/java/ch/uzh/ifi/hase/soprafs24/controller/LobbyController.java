package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyJoinPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantJoinedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LobbyController {
    private final WebsocketService websocketService;
    private final LobbyService lobbyService;

    LobbyController(LobbyService lobbyService, WebsocketService websocketService) {
        this.lobbyService = lobbyService;
        this.websocketService = websocketService;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        Lobby createdLobby = lobbyService
                .createLobby(lobbyPostDTO.getUsername(), lobbyPostDTO.getName(), lobbyPostDTO.getPassword());
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(createdLobby);
    }

    @GetMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LobbyGetDTO> getLobbies() {
        List<LobbyGetDTO> lobbyGetDTOs = new ArrayList<>();
        List<Lobby> lobbies = lobbyService.getAllLobbies();
        for (Lobby lobby : lobbies) {
            lobbyGetDTOs.add(DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(lobby));
        }
        return lobbyGetDTOs;
    }

    @PutMapping("/lobbies/{id}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void joinLobby(@PathVariable Long id, @RequestBody LobbyJoinPutDTO joinPutDTO) {
        lobbyService.joinLobby(id, joinPutDTO.getUsername(), joinPutDTO.getLobbyPassword());
        websocketService.sendMessage("/topic/lobby/" + id,
                new ParticipantJoinedDTO(joinPutDTO.getUsername()));
    }

    @PutMapping("/lobbies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateLobbySettings(@PathVariable Long id, @RequestBody LobbyPutDTO lobbyPutDTO) {
        Lobby lobby = lobbyService.updateLobbySettings(id, lobbyPutDTO);
        websocketService.sendMessage("/topic/lobby/" + id,
                new UpdateSettingsDTO(lobby.getGameLocation(), lobby.getRoundDurationSeconds(), lobby.getQuests()));
    }
}
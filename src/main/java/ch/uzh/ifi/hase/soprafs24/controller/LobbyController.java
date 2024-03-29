package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyJoinPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantJoinedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantLeftDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LobbyGetDTO> createLobby(@RequestBody LobbyPostDTO lobbyPostDTO,
                                                   HttpServletResponse response) {
        Lobby createdLobby = lobbyService
                .createLobby(lobbyPostDTO.getUsername(), lobbyPostDTO.getName(), lobbyPostDTO.getPassword());
        Participant admin = lobbyService.getParticipantById(createdLobby.getAdminId());
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Authorization", admin.getToken());
        LobbyGetDTO lobbyGetDTO = DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(createdLobby);
        return ResponseEntity.ok().body(lobbyGetDTO);
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

    @GetMapping("/lobbies/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO getLobby(@PathVariable Long id) {
        Lobby lobby = lobbyService.getSpecificLobby(id);
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(lobby);
    }

    @PutMapping("/lobbies/{id}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void joinLobby(@PathVariable Long id, @RequestBody LobbyJoinPutDTO joinPutDTO,
                                      HttpServletResponse response) {
        String token = lobbyService.joinLobby(id, joinPutDTO.getUsername(), joinPutDTO.getLobbyPassword());
        websocketService.sendMessage("/topic/lobby/" + id,
                new ParticipantJoinedDTO(joinPutDTO.getUsername()));
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Authorization", token);
    }

    @DeleteMapping("/lobbies/{id}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveLobby(@PathVariable Long id,
                           @RequestHeader(value = "Authorization", required = false) String token) {
        String username = lobbyService.leaveLobby(id, token);
        websocketService.sendMessage("/topic/lobby/" + id,
                new ParticipantLeftDTO(username));
    }

    @PutMapping("/lobbies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateLobbySettings(@PathVariable Long id, @RequestBody LobbyPutDTO lobbyPutDTO,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        Lobby lobby = lobbyService.updateLobbySettings(id, lobbyPutDTO, token);
        websocketService.sendMessage("/topic/lobby/" + id,
                new UpdateSettingsDTO(lobby.getGameLocation(), lobby.getRoundDurationSeconds(), lobby.getQuests()));
    }
}

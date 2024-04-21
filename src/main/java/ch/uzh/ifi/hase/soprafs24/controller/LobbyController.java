package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameStartedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantJoinedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantLeftDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LobbyController {
    private final WebsocketService websocketService;
    private final LobbyService lobbyService;
    private final GameService gameService;

    LobbyController(LobbyService lobbyService, WebsocketService websocketService, GameService gameService) {
        this.lobbyService = lobbyService;
        this.websocketService = websocketService;
        this.gameService = gameService;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO createLobby(@RequestBody LobbyPostDTO lobbyPostDTO,
                                                   HttpServletResponse response) {
        Lobby lobby = lobbyService.createLobby(lobbyPostDTO.getName(), lobbyPostDTO.getPassword());
        String token = lobbyService.joinLobby(lobby.getId(), lobbyPostDTO.getUsername(), lobbyPostDTO.getPassword());
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Authorization", token);
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(lobby);
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
    public LobbyGetInformationDTO getLobbyInformation(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization", required = false)
                                                      String token) {
        Lobby lobby = lobbyService.getSpecificLobby(id);
        lobbyService.authorizeLobbyParticipant(lobby, token);
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetInformationDTO(lobby);
    }

    @GetMapping("/lobbies/{id}/participants")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParticipantGetDTO> getLobbyParticipants(@PathVariable Long id,
                                                        @RequestHeader(value = "Authorization", required = false)
                                                        String token) {
        List<ParticipantGetDTO> participantGetDTOs = new ArrayList<>();
        List<Participant> participants = lobbyService.getAllParticipants(id, token);
        for (Participant participant : participants) {
            participantGetDTOs.add(DTOMapper.INSTANCE.convertParticipantToParticipantGetDTO(participant));
        }
        return participantGetDTOs;
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
        List<String> usernames = lobbyService.leaveLobby(id, token);
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO(usernames.get(0));
        String newAdmin = usernames.get(1);
        if (newAdmin != null) {
            participantLeftDTO.setNewAdmin(newAdmin);
        }
        websocketService.sendMessage("/topic/lobby/" + id, participantLeftDTO);
    }

    @PutMapping("/lobbies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateLobbySettings(@PathVariable Long id, @RequestBody LobbyPutDTO lobbyPutDTO,
                                    @RequestHeader(value = "Authorization", required = false) String token) throws IOException {
        Lobby lobby = lobbyService.updateLobbySettings(id, lobbyPutDTO, token);
        websocketService.sendMessage("/topic/lobby/" + id,
                new UpdateSettingsDTO(lobby.getGameLocation(), lobby.getRoundDurationSeconds(),
                        lobby.getGameLocationCoordinates(), lobby.getQuests()));
    }

    @PostMapping("/lobbies/{id}/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void startGame(@PathVariable Long id, @RequestBody(required = false) String body,
                          @RequestHeader(value = "Authorization", required = false) String token) {
        Lobby lobby = lobbyService.getSpecificLobby(id);
        lobbyService.authorizeLobbyAdmin(lobby, token);
        Long gameId = gameService.startGame(lobby);
        websocketService.sendMessage("/topic/lobby/" + id, new GameStartedDTO(gameId));
    }

    @GetMapping("/lobbies/cities")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> getExistingCities() {
        return lobbyService.getExistingCities();
    }
}

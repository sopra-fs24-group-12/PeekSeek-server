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
import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LobbyController {
    private final WebsocketService websocketService;
    private final LobbyService lobbyService;
    private final GameService gameService;
    private static final String authorizationField = "Authorization";
    private static final String websocketTopicAddress = "/topic/lobby/";

    LobbyController(LobbyService lobbyService, WebsocketService websocketService, GameService gameService) {
        this.lobbyService = lobbyService;
        this.websocketService = websocketService;
        this.gameService = gameService;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    public LobbyGetDTO createLobby(@RequestBody LobbyPostDTO lobbyPostDTO,
                                                   HttpServletResponse response) {
        Lobby lobby = lobbyService.createLobby(lobbyPostDTO.getName(), lobbyPostDTO.getPassword());
        String token = lobbyService.joinLobby(lobby.getId(), lobbyPostDTO.getUsername(), lobbyPostDTO.getPassword());
        response.setHeader("Access-Control-Expose-Headers", authorizationField);
        response.setHeader(authorizationField, token);
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(lobby);
    }

    @GetMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
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
    public LobbyGetInformationDTO getLobbyInformation(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization", required = false)
                                                      String token) {
        Lobby lobby = lobbyService.getSpecificLobby(id);
        lobbyService.authorizeLobbyParticipant(lobby, token);
        return DTOMapper.INSTANCE.convertLobbyToLobbyGetInformationDTO(lobby);
    }

    @GetMapping("/lobbies/{id}/participants")
    @ResponseStatus(HttpStatus.OK)
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
    public void joinLobby(@PathVariable Long id, @RequestBody LobbyJoinPutDTO joinPutDTO,
                                      HttpServletResponse response) {
        String token = lobbyService.joinLobby(id, joinPutDTO.getUsername(), joinPutDTO.getLobbyPassword());
        websocketService.sendMessage(websocketTopicAddress + id,
                new ParticipantJoinedDTO(joinPutDTO.getUsername()));
        response.setHeader("Access-Control-Expose-Headers", authorizationField);
        response.setHeader(authorizationField, token);
    }

    @DeleteMapping("/lobbies/{id}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveLobby(@PathVariable Long id,
                           @RequestHeader(value = "Authorization", required = false) String token) {
        lobbyService.leaveLobby(id, token);
    }

    @PutMapping("/lobbies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLobbySettings(@PathVariable Long id, @RequestBody LobbyPutDTO lobbyPutDTO,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        Lobby lobby = lobbyService.updateLobbySettings(id, lobbyPutDTO, token);
        websocketService.sendMessage(websocketTopicAddress + id,
                new UpdateSettingsDTO(lobby.getGameLocation(), lobby.getRoundDurationSeconds(),
                        lobby.getGameLocationCoordinates(), lobby.getQuests()));
    }

    @PostMapping("/lobbies/{id}/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startGame(@PathVariable Long id, @RequestBody(required = false) String body,
                          @RequestHeader(value = "Authorization", required = false) String token) {
        Lobby lobby = lobbyService.getSpecificLobby(id);
        lobbyService.authorizeLobbyAdmin(lobby, token);
        Long gameId = gameService.startGame(lobby);
        websocketService.sendMessage(websocketTopicAddress + id, new GameStartedDTO(gameId));
    }

    @GetMapping("/lobbies/cities")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getExistingCities() {
        return lobbyService.getExistingCities();
    }
}

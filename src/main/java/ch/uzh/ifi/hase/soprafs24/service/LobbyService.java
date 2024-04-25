package ch.uzh.ifi.hase.soprafs24.service;

//TODO: Websocket for checking if user left game/lobby
//TODO: Protect websockets?

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.GeoCodingDataRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.google.GeoCoding;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantLeftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import java.io.IOException;

@Service
@Transactional
public class LobbyService {
    private final GeoCodingDataRepository geoCodingDataRepository;
    private final WebsocketService websocketService;

    private final Map<Long, Timer> lobbyTimers = new HashMap<>();

    @Autowired
    public LobbyService(@Qualifier("geoCodingDataRepository") GeoCodingDataRepository geoCodingDataRepository, WebsocketService websocketService) {
        this.geoCodingDataRepository = geoCodingDataRepository;
        this.websocketService = websocketService;
    }

    public Lobby createLobby(String name, String password) {
        checkIfLobbyNameExists(name);
        // TODO: empty password (no password)

        Lobby createdLobby = new Lobby();
        createdLobby.setName(name);
        createdLobby.setPassword(password);

        if (password != null && !password.isEmpty()) {
            createdLobby.setPasswordProtected(true);
        }

        LobbyRepository.addLobby(createdLobby);
        startInactivityTimer(createdLobby);     //Comment out for testing

        return createdLobby;
    }

    public void updateActiveStatus(Long lobbyId, String token) {
        Lobby lobby = getSpecificLobby(lobbyId);
        authorizeLobbyParticipant(lobby, token);
        lobby.updateActivityTime(token);
    }

    private void startInactivityTimer(Lobby lobby) {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<String> inactiveTokens = lobby.removeInactiveParticipants(5000);
                for (String token : inactiveTokens) {
                    leaveLobby(lobby.getId(), token);
                }

                if (lobby.getJoinedParticipants() == 0) {
                    stopInactivityTimer(lobby.getId());
                }
            }
        };
        timer.schedule(task, 5000, 5000);

        lobbyTimers.put(lobby.getId(), timer);
    }

    private void stopInactivityTimer(Long lobbyId) {
        Timer timer = lobbyTimers.get(lobbyId);
        if (timer != null) {
            timer.cancel();
            lobbyTimers.remove(lobbyId);
        }
    }

    public List<String> getExistingCities() {
        return geoCodingDataRepository.findAllCityNames();
    }

    public List<Lobby> getAllLobbies() {
        return LobbyRepository.findAll();
    }

//    public Map<String, GeoCodingData> getAllLobbyLocationsWithCoordinates() {
//        List<Lobby> lobbies = getAllLobbies();
//        Map<String, GeoCodingData> locationCoordinates = new HashMap<>();
//        for (Lobby lobby : lobbies) {
//            locationCoordinates.put(lobby.getGameLocation(), lobby.getGameLocationCoordinates());
//        }
//        return locationCoordinates;
//    }

    public List<Participant> getAllParticipants(Long lobbyId, String token) {
        Lobby lobby = getSpecificLobby(lobbyId);
        authorizeLobbyParticipant(lobby, token);
        return new ArrayList<>(lobby.getParticipants().values());
    }

    public Lobby getSpecificLobby(Long id) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        return lobby;
    }

    public String joinLobby(Long id, String username, String password) {
        Lobby lobby = getSpecificLobby(id);
        checkIfUsernameInLobby(username, lobby);
        if (lobby.getJoinedParticipants() >= lobby.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The lobby is full");
        }
        if (lobby.getPassword() != null && !lobby.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        Participant participant = new Participant();
        participant.setUsername(username);
        participant.setToken(UUID.randomUUID().toString());
        participant.setLobbyId(lobby.getId());

        if (lobby.getJoinedParticipants() == 0) {
            lobby.setAdminUsername(participant.getUsername());
            participant.setAdmin(true);
            lobby.setAdminId(participant.getId());
        }

        lobby.addParticipant(participant);

        if (lobby.getJoinedParticipants() == 1) {   //Comment out for testing
            startInactivityTimer(lobby);
        }

        return participant.getToken();
    }

    public void leaveLobby(Long id, String token) {
        Lobby lobby = getSpecificLobby(id);
        Participant participant = lobby.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A participant with this token does not exist");
        }

        String username = participant.getUsername();
        String newAdminUsername = null;

        if (participant.getAdmin() && lobby.getJoinedParticipants() > 1) {
            lobby.removeParticipant(token);
            Participant newAdmin = lobby.getParticipants().entrySet().iterator().next().getValue();
            lobby.setAdminId(newAdmin.getId());
            newAdmin.setAdmin(true);
            newAdminUsername = newAdmin.getUsername();
            lobby.setAdminUsername(newAdminUsername);
        } else {
            lobby.removeParticipant(token);
        }

        if (lobby.getJoinedParticipants() == 0 && lobby.getQuests() != null && !lobby.getQuests().isEmpty()) {
            stopInactivityTimer(lobby.getId());    //Comment out for testing
            lobby.resetLobby();
        }

        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO(username);
        if (newAdminUsername != null) {
            participantLeftDTO.setNewAdmin(newAdminUsername);
        }

        websocketService.sendMessage("/topic/lobby/" + id, participantLeftDTO);
    }

    // TODO: no lobbyPutDTO as parameter
    public Lobby updateLobbySettings(Long id, LobbyPutDTO lobbyPutDTO, String token) throws IOException {
        Lobby lobby = getSpecificLobby(id);
        authorizeLobbyAdmin(lobby, token);
        if (lobbyPutDTO.getGameLocation() != null) {
            lobby.setGameLocation(lobbyPutDTO.getGameLocation());
        }

        GeoCodingData locationInDatabase = locationIfAlreadyCalled(lobbyPutDTO.getGameLocation());
        if (locationInDatabase != null) {
            lobby.setGameLocationCoordinates(locationInDatabase);
            lobby.setGameLocation(locationInDatabase.getFormAddress());
        } else {
            GeoCodingData coordinates = new GeoCodingData();
            GeoCodingData returnedCoordinates = GeoCoding.getGameCoordinates(lobbyPutDTO.getGameLocation().toLowerCase());

            coordinates.setLocation(returnedCoordinates.getLocation());
            coordinates.setLat(returnedCoordinates.getLat());
            coordinates.setLng(returnedCoordinates.getLng());
            coordinates.setFormAddress(returnedCoordinates.getFormAddress());
            coordinates.setResLatNe(returnedCoordinates.getResLatNe());
            coordinates.setResLngNe(returnedCoordinates.getResLngNe());
            coordinates.setResLngSw(returnedCoordinates.getResLngSw());
            coordinates.setResLatSw(returnedCoordinates.getResLatSw());

            coordinates = geoCodingDataRepository.save(coordinates);
            geoCodingDataRepository.flush();
            lobby.setGameLocationCoordinates(coordinates);
            lobby.setGameLocation(coordinates.getFormAddress());
        }
        if (lobbyPutDTO.getQuests() != null) {
            lobby.setQuests(lobbyPutDTO.getQuests());
        }
        if (lobbyPutDTO.getRoundDurationSeconds() != null) {
            lobby.setRoundDurationSeconds(lobbyPutDTO.getRoundDurationSeconds());
        }

        // TODO: return list instead of object (in case it is more efficient)
        return lobby;
    }


    private void checkIfLobbyNameExists(String name) {
        Boolean nameFree = LobbyRepository.lobbyNameFree(name);
        if (!nameFree) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A lobby with this name already exists");
        }
    }

    public void checkIfUsernameInLobby(String username, Lobby lobby) {
        if (lobby.getUsernames().contains(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in lobby");
        }
    }

    public GeoCodingData locationIfAlreadyCalled(String location) {
        return geoCodingDataRepository.findGeoCodingDataByLocation(location);
    }

    public void authorizeLobbyAdmin(Lobby lobby, String token) {
        Participant admin = lobby.getParticipantByToken(token);
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        } else if (!admin.getAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not sufficient permissions");
        }
    }

    public void authorizeLobbyParticipant(Lobby lobby, String token) {
        Participant participant = lobby.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        }
    }
}

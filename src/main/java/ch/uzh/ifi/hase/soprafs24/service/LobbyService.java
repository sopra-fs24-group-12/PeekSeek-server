package ch.uzh.ifi.hase.soprafs24.service;

//TODO: Websocket for checking if user left game/lobby
//TODO: Protect websockets?

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.GeoCodingDataRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GeoCodingGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.google.GeoCoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

import java.io.IOException;

@Service
@Transactional
public class LobbyService {
    private final GeoCodingDataRepository geoCodingDataRepository;

    @Autowired
    public LobbyService(@Qualifier("geoCodingDataRepository") GeoCodingDataRepository geoCodingDataRepository) {
        this.geoCodingDataRepository = geoCodingDataRepository;
    }

    public Lobby createLobby(String name, String password) {
        checkIfLobbyNameExists(name);
        // TODO: empty password (no password)

        Lobby createdLobby = new Lobby();
        createdLobby.setName(name);
        createdLobby.setPassword(password);

        LobbyRepository.addLobby(createdLobby);

        return createdLobby;

    }

    public List<Lobby> getAllLobbies() {
        return LobbyRepository.findAll();
    }

    public Map<String, GeoCodingData> getAllLobbyLocationsWithCoordinates() {
        List<Lobby> lobbies = getAllLobbies();
        Map<String, GeoCodingData> locationCoordinates = new HashMap<>();
        for (Lobby lobby : lobbies) {
            locationCoordinates.put(lobby.getGameLocation(), lobby.getGameLocationCoordinates());
        }
        return locationCoordinates;
    }

    public List<Participant> getAllParticipants(Long lobbyId, String token) {
        Lobby lobby = LobbyRepository.getLobbyById(lobbyId);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        authorizeLobbyParticipant(lobby, token);
        return new ArrayList<>(lobby.getParticipants().values());
    }

    // TODO: maybe protect for only participants (low priority)
    public Lobby getSpecificLobby(Long id) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        return lobby;
    }

    // TODO: empty password/no password
    public String joinLobby(Long id, String username, String password) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        checkIfUsernameInLobby(username, lobby);
        if (lobby.getJoinedParticipants() >= lobby.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The lobby is full");
        }
        if (!lobby.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        Participant participant = new Participant();
        participant.setUsername(username);
        participant.setToken(UUID.randomUUID().toString());
        participant.setLobbyId(lobby.getId());

        if (lobby.getJoinedParticipants() == 0) {
            participant.setAdmin(true);
            lobby.setAdminId(participant.getId());
        }

        lobby.addParticipant(participant);

        return participant.getToken();
    }

    public List<String> leaveLobby(Long id, String token) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        Participant participant = lobby.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A participant with this token does not exist");
        }

        String username = participant.getUsername();
        String newAdminUsername = null;

        if (participant.getAdmin()) {
            lobby.removeParticipant(token);
            Participant newAdmin = lobby.getParticipants().entrySet().iterator().next().getValue();
            lobby.setAdminId(newAdmin.getId());
            newAdmin.setAdmin(true);
            newAdminUsername = newAdmin.getUsername();
        } else {
            lobby.removeParticipant(token);
        }

        if (lobby.getJoinedParticipants() == 0 && !lobby.getQuests().isEmpty()) {
            lobby.resetLobby();
        }

        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        usernames.add(newAdminUsername);
        return usernames;
    }

    // TODO: no lobbyPutDTO as parameter
    public Lobby updateLobbySettings(Long id, LobbyPutDTO lobbyPutDTO, String token) throws IOException {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
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

//    public Long startGame(Long lobbyId, String token) {
//        Lobby lobby = LobbyRepository.getLobbyById(lobbyId);
//        if (lobby == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
//        }
//
//        authorizeLobbyAdmin(lobby, token);
//
//        if (lobby.getJoinedParticipants() < 3) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                    "You need at least 3 participants to start the game");
//        }
//
//        List<String> quests = lobby.getQuests();
//        if (quests.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                    "You need to have at least one quest to start the game");
//        }
//
//        Game createdGame = new Game();
//
//        List<Round> rounds = new ArrayList<>(quests.size());
//        for (String quest : quests) {
//            Round round = new Round();
//            round.setQuest(quest);
//            round.setRoundTime(lobby.getRoundDurationSeconds());
//            round.setRemainingSeconds(lobby.getRoundDurationSeconds());
//            round.setGame(createdGame.getId());
//            rounds.add(round);
//        }
//
//        createdGame.setRoundDurationSeconds(lobby.getRoundDurationSeconds());
//        createdGame.setAdminId(lobby.getAdminId());
//        createdGame.setGameLocation(lobby.getGameLocation());
//        createdGame.setRounds(rounds);
//        createdGame.setNumberRounds(lobby.getQuests().size());
//
//        List<Participant> participants = new ArrayList<>(lobby.getParticipants());
//        for (Participant participant : participants) {
//            participant.setGame(createdGame.getId());
//            participant.setLobby(null);
//        }
//        createdGame.setParticipants(participants);
//        createdGame.getRounds().get(0).setRoundStatus(RoundStatus.PLAYING);
//        lobby.getParticipants().clear();
//        lobby.recycleLobby();
//
//        lobby = lobbyRepository.save(lobby);
//        createdGame = gameRepository.save(createdGame);
//        gameRepository.flush();
//
//        gameService.startTimer(createdGame.getRounds().get(0), createdGame.getId());
//
//        return createdGame.getId();
//    }

    private void checkIfLobbyNameExists(String name) {
        Boolean nameFree = LobbyRepository.lobbyNameFree(name);
        if (!nameFree) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A lobby with this name already exists");
        }
    }

    public void checkIfUsernameInLobby(String username, Lobby lobby) {
        if (lobby.getUsernames().contains(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already in lobby");
        }
    }

    public GeoCodingData locationIfAlreadyCalled(String location) throws IOException {
        // check if location is already in the database
//        List<GeoCodingData> lookedUpCoordinates = geoCodingDataRepository.findAll();
//        for (GeoCodingData geoCodingData : lookedUpCoordinates) {
//            if (geoCodingData.getLocation().toLowerCase().contains(location.toLowerCase())) {
//                return geoCodingData;
//            }
//        }
        //return null;
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

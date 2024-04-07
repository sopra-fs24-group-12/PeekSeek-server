package ch.uzh.ifi.hase.soprafs24.service;


import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.service.APIService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {

    public Long createLobby(String name, String password) {
        checkIfLobbyNameExists(name);

        Lobby createdLobby = new Lobby();
        createdLobby.setName(name);
        createdLobby.setPassword(password);

        LobbyRepository.addLobby(createdLobby);

        return createdLobby.getId();

    }

    public List<Lobby> getAllLobbies() {
        return LobbyRepository.findAll();
    }

    public Map<String, List<String>> getAllLobbyLocationsWithCoordinates() {
        List<Lobby> lobbies = getAllLobbies();
        Map<String, List<String>> locationCoordinates = new HashMap<>();
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

    public Lobby getSpecificLobby(Long id) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        return lobby;
    }

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

    public String leaveLobby(Long id, String token) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        Participant participant = lobby.getParticipantByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A participant with this token does not exist");
        }
        if (participant.getId().equals(lobby.getAdminId())) {
            // TODO: handle case where admin leaves the lobby
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The admin cannot leave the lobby");
        }
        String username = participant.getUsername();
        lobby.removeParticipant(token);

        return username;
    }

    public Lobby updateLobbySettings(Long id, LobbyPutDTO lobbyPutDTO, String token) {
        Lobby lobby = LobbyRepository.getLobbyById(id);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A lobby with this ID does not exist");
        }
        authorizeLobbyAdmin(lobby, token);
        if (lobbyPutDTO.getGameLocation() != null) {
            
            lobby.setGameLocation(lobbyPutDTO.getGameLocation());
        }
        if(returnIfLocationAlreadyCalled(lobbyPutDTO.getGameLocation()) != null) {
            lobby.setGameLocationCoordinates(returnIfLocationAlreadyCalled(lobbyPutDTO.getGameLocation()));
        } else {
            List<String> coordinates = APIService.getGameCoordinates(lobbyPutDTO.getGameLocation());
            lobby.setGameLocationCoordinates(coordinates);
        }
        if (lobbyPutDTO.getQuests() != null) {
            lobby.setQuests(lobbyPutDTO.getQuests());
        }
        if (lobbyPutDTO.getRoundDurationSeconds() != null) {
            lobby.setRoundDurationSeconds(lobbyPutDTO.getRoundDurationSeconds());
        }

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

    public List<String> returnIfLocationAlreadyCalled(String location) {
        // check if location is already in the database
        List<String> coordinates = getAllLobbyLocationsWithCoordinates().containsKey(location) ?
                getAllLobbyLocationsWithCoordinates().get(location) : null;
        return coordinates;
    }

    public void authorizeLobbyAdmin(Lobby lobby, String token) {
        Participant admin = lobby.getParticipantByToken(token);
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        } else if (!admin.getId().equals(lobby.getAdminId())) {
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

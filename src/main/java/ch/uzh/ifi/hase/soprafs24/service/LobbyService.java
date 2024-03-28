package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.ParticipantRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository,
                        @Qualifier("participantRepository") ParticipantRepository participantRepository) {
        this.lobbyRepository = lobbyRepository;
        this.participantRepository = participantRepository;
    }

    public Lobby createLobby(String username, String name, String password) {
        checkIfLobbyNameExists(name);

        Lobby createdLobby = new Lobby();
        createdLobby.setName(name);
        createdLobby.setPassword(password);

        Participant admin = new Participant();
        admin.setAdmin(true);
        admin.setUsername(username);
        admin.setToken(UUID.randomUUID().toString());
        admin = participantRepository.save(admin);

        createdLobby.setAdminId(admin.getId());
        createdLobby.addParticipant(admin);
        createdLobby.setJoinedParticipants(1);
        createdLobby = lobbyRepository.save(createdLobby);

        admin.setLobby(createdLobby.getId());
        admin = participantRepository.save(admin);

        return createdLobby;
    }

    public List<Lobby> getAllLobbies() {
        return this.lobbyRepository.findAll();
    }

    public void joinLobby(Long id, String username, String password) {
        Lobby lobby = lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkIfUsernameInLobby(username, id);
        if (lobby.getJoinedParticipants() >= lobby.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The lobby is full");
        }
        if (!lobby.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        Participant participant = new Participant();
        participant.setUsername(username);
        participant.setToken(UUID.randomUUID().toString());

        lobby.addParticipant(participant);

        lobby = lobbyRepository.save(lobby);
        lobbyRepository.flush();
    }

    public Lobby updateLobbySettings(Long id, LobbyPutDTO lobbyPutDTO) {
        Lobby lobby = lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (lobbyPutDTO.getGameLocation() != null) {
            lobby.setGameLocation(lobbyPutDTO.getGameLocation());
        }
        if (lobbyPutDTO.getQuest() != null) {
            lobby.addQuest(lobbyPutDTO.getQuest());
        }
        if (lobbyPutDTO.getRoundDurationSeconds() != null) {
            lobby.setRoundDurationSeconds(lobbyPutDTO.getRoundDurationSeconds());
        }

        lobby = lobbyRepository.save(lobby);

        return lobby;
    }

    private void checkIfLobbyNameExists(String name) {
        Lobby lobby = lobbyRepository.findLobbyByName(name);
        if (lobby != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A lobby with this name already exists");
        }
    }

    public void checkIfUsernameInLobby(String username, Long lobbyId) {
        Participant participant = participantRepository.findByUsernameAndLobby(username, lobbyId);
        if (participant != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already in lobby");
        }
    }
}

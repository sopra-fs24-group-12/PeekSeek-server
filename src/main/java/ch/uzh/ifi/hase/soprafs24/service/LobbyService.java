package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    private void checkIfLobbyNameExists(String name) {
        Lobby lobby = lobbyRepository.findLobbyByName(name);
        if (lobby != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A lobby with this name already exists");
        }
    }
}

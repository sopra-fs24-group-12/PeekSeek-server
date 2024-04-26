package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.GeoCodingDataRepository;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private GeoCodingDataRepository geoCodingDataRepository;

    @Mock
    private WebsocketService websocketService;

    @Mock
    private LobbyRepository lobbyRepositoryInstance;

    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Lobby lobby = new Lobby();
        lobby.setId(1L);
        lobby.setName("lobby");
        lobby.setPassword("test");
        lobby.setPasswordProtected(true);
        LobbyRepository.addLobby(lobby);
    }

    @Test
    public void testCreateLobby() {
        String name = "Test Lobby";
        String password = "password123";

        Lobby createdLobby = lobbyService.createLobby(name, password);

        assertNotNull(createdLobby);
        assertEquals(name, createdLobby.getName());
        assertEquals(password, createdLobby.getPassword());
        assertTrue(createdLobby.getPasswordProtected());
        verify(lobbyRepositoryInstance, times(1));
        LobbyRepository.addLobby(createdLobby);
    }

    @Test
    public void testJoinLobby() {
        String username = "test";
        String password = "test";
        String token = lobbyService.joinLobby(1L, username, password);

        Map<String, Participant> joinedParticipants = LobbyRepository.getLobbyById(1L).getParticipants();

        assert(joinedParticipants.values().size() == 1);
        assert(joinedParticipants.get(token).getUsername().equals(username));
        assert(joinedParticipants.get(token).getId() == 1L);
        assert(joinedParticipants.get(token).getAdmin());
        assert(lobbyService.getSpecificLobby(1L).getJoinedParticipants() == 1);
        assert(lobbyService.getSpecificLobby(1L).getAdminId() == 1L);
        assert(lobbyService.getSpecificLobby(1L).getUsernames().contains("test"));
    }

    @Test
    public void testLeaveLobby() {
        String username = "test";
        String password = "test";
        String token = lobbyService.joinLobby(1L, username, password);

        Map<String, Participant> joinedParticipants = LobbyRepository.getLobbyById(1L).getParticipants();

        lobbyService.leaveLobby(1L, token);
        assert(joinedParticipants.values().isEmpty());
        assert(lobbyService.getSpecificLobby(1L).getJoinedParticipants() == 0);
        assert(!lobbyService.getSpecificLobby(1L).getUsernames().contains("test"));
    }

}

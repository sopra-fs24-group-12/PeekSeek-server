package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.google.GeoCoding;
import ch.uzh.ifi.hase.soprafs24.repository.GeoCodingDataRepository;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Lob;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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

    private Lobby lobby;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Lobby lobby = new Lobby();
        this.lobby = lobby;
        lobby.setId(1L);
        lobby.setName("lobby");
        lobby.setPassword("test");
        lobby.setPasswordProtected(true);
        LobbyRepository.addLobby(lobby);
    }

    @AfterEach
    public void tearDown() {
        LobbyRepository.deleteLobby(1L);
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
    }

    @Test
    public void testJoinLobby() {
        String username = "test";
        String password = "test";
        String token = lobbyService.joinLobby(1L, username, password);

        Map<String, Participant> joinedParticipants = LobbyRepository.getLobbyById(1L).getParticipants();

        assert(joinedParticipants.values().size() == 1);
        assert(joinedParticipants.get(token).getUsername().equals(username));
        assert(joinedParticipants.get(token).getAdmin());
        assert(lobbyService.getSpecificLobby(1L).getJoinedParticipants() == 1);
        assert(Objects.equals(lobbyService.getSpecificLobby(1L).getAdminUsername(), "test"));
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

    @Test
    public void testUpdateLobbySettings() throws IOException {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");

        lobby.setAdminId(1L);
        lobby.setAdminUsername("test");
        lobby.addParticipant(participant);

        GeoCodingData geoCodingData = new GeoCodingData();
        geoCodingData.setLocation("zurich");
        geoCodingData.setLat("47.3768866");
        geoCodingData.setLng("8.541694");
        geoCodingData.setResLatNe("47.434665");
        geoCodingData.setResLngNe("8.625452899999999");
        geoCodingData.setResLatSw("47.32021839999999");
        geoCodingData.setResLngSw("8.448018099999999");
        geoCodingData.setFormAddress("Zürich, Switzerland");
        given(geoCodingDataRepository.save(any())).willReturn(geoCodingData);

        List<String> quests = new ArrayList<>();
        quests.add("item 1");

        LobbyPutDTO lobbyPutDTO = new LobbyPutDTO();
        lobbyPutDTO.setGameLocation("Zurich");
        lobbyPutDTO.setRoundDurationSeconds(60);
        lobbyPutDTO.setQuests(quests);

        Lobby lobby1 = lobbyService.updateLobbySettings(1L, lobbyPutDTO, "abc");

        assert(lobby1.getQuests().equals(quests));
        assert(lobby1.getRoundDurationSeconds() == 60);
        assert(lobby1.getGameLocation().equals("Zürich, Switzerland"));
    }

    @Test
    public void testGetAllParticipants() throws IOException {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");

        lobby.setAdminId(1L);
        lobby.setAdminUsername("test");
        lobby.addParticipant(participant);

        //given(lobbyRepositoryInstance.save(any())).willReturn(Lobby);

        lobbyService.authorizeLobbyAdmin(lobby, participant.getToken());

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);


        List<Participant> lobby1 = lobbyService.getAllParticipants(1L, "abc");

        assert(lobby1.equals(participants));
        assert(lobby1.get(0).getId() == 1L);
        assert(lobby1.get(0).getUsername().equals("test"));
        assert(lobby1.get(0).getAdmin().equals(true));
        assert(lobby1.get(0).getToken().equals("abc"));
    }
    public void testCheckIfLobbyNameExists() throws Exception {
        Boolean free = false;
        Boolean f = LobbyRepository.lobbyNameFree("lobby");
        assert(free.equals(f));
    }


}

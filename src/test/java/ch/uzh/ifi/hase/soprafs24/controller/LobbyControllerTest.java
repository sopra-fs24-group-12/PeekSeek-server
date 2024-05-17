package ch.uzh.ifi.hase.soprafs24.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;


import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.*;

@ExtendWith(SpringExtension.class)
class LobbyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LobbyService lobbyService;
    @MockBean
    private DTOMapper dtoMapper;

    @Mock
    private WebsocketService websocketService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private LobbyController lobbyController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lobbyController).build();
    }

    @Test
    void testCreateLobby() throws Exception {
        // Given
        LobbyPostDTO lobbyPostDTO = new LobbyPostDTO();
        lobbyPostDTO.setName("testLobby");
        lobbyPostDTO.setUsername("user1");
        lobbyPostDTO.setPassword("password123");

        Lobby mockLobby = new Lobby();
        mockLobby.setId(1L);
        mockLobby.setName("testLobby");

        when(lobbyService.createLobby(anyString(), anyString())).thenReturn(mockLobby);
        when(lobbyService.joinLobby(eq(mockLobby.getId()), anyString(), anyString())).thenReturn("authToken");

        // When & Then
        mockMvc.perform(post("/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lobbyPostDTO)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Authorization", "authToken"));

        verify(lobbyService).createLobby(lobbyPostDTO.getName(), lobbyPostDTO.getPassword());
        verify(lobbyService).joinLobby(mockLobby.getId(), lobbyPostDTO.getUsername(), lobbyPostDTO.getPassword());
    }

    @Test
    void testGetLobbies() throws Exception {
        // Given
        Lobby mockLobby1 = new Lobby();
        mockLobby1.setId(1L);
        mockLobby1.setName("LobbyOne");

        Lobby mockLobby2 = new Lobby();
        mockLobby2.setId(2L);
        mockLobby2.setName("LobbyTwo");

        List<Lobby> mockLobbies = Arrays.asList(mockLobby1, mockLobby2);

        LobbyGetDTO mockLobbyGetDTO1 = new LobbyGetDTO();
        mockLobbyGetDTO1.setId(mockLobby1.getId());
        mockLobbyGetDTO1.setName(mockLobby1.getName());

        LobbyGetDTO mockLobbyGetDTO2 = new LobbyGetDTO();
        mockLobbyGetDTO2.setId(mockLobby2.getId());
        mockLobbyGetDTO2.setName(mockLobby2.getName());

        List<LobbyGetDTO> mockLobbyGetDTOs = Arrays.asList(mockLobbyGetDTO1, mockLobbyGetDTO2);

        when(lobbyService.getAllLobbies()).thenReturn(mockLobbies);
        when(dtoMapper.convertLobbyToLobbyGetDTO(mockLobby1)).thenReturn(mockLobbyGetDTO1);

        // When
        MockHttpServletRequestBuilder getRequest = get("/lobbies")
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verify(lobbyService).getAllLobbies();
    }

    @Test
    void testGetLobbyInformation() throws Exception {
        String token = "mocktoken123";
        String token2 = "mocktoken1234";
        // Given
        Lobby mockLobby1 = new Lobby();
        mockLobby1.setId(1L);
        mockLobby1.setName("LobbyOne");
        mockLobby1.setPassword("password123");
        mockLobby1.setGameLocation("Zurich");
        mockLobby1.setRoundDurationSeconds(30);
        mockLobby1.setAdminUsername("K");


        Participant p = new Participant();
        p.setId(1L);
        p.setUsername("K");
        p.setAdmin(true);

        Participant p2 = new Participant();
        p2.setId(1L);
        p2.setUsername("M");

        List<String> participants = Arrays.asList("K", "M");
        List<String> quests = Arrays.asList("Hi", "Hello");

        Map<String, Participant> m = new HashMap<String, Participant>();
        m.put(token, p);
        m.put(token2, p2);
        mockLobby1.setParticipants(m);


        LobbyGetInformationDTO mockLobbyGetDTO1 = new LobbyGetInformationDTO();
        mockLobbyGetDTO1.setAdminUsername(mockLobby1.getAdminUsername());
        mockLobbyGetDTO1.setName(mockLobby1.getName());
        mockLobbyGetDTO1.setGameLocation(mockLobby1.getGameLocation());
        mockLobbyGetDTO1.setRoundDurationSeconds(mockLobby1.getRoundDurationSeconds());




        when(lobbyService.getSpecificLobby(mockLobby1.getId())).thenReturn(mockLobby1);
        when(dtoMapper.convertLobbyToLobbyGetInformationDTO(mockLobby1)).thenReturn(mockLobbyGetDTO1);



        mockMvc.perform(get("/lobbies/{id}", mockLobby1.getId())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminUsername", is(mockLobbyGetDTO1.getAdminUsername())))
                .andExpect(jsonPath("$.name", is(mockLobbyGetDTO1.getName())))

                .andExpect(jsonPath("$.roundDurationSeconds", is(mockLobbyGetDTO1.getRoundDurationSeconds())))
                .andExpect(jsonPath("$.gameLocation", is(mockLobbyGetDTO1.getGameLocation())));





        verify(lobbyService).getSpecificLobby(mockLobby1.getId());
    }

    @Test
    void testGetLobbyParticipants() throws Exception {
        String token = "mocktoken123";
        String token2 = "mocktoken1234";
        // Given
        Lobby mockLobby1 = new Lobby();
        mockLobby1.setId(1L);
        mockLobby1.setName("LobbyOne");
        mockLobby1.setPassword("password123");
        mockLobby1.setGameLocation("Zurich");
        mockLobby1.setRoundDurationSeconds(30);
        mockLobby1.setAdminUsername("K");


        Participant p = new Participant();
        p.setId(1L);
        p.setUsername("K");
        p.setAdmin(true);
        p.setScore(88);

        Participant p2 = new Participant();
        p2.setId(1L);
        p2.setUsername("M");
        p2.setScore(30);
        p2.setStreak(1);

        List<String> participants = Arrays.asList("K", "M");
        List<String> quests = Arrays.asList("Hi", "Hello");
        List<Participant> participants1 = new ArrayList<Participant>();
        participants1.add(p);
        participants1.add(p2);

        Map<String, Participant> m = new HashMap<String, Participant>();
        m.put(token, p);
        m.put(token2, p2);
        mockLobby1.setParticipants(m);



        ParticipantGetDTO p3 = new ParticipantGetDTO();
        p3.setId(p.getId());
        p3.setUsername(p.getUsername());
        p3.setAdmin(p.getAdmin());
        p3.setScore(p.getScore());

        ParticipantGetDTO p4 = new ParticipantGetDTO();
        p4.setId(p2.getId());
        p4.setUsername(p2.getUsername());
        p4.setAdmin(p2.getAdmin());
        p4.setScore(p2.getScore());
        p4.setStreak(p.getStreak());




        when(lobbyService.getAllParticipants(mockLobby1.getId(), token)).thenReturn(participants1);

        when(dtoMapper.convertParticipantToParticipantGetDTO(any(Participant.class)))
                .thenAnswer(invocation -> {
                    Participant participant = invocation.getArgument(0);

                    if (participant.getUsername().equals("K")) {
                        return p3;
                    } else if (participant.getUsername().equals("M")) {
                        return p4;
                    } else {

                        return null;
                    }
                });


        mockMvc.perform(get("/lobbies/{id}/participants", mockLobby1.getId())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(lobbyService).getAllParticipants(mockLobby1.getId(), token);

    }

    @Test
    void testJoinLobby() throws Exception {
        LobbyJoinPutDTO lp = new LobbyJoinPutDTO();
        lp.setLobbyPassword("password123");
        lp.setUsername("user1");

        Lobby mockLobby = new Lobby();
        mockLobby.setId(1L);
        mockLobby.setName("testLobby");

        when(lobbyService.joinLobby(eq(mockLobby.getId()), anyString(), anyString())).thenReturn("Token");

        // When & Then
        mockMvc.perform(put("/lobbies/{id}/join", mockLobby.getId(), lp)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lp)))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Authorization", "Token"));

        verify(lobbyService).joinLobby(mockLobby.getId(), lp.getUsername(), lp.getLobbyPassword());
    }

    @Test
    public void testLeaveLobby() throws Exception {

        Long lobbyId = 1L;
        String token = "mockToken";


        mockMvc.perform(delete("/lobbies/{id}/leave", lobbyId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(lobbyService).leaveLobby(lobbyId, token);
    }
    /*
    @Test
    public void testUpdateLobbySettings() throws Exception {
        // Given
        Long lobbyId = 1L;
        String token = "mockToken";
        LobbyPutDTO lobbyPutDTO = new LobbyPutDTO();
        lobbyPutDTO.setGameLocation("Zurich");
        lobbyPutDTO.setRoundDurationSeconds(30);

        Lobby mockLobby = new Lobby();
        mockLobby.setGameLocation("Zurich");
        mockLobby.setRoundDurationSeconds(30);


        //when(lobbyService.getSpecificLobby(lobbyId)).thenReturn(mockLobby);
        when(lobbyService.updateLobbySettings(eq(lobbyId), eq(lobbyPutDTO), eq(token))).thenReturn(mockLobby);



        mockMvc.perform(put("/lobbies/{id}", lobbyId, lobbyPutDTO)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lobbyPutDTO)))
                .andExpect(status().isNoContent());


        verify(lobbyService).updateLobbySettings(lobbyId, lobbyPutDTO, token);


    }
    */

    @Test
    public void testStartGame() throws Exception {
        // Given
        Long lobbyId = 1L;
        String token = "mockToken";
        String token2 = "mockToken2";

        Lobby mockLobby = new Lobby();
        mockLobby.setGameLocation("Zurich");
        mockLobby.setRoundDurationSeconds(30);
        mockLobby.setAdminId(2L);

        Participant p = new Participant();
        p.setId(1L);
        p.setUsername("K");
        p.setAdmin(true);
        p.setScore(88);

        Participant p2 = new Participant();
        p2.setId(1L);
        p2.setUsername("M");
        p2.setScore(30);
        p2.setStreak(1);

        Map<String, Participant> m = new HashMap<String, Participant>();
        m.put(token, p);
        m.put(token2, p2);
        mockLobby.setParticipants(m);
        when(lobbyService.getSpecificLobby(lobbyId)).thenReturn(mockLobby);


        mockMvc.perform(post("/lobbies/{id}/start", lobbyId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Add request body if needed
                .andExpect(status().isNoContent());


        verify(lobbyService).getSpecificLobby(lobbyId);
        verify(lobbyService).authorizeLobbyAdmin(mockLobby, token);
        verify(gameService).startGame(mockLobby);

    }
    @Test
    public void testGetExistingCities() throws Exception {

        List<String> cities = Arrays.asList("City1", "City2", "City3");
        when(lobbyService.getExistingCities()).thenReturn(cities);

        mockMvc.perform(get("/lobbies/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("City1"))
                .andExpect(jsonPath("$[1]").value("City2"))
                .andExpect(jsonPath("$[2]").value("City3"));
        
        verify(lobbyService).getExistingCities();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

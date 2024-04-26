package ch.uzh.ifi.hase.soprafs24.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;


import com.fasterxml.jackson.databind.ObjectMapper;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPostDTO;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class LobbyControllerTest {

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
    public void testCreateLobby() throws Exception {
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
    public void testGetLobbies() throws Exception {
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
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is("LobbyOne")))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].name", is("LobbyTwo")));

        verify(lobbyService).getAllLobbies();
//        verify(DTOMapper.INSTANCE).convertLobbyToLobbyGetDTO(mockLobby1);
//        verify(DTOMapper.INSTANCE).convertLobbyToLobbyGetDTO(mockLobby2);
    }
}

package ch.uzh.ifi.hase.soprafs24.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
public class LobbyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LobbyService lobbyService;

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
}

package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {
    
    @InjectMocks
    private ActivityController activityController;
    
    @Mock
    private LobbyService lobbyService;
    
    @Mock
    private GameService gameService;
    
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void setActiveLobby_ShouldUpdateActiveStatus() throws Exception {
        doNothing().when(lobbyService).updateActiveStatus(anyLong(), anyString());
        
        mockMvc.perform(put("/lobbies/{id}/active", 1L)
                .header("Authorization", "123")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void setActiveGame_ShouldUpdateActiveStatus() throws Exception {
        doNothing().when(gameService).updateActiveStatus(anyLong(), anyString());
        
        mockMvc.perform(put("/games/{id}/active", 1L)
                .header("Authorization", "123")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}

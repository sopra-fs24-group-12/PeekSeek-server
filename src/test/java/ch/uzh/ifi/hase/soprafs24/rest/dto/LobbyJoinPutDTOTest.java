package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LobbyJoinPutDTOTest {

    private LobbyJoinPutDTO lobbyJoinPutDTO;

    @BeforeEach
    public void setUp() {
        lobbyJoinPutDTO = new LobbyJoinPutDTO();
    }

    @Test
    public void getUsername_setAndReturnCorrectValue() {
        String expectedUsername = "Player1";
        lobbyJoinPutDTO.setUsername(expectedUsername);
        
        assertEquals(expectedUsername, lobbyJoinPutDTO.getUsername());
    }

    @Test
    public void getLobbyPassword_setAndReturnCorrectValue() {
        String expectedLobbyPassword = "SecretPass123";
        lobbyJoinPutDTO.setLobbyPassword(expectedLobbyPassword);
        
        assertEquals(expectedLobbyPassword, lobbyJoinPutDTO.getLobbyPassword());
    }
}

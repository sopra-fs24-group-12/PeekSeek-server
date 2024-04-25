package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LobbyPostDTOTest {

    private LobbyPostDTO lobbyPostDTO;

    @BeforeEach
    public void setUp() {
        lobbyPostDTO = new LobbyPostDTO();
    }

    @Test
    public void getUsername_setUsername_returnsCorrectValue() {
        String expectedUsername = "Player2";
        lobbyPostDTO.setUsername(expectedUsername);
        
        assertEquals(expectedUsername, lobbyPostDTO.getUsername(), "The username should match the set value.");
    }

    @Test
    public void getName_setName_returnsCorrectValue() {
        String expectedName = "GameRoom1";
        lobbyPostDTO.setName(expectedName);
        
        assertEquals(expectedName, lobbyPostDTO.getName(), "The name should match the set value.");
    }

    @Test
    public void getPassword_setPassword_returnsCorrectValue() {
        String expectedPassword = "SecurePassword456";
        lobbyPostDTO.setPassword(expectedPassword);
        
        assertEquals(expectedPassword, lobbyPostDTO.getPassword(), "The password should match the set value.");
    }
}

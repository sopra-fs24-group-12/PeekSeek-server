package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LobbyPutDTOTest {

    private LobbyPutDTO lobbyPutDTO;

    @BeforeEach
    public void setUp() {
        lobbyPutDTO = new LobbyPutDTO();
    }

    @Test
    public void getRoundDurationSeconds_setRoundDurationSeconds_returnsCorrectValue() {
        Integer expectedDuration = 120;
        lobbyPutDTO.setRoundDurationSeconds(expectedDuration);
        
        assertEquals(expectedDuration, lobbyPutDTO.getRoundDurationSeconds(), "The round duration in seconds should match the set value.");
    }

    @Test
    public void getQuests_setQuests_returnsCorrectList() {
        List<String> expectedQuests = Arrays.asList("Quest1", "Quest2", "Quest3");
        lobbyPutDTO.setQuests(expectedQuests);
        
        assertIterableEquals(expectedQuests, lobbyPutDTO.getQuests(), "The quests list should match the set value.");
    }

    @Test
    public void getGameLocation_setGameLocation_returnsCorrectValue() {
        String expectedLocation = "Berlin";
        lobbyPutDTO.setGameLocation(expectedLocation);
        
        assertEquals(expectedLocation, lobbyPutDTO.getGameLocation(), "The game location should match the set value.");
    }
}

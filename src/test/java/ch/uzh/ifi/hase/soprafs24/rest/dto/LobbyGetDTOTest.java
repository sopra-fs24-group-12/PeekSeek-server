package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LobbyGetDTOTest {

    private LobbyGetDTO lobbyGetDTO;

    @BeforeEach
    public void setUp() {
        lobbyGetDTO = new LobbyGetDTO();
    }

    @Test
    public void getId_setAndReturnCorrectValue() {
        Long id = 1L;
        lobbyGetDTO.setId(id);
        
        assertEquals(id, lobbyGetDTO.getId());
    }

    @Test
    public void getName_setAndReturnCorrectValue() {
        String name = "Game Room";
        lobbyGetDTO.setName(name);
        
        assertEquals(name, lobbyGetDTO.getName());
    }

    @Test
    public void getMaxParticipants_setAndReturnCorrectValue() {
        Integer maxParticipants = 10;
        lobbyGetDTO.setMaxParticipants(maxParticipants);
        
        assertEquals(maxParticipants, lobbyGetDTO.getMaxParticipants());
    }

    @Test
    public void getJoinedParticipants_setAndReturnCorrectValue() {
        Integer joinedParticipants = 5;
        lobbyGetDTO.setJoinedParticipants(joinedParticipants);
        
        assertEquals(joinedParticipants, lobbyGetDTO.getJoinedParticipants());
    }

    @Test
    public void getRoundDurationSeconds_setAndReturnCorrectValue() {
        Integer roundDurationSeconds = 120;
        lobbyGetDTO.setRoundDurationSeconds(roundDurationSeconds);
        
        assertEquals(roundDurationSeconds, lobbyGetDTO.getRoundDurationSeconds());
    }

    @Test
    public void getGameLocation_setAndReturnCorrectValue() {
        String gameLocation = "Zurich";
        lobbyGetDTO.setGameLocation(gameLocation);
        
        assertEquals(gameLocation, lobbyGetDTO.getGameLocation());
    }

    @Test
    public void getQuests_setAndReturnCorrectList() {
        List<String> quests = Arrays.asList("Find the ring", "Destroy the ring");
        lobbyGetDTO.setQuests(quests);
        
        assertEquals(quests, lobbyGetDTO.getQuests());
    }

    @Test
    public void getPasswordProtected_setAndReturnCorrectValue() {
        Boolean passwordProtected = true;
        lobbyGetDTO.setPasswordProtected(passwordProtected);
        
        assertTrue(lobbyGetDTO.getPasswordProtected());
    }
}

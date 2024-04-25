package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LobbyGetInformationDTOTest {

    private LobbyGetInformationDTO lobbyGetInformationDTO;
    private GeoCodingData mockGeoCodingData;

    @BeforeEach
    public void setUp() {
        lobbyGetInformationDTO = new LobbyGetInformationDTO();
        mockGeoCodingData = new GeoCodingData();
    }

    @Test
    public void getName_setAndReturnCorrectValue() {
        String name = "Adventure Quest";
        lobbyGetInformationDTO.setName(name);
        
        assertEquals(name, lobbyGetInformationDTO.getName());
    }

    @Test
    public void getQuests_setAndReturnCorrectList() {
        List<String> quests = Arrays.asList("Quest A", "Quest B");
        lobbyGetInformationDTO.setQuests(quests);
        
        assertEquals(quests, lobbyGetInformationDTO.getQuests());
    }

    @Test
    public void getParticipants_setAndReturnCorrectList() {
        List<String> participants = Arrays.asList("User1", "User2");
        lobbyGetInformationDTO.setParticipants(participants);
        
        assertEquals(participants, lobbyGetInformationDTO.getParticipants());
    }

    @Test
    public void getGameLocation_setAndReturnCorrectValue() {
        String gameLocation = "Berlin";
        lobbyGetInformationDTO.setGameLocation(gameLocation);
        
        assertEquals(gameLocation, lobbyGetInformationDTO.getGameLocation());
    }

    @Test
    public void getGameLocationCoordinates_setAndReturnCorrectObject() {
        lobbyGetInformationDTO.setGameLocationCoordinates(mockGeoCodingData);
        
        assertNotNull(lobbyGetInformationDTO.getGameLocationCoordinates());
        assertEquals(mockGeoCodingData, lobbyGetInformationDTO.getGameLocationCoordinates());
    }

    @Test
    public void getRoundDurationSeconds_setAndReturnCorrectValue() {
        Integer roundDurationSeconds = 90;
        lobbyGetInformationDTO.setRoundDurationSeconds(roundDurationSeconds);
        
        assertEquals(roundDurationSeconds, lobbyGetInformationDTO.getRoundDurationSeconds());
    }

    @Test
    public void getAdminUsername_setAndReturnCorrectValue() {
        String adminUsername = "Admin123";
        lobbyGetInformationDTO.setAdminUsername(adminUsername);
        
        assertEquals(adminUsername, lobbyGetInformationDTO.getAdminUsername());
    }
}

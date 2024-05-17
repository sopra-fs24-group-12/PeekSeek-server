package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LobbyGetInformationDTOTest {

    private LobbyGetInformationDTO lobbyGetInformationDTO;
    private GeoCodingData mockGeoCodingData;

    @BeforeEach
    public void setUp() {
        lobbyGetInformationDTO = new LobbyGetInformationDTO();
        mockGeoCodingData = new GeoCodingData();
    }

    @Test
    void getName_setAndReturnCorrectValue() {
        String name = "Adventure Quest";
        lobbyGetInformationDTO.setName(name);
        
        assertEquals(name, lobbyGetInformationDTO.getName());
    }

    @Test
    void getQuests_setAndReturnCorrectList() {
        List<String> quests = Arrays.asList("Quest A", "Quest B");
        lobbyGetInformationDTO.setQuests(quests);
        
        assertEquals(quests, lobbyGetInformationDTO.getQuests());
    }

    @Test
    void getParticipants_setAndReturnCorrectList() {
        List<String> participants = Arrays.asList("User1", "User2");
        lobbyGetInformationDTO.setParticipants(participants);
        
        assertEquals(participants, lobbyGetInformationDTO.getParticipants());
    }

    @Test
    void getGameLocation_setAndReturnCorrectValue() {
        String gameLocation = "Berlin";
        lobbyGetInformationDTO.setGameLocation(gameLocation);
        
        assertEquals(gameLocation, lobbyGetInformationDTO.getGameLocation());
    }

    @Test
    void getGameLocationCoordinates_setAndReturnCorrectObject() {
        lobbyGetInformationDTO.setGameLocationCoordinates(mockGeoCodingData);
        
        assertNotNull(lobbyGetInformationDTO.getGameLocationCoordinates());
        assertEquals(mockGeoCodingData, lobbyGetInformationDTO.getGameLocationCoordinates());
    }

    @Test
    void getRoundDurationSeconds_setAndReturnCorrectValue() {
        Integer roundDurationSeconds = 90;
        lobbyGetInformationDTO.setRoundDurationSeconds(roundDurationSeconds);
        
        assertEquals(roundDurationSeconds, lobbyGetInformationDTO.getRoundDurationSeconds());
    }

    @Test
    void getAdminUsername_setAndReturnCorrectValue() {
        String adminUsername = "Admin123";
        lobbyGetInformationDTO.setAdminUsername(adminUsername);
        
        assertEquals(adminUsername, lobbyGetInformationDTO.getAdminUsername());
    }
}

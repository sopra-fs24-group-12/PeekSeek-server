package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantGetDTOTest {

    private ParticipantGetDTO participantGetDTO;

    @BeforeEach
    public void setUp() {
        participantGetDTO = new ParticipantGetDTO();
    }

    @Test
    void getUsername_setUsername_returnsCorrectValue() {
        String expectedUsername = "player1";
        participantGetDTO.setUsername(expectedUsername);
        
        assertEquals(expectedUsername, participantGetDTO.getUsername(), "The username should match the set value.");
    }

    @Test
    void getScore_setScore_returnsCorrectValue() {
        int expectedScore = 1000;
        participantGetDTO.setScore(expectedScore);
        
        assertEquals(expectedScore, participantGetDTO.getScore(), "The score should match the set value.");
    }

    @Test
    void getStreak_setStreak_returnsCorrectValue() {
        int expectedStreak = 5;
        participantGetDTO.setStreak(expectedStreak);
        
        assertEquals(expectedStreak, participantGetDTO.getStreak(), "The streak should match the set value.");
    }

    @Test
    void getAdmin_setAdmin_returnsCorrectValue() {
        Boolean expectedAdminStatus = true;
        participantGetDTO.setAdmin(expectedAdminStatus);
        
        assertEquals(expectedAdminStatus, participantGetDTO.getAdmin(), "The admin status should match the set value.");
    }

    @Test
    void getLeftGame_setLeftGame_returnsCorrectValue() {
        Boolean expectedLeftGameStatus = true;
        participantGetDTO.setLeftGame(expectedLeftGameStatus);
        
        assertEquals(expectedLeftGameStatus, participantGetDTO.getLeftGame(), "The left game status should match the set value.");
    }

    @Test
    void getId_setId_returnsCorrectValue() {
        Long expectedId = 10L;
        participantGetDTO.setId(expectedId);
        
        assertEquals(expectedId, participantGetDTO.getId(), "The ID should match the set value.");
    }
}

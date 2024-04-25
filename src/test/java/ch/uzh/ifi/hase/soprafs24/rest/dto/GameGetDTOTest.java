package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameGetDTOTest {

    // Create a test subject used in all the test cases
    private GameGetDTO gameGetDTO;

    @BeforeEach
    public void setUp() {
        gameGetDTO = new GameGetDTO();
    }

    @Test
    public void getId_shouldReturnCorrectValue() {
        Long expectedId = 1L;
        gameGetDTO.setId(expectedId);

        assertEquals(expectedId, gameGetDTO.getId());
    }

    @Test
    public void getRoundDurationSeconds_shouldReturnCorrectValue() {
        Integer expectedDuration = 120;
        gameGetDTO.setRoundDurationSeconds(expectedDuration);

        assertEquals(expectedDuration, gameGetDTO.getRoundDurationSeconds());
    }

    @Test
    public void getGameLocation_shouldReturnCorrectValue() {
        String expectedLocation = "Zurich";
        gameGetDTO.setGameLocation(expectedLocation);

        assertEquals(expectedLocation, gameGetDTO.getGameLocation());
    }

    @Test
    public void getCurrentRound_shouldReturnCorrectValue() {
        Integer expectedCurrentRound = 2;
        gameGetDTO.setCurrentRound(expectedCurrentRound);

        assertEquals(expectedCurrentRound, gameGetDTO.getCurrentRound());
    }

    @Test
    public void getNumberRounds_shouldReturnCorrectValue() {
        Integer expectedNumberRounds = 5;
        gameGetDTO.setNumberRounds(expectedNumberRounds);

        assertEquals(expectedNumberRounds, gameGetDTO.getNumberRounds());
    }

    @Test
    public void getAdminId_shouldReturnCorrectValue() {
        Long expectedAdminId = 10L;
        gameGetDTO.setAdminId(expectedAdminId);

        assertEquals(expectedAdminId, gameGetDTO.getAdminId());
    }

    // Uncomment and use these tests if the methods for rounds and participants are enabled later on.
    /*
    @Test
    public void getRounds_shouldReturnCorrectValue() {
        List<Round> expectedRounds = new ArrayList<>();
        expectedRounds.add(new Round()); // Assume constructor and methods of Round are implemented
        gameGetDTO.setRounds(expectedRounds);

        assertTrue(expectedRounds.equals(gameGetDTO.getRounds()));
    }

    @Test
    public void getParticipants_shouldReturnCorrectValue() {
        Map<String, Participant> expectedParticipants = new HashMap<>();
        expectedParticipants.put("player1", new Participant()); // Assume constructor and methods of Participant are implemented
        gameGetDTO.setParticipants(expectedParticipants);

        assertTrue(expectedParticipants.equals(gameGetDTO.getParticipants()));
    }
    */
}

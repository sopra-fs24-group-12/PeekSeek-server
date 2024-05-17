package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameGetDTOTest {

    // Create a test subject used in all the test cases
    private GameGetDTO gameGetDTO;

    @BeforeEach
    public void setUp() {
        gameGetDTO = new GameGetDTO();
    }

    @Test
    void getId_shouldReturnCorrectValue() {
        Long expectedId = 1L;
        gameGetDTO.setId(expectedId);

        assertEquals(expectedId, gameGetDTO.getId());
    }

    @Test
    void getRoundDurationSeconds_shouldReturnCorrectValue() {
        Integer expectedDuration = 120;
        gameGetDTO.setRoundDurationSeconds(expectedDuration);

        assertEquals(expectedDuration, gameGetDTO.getRoundDurationSeconds());
    }

    @Test
    void getGameLocation_shouldReturnCorrectValue() {
        String expectedLocation = "Zurich";
        gameGetDTO.setGameLocation(expectedLocation);

        assertEquals(expectedLocation, gameGetDTO.getGameLocation());
    }

    @Test
    void getCurrentRound_shouldReturnCorrectValue() {
        Integer expectedCurrentRound = 2;
        gameGetDTO.setCurrentRound(expectedCurrentRound);

        assertEquals(expectedCurrentRound, gameGetDTO.getCurrentRound());
    }

    @Test
    void getNumberRounds_shouldReturnCorrectValue() {
        Integer expectedNumberRounds = 5;
        gameGetDTO.setNumberRounds(expectedNumberRounds);

        assertEquals(expectedNumberRounds, gameGetDTO.getNumberRounds());
    }

    @Test
    void getAdminId_shouldReturnCorrectValue() {
        Long expectedAdminId = 10L;
        gameGetDTO.setAdminId(expectedAdminId);

        assertEquals(expectedAdminId, gameGetDTO.getAdminId());
    }
}

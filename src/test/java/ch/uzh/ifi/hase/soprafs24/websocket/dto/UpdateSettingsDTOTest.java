package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateSettingsDTOTest {

    private UpdateSettingsDTO updateSettingsDTO;
    private String expectedGameLocation;
    private Integer expectedRoundDurationSeconds;
    private GeoCodingData expectedGameLocationCoordinates;
    private List<String> expectedQuests;

    @BeforeEach
    public void setUp() {
        expectedGameLocation = "Zurich";
        expectedRoundDurationSeconds = 300;
        expectedGameLocationCoordinates = new GeoCodingData(); // Assume GeoCodingData has a proper implementation
        expectedQuests = Arrays.asList("Fountain", "Statue");

        // Full constructor is used for initialization
        updateSettingsDTO = new UpdateSettingsDTO(
                expectedGameLocation,
                expectedRoundDurationSeconds,
                expectedGameLocationCoordinates,
                expectedQuests
        );
    }

    @Test
    public void constructor_shouldCorrectlySetFields() {
        assertEquals(expectedGameLocation, updateSettingsDTO.getGameLocation());
        assertEquals(expectedRoundDurationSeconds, updateSettingsDTO.getRoundDurationSeconds());
        assertEquals(expectedGameLocationCoordinates, updateSettingsDTO.getGameLocationCoordinates());
        assertEquals(expectedQuests, updateSettingsDTO.getQuests());
        assertEquals("update", updateSettingsDTO.getStatus()); // Default status should be set to "update"
    }

    @Test
    public void setStatus_shouldUpdateStatusField() {
        String newStatus = "active";
        
        updateSettingsDTO.setStatus(newStatus);

        assertEquals(newStatus, updateSettingsDTO.getStatus());
    }

    @Test
    public void setGameLocation_shouldUpdateGameLocationField() {
        String newGameLocation = "Bern";

        updateSettingsDTO.setGameLocation(newGameLocation);

        assertEquals(newGameLocation, updateSettingsDTO.getGameLocation());
    }

    @Test
    public void setRoundDurationSeconds_shouldUpdateRoundDurationSecondsField() {
        Integer newRoundDurationSeconds = 600;

        updateSettingsDTO.setRoundDurationSeconds(newRoundDurationSeconds);

        assertEquals(newRoundDurationSeconds, updateSettingsDTO.getRoundDurationSeconds());
    }

    @Test
    public void setGameLocationCoordinates_shouldUpdateGameLocationCoordinatesField() {
        GeoCodingData newGameLocationCoordinates = new GeoCodingData(); // Again, assuming proper implementation

        updateSettingsDTO.setGameLocationCoordinates(newGameLocationCoordinates);

        assertEquals(newGameLocationCoordinates, updateSettingsDTO.getGameLocationCoordinates());
    }

    @Test
    public void setQuests_shouldUpdateQuestsField() {
        List<String> newQuests = Arrays.asList("Visit the old town", "Explore the lake");

        updateSettingsDTO.setQuests(newQuests);

        assertEquals(newQuests, updateSettingsDTO.getQuests());
    }
}

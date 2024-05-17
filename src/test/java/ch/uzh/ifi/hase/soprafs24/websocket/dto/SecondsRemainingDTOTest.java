package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SecondsRemainingDTOTest {

    @Test
    void whenInstantiated_thenSecondsRemainingIsSetCorrectly() {
        // Arrange & Act
        SecondsRemainingDTO secondsRemainingDTO = new SecondsRemainingDTO(30);

        // Assert
        assertEquals(30, secondsRemainingDTO.getSecondsRemaining());
    }

    @Test
    void whenSettingSecondsRemaining_thenValueShouldBeUpdated() {
        // Arrange
        SecondsRemainingDTO secondsRemainingDTO = new SecondsRemainingDTO(30);

        // Act
        secondsRemainingDTO.setSecondsRemaining(15);

        // Assert
        assertEquals(15, secondsRemainingDTO.getSecondsRemaining());
    }
}

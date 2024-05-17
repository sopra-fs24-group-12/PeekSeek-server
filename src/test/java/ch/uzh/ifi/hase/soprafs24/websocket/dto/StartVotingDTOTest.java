package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StartVotingDTOTest {

    @Test
    void onInstantiation_StatusShouldBeVoting() {
        // Arrange & Act
        StartVotingDTO startVotingDTO = new StartVotingDTO();

        // Assert
        assertEquals("voting", startVotingDTO.getStatus());
    }

    @Test
    void whenStatusIsSet_thenStatusValueShouldBeUpdated() {
        // Arrange
        StartVotingDTO startVotingDTO = new StartVotingDTO();

        // Act
        String newStatus = "completed";
        startVotingDTO.setStatus(newStatus);

        // Assert
        assertEquals(newStatus, startVotingDTO.getStatus());
    }
}

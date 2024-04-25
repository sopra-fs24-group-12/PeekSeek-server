package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StartVotingDTOTest {

    @Test
    public void onInstantiation_StatusShouldBeVoting() {
        // Arrange & Act
        StartVotingDTO startVotingDTO = new StartVotingDTO();

        // Assert
        assertEquals("voting", startVotingDTO.getStatus());
    }

    @Test
    public void whenStatusIsSet_thenStatusValueShouldBeUpdated() {
        // Arrange
        StartVotingDTO startVotingDTO = new StartVotingDTO();

        // Act
        String newStatus = "completed";
        startVotingDTO.setStatus(newStatus);

        // Assert
        assertEquals(newStatus, startVotingDTO.getStatus());
    }
}

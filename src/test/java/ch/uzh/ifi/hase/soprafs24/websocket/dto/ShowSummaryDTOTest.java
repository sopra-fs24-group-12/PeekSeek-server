package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ShowSummaryDTOTest {

    @Test
    void onInstantiation_StatusShouldBeSummary() {
        // Arrange & Act
        ShowSummaryDTO showSummaryDTO = new ShowSummaryDTO();

        // Assert
        assertEquals("summary", showSummaryDTO.getStatus());
    }

    @Test
    void whenChangingStatus_thenStatusValueShouldBeUpdated() {
        // Arrange
        ShowSummaryDTO showSummaryDTO = new ShowSummaryDTO();

        // Act
        String newStatus = "updatedSummary";
        showSummaryDTO.setStatus(newStatus);

        // Assert
        assertEquals(newStatus, showSummaryDTO.getStatus());
    }
}

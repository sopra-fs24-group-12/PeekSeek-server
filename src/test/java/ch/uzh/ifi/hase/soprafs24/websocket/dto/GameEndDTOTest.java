package ch.uzh.ifi.hase.soprafs24.websocket.dto;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameEndDTOTest {

    private GameEndDTO gameEndDTO;
    private final Long summaryId = 12345L; // Example summary ID

    @BeforeEach
    void setUp() {
        gameEndDTO = new GameEndDTO(summaryId);
    }

    @Test
    void statusShouldBeGameOverByDefault() {
        assertEquals("game_over", gameEndDTO.getStatus(), "The status should be 'game_over' by default");
    }

    @Test
    void constructWithSummaryIdShouldSetSummaryId() {
        assertEquals(summaryId, gameEndDTO.getSummaryId(), "Constructor should set the summary ID correctly");
    }

    @Test
    void setStatusShouldChangeStatus() {
        String newStatus = "finished";
        gameEndDTO.setStatus(newStatus);
        assertEquals(newStatus, gameEndDTO.getStatus(), "setStatus should change the status");
    }

    @Test
    void setSummaryIdShouldChangeSummaryId() {
        Long newSummaryId = 67890L;
        gameEndDTO.setSummaryId(newSummaryId);
        assertEquals(newSummaryId, gameEndDTO.getSummaryId(), "setSummaryId should update the summary ID");
    }
}

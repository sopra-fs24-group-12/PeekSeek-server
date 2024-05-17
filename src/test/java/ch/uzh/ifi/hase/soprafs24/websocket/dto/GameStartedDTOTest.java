package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStartedDTOTest {

    private GameStartedDTO gameStartedDTO;
    private final Long gameId = 1L; // Example game ID for testing

    @BeforeEach
    public void setUp() {
        gameStartedDTO = new GameStartedDTO(gameId);
    }

    @Test
    void statusShouldBeStartedByDefault() {
        assertEquals("started", gameStartedDTO.getStatus(), "The status should be 'started' by default.");
    }

    @Test
    void constructWithGameIdShouldSetGameId() {
        assertEquals(gameId, gameStartedDTO.getGameId(), "Constructor should set the game ID correctly.");
    }

    @Test
    void setStatusShouldChangeStatus() {
        String newStatus = "in_progress";
        gameStartedDTO.setStatus(newStatus);
        assertEquals(newStatus, gameStartedDTO.getStatus(), "setStatus should change the status.");
    }

    @Test
    void setGameIdShouldChangeGameId() {
        Long newGameId = 2L;
        gameStartedDTO.setGameId(newGameId);
        assertEquals(newGameId, gameStartedDTO.getGameId(), "setGameId should update the game ID.");
    }
}

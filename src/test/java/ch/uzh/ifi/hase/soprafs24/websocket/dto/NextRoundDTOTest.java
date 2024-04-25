package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextRoundDTOTest {

    @Test
    public void testGetStatus_initialValue() {
        NextRoundDTO nextRoundDTO = new NextRoundDTO();
        assertEquals("round", nextRoundDTO.getStatus(), "Initial status should be 'round'");
    }

    @Test
    public void testSetAndGetStatus() {
        NextRoundDTO nextRoundDTO = new NextRoundDTO();

        String testStatus = "newStatus";
        nextRoundDTO.setStatus(testStatus);

        assertEquals(testStatus, nextRoundDTO.getStatus(), "The status should be equal to the set value");
    }
}

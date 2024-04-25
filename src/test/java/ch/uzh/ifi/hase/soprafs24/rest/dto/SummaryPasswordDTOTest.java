package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SummaryPasswordDTOTest {

    private SummaryPasswordDTO summaryPasswordDTO;

    @BeforeEach
    public void setUp() {
        summaryPasswordDTO = new SummaryPasswordDTO();
    }

    @Test
    public void getPassword_setPassword_returnsCorrectValue() {
        String password = "SecurePass123!";
        summaryPasswordDTO.setPassword(password);
        
        assertEquals(password, summaryPasswordDTO.getPassword(), "The password should match the set value.");
    }
}

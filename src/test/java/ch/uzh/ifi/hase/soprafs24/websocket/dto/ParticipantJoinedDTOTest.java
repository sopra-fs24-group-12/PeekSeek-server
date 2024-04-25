package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParticipantJoinedDTOTest {

    private ParticipantJoinedDTO participantJoined;

    @BeforeEach
    public void setUp() {
        // Initialize the DTO with a sample username before each test
        participantJoined = new ParticipantJoinedDTO("SampleUser");
    }

    @Test
    public void whenInstantiated_thenDefaultStatusIsJoined() {
        // Assert that the default status is "joined"
        assertEquals("joined", participantJoined.getStatus());
    }

    @Test
    public void whenInstantiated_thenUsernameIsSetCorrectly() {
        // Assert that the provided username is set correctly
        assertEquals("SampleUser", participantJoined.getUsername());
    }

    @Test
    public void whenSettingStatus_thenStatusShouldBeUpdated() {
        // Set a new status
        participantJoined.setStatus("left");
        // Assert that the status is updated correctly
        assertEquals("left", participantJoined.getStatus());
    }

    @Test
    public void whenSettingUsername_thenUsernameShouldBeUpdated() {
        // Set a new username
        participantJoined.setUsername("NewUser");
        // Assert that the username is updated correctly
        assertEquals("NewUser", participantJoined.getUsername());
    }
}

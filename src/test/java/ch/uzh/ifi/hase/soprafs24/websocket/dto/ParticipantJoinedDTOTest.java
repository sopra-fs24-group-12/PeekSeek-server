package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ParticipantJoinedDTOTest {

    private ParticipantJoinedDTO participantJoined;
    private final List<String> usernames = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        usernames.add("SampleUser");
        participantJoined = new ParticipantJoinedDTO(usernames);
    }

    @Test
    void whenInstantiated_thenDefaultStatusIsJoined() {
        // Assert that the default status is "joined"
        assertEquals("joined", participantJoined.getStatus());
    }

    @Test
    void whenInstantiated_thenUsernameIsSetCorrectly() {
        // Assert that the provided username is set correctly
        assertEquals("SampleUser", participantJoined.getUsernames().get(0));
    }

    @Test
    void whenSettingStatus_thenStatusShouldBeUpdated() {
        // Set a new status
        participantJoined.setStatus("left");
        // Assert that the status is updated correctly
        assertEquals("left", participantJoined.getStatus());
    }

    @Test
    void whenSettingUsername_thenUsernameShouldBeUpdated() {
        usernames.add("NewUser");
        assertEquals("NewUser", participantJoined.getUsernames().get(1));
    }
}

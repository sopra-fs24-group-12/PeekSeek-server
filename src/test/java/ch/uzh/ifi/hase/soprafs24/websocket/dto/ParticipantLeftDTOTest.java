package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ParticipantLeftDTOTest {

    @Test
    public void whenInstantiated_thenUsernameIsSetCorrectly() {
        // Arrange & Act
        String testUsername = "testUser";
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO(testUsername);

        // Assert
        assertEquals(testUsername, participantLeftDTO.getUsername());
    }

    @Test
    public void onInstantiation_StatusShouldBeLeft() {
        // Arrange & Act
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO("testUser");

        // Assert
        assertEquals("left", participantLeftDTO.getStatus());
    }

    @Test
    public void whenChangingStatus_thenStatusValueShouldBeUpdated() {
        // Arrange
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO("testUser");

        // Act
        String newStatus = "disconnected";
        participantLeftDTO.setStatus(newStatus);

        // Assert
        assertEquals(newStatus, participantLeftDTO.getStatus());
    }

    @Test
    public void whenChangingUsername_thenUsernameShouldBeUpdated() {
        // Arrange
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO("testUser");

        // Act
        String newUsername = "newUser";
        participantLeftDTO.setUsername(newUsername);

        // Assert
        assertEquals(newUsername, participantLeftDTO.getUsername());
    }

    @Test
    public void whenSettingNewAdmin_thenNewAdminShouldBeSet() {
        // Arrange
        ParticipantLeftDTO participantLeftDTO = new ParticipantLeftDTO("testUser");

        // Pre-Assert
        assertNull(participantLeftDTO.getNewAdmin());

        // Act
        String newAdmin = "adminUser";
        participantLeftDTO.setNewAdmin(newAdmin);

        // Assert
        assertEquals(newAdmin, participantLeftDTO.getNewAdmin());
    }
}

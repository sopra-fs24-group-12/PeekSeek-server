package ch.uzh.ifi.hase.soprafs24.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    @Test
    void testConstructor() {
        Participant participant = new Participant();
        participant.setId(1L);
        assertEquals(1L, participant.getId());
    }

    @Test
    void testSettersAndGetters() {
        Participant participant = new Participant();

        participant.setUsername("testUser");
        participant.setToken("abc123");
        participant.setLobbyId(10L);
        participant.setScore(100);
        participant.setWinningSubmissions(5);
        participant.setStreak(2);
        participant.setAdmin(true);
        participant.setLeftGame(false);
        participant.setHasSubmitted(true);
        participant.setHasVoted(true);
        participant.setPointsThisRound(20);

        assertEquals("testUser", participant.getUsername());
        assertEquals("abc123", participant.getToken());
        assertEquals(10L, participant.getLobbyId());
        assertEquals(100, participant.getScore());
        assertEquals(5, participant.getWinningSubmissions());
        assertEquals(2, participant.getStreak());
        assertTrue(participant.getAdmin());
        assertFalse(participant.getLeftGame());
        assertTrue(participant.getHasSubmitted());
        assertTrue(participant.getHasVoted());
        assertEquals(20, participant.getPointsThisRound());
    }
}

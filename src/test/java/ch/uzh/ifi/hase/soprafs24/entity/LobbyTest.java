package ch.uzh.ifi.hase.soprafs24.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class LobbyTest {
    @Test
    public void testConstructor() {
        Lobby lobby = new Lobby();
        lobby.setId(1L);
        assertEquals(1L, lobby.getId());
        assertFalse(lobby.getReUsed());
        assertEquals(0, lobby.getJoinedParticipants());
    }

    @Test
    public void testGetterSetters() {
        Lobby lobby = new Lobby();
        List<String> l = new ArrayList<>();
        l.add("1");
        l.add("2");

        lobby.setName("Test Lobby");
        lobby.setPassword("secret");
        lobby.setRoundDurationSeconds(120);
        lobby.setGameLocation("Zurich");
        lobby.setMaxParticipants(10);
        lobby.setQuests(l);
        lobby.setAdminId(1L);
        lobby.setPasswordProtected(true);
        lobby.setAdminUsername("adminUser");

        assertEquals("Test Lobby", lobby.getName());
        assertEquals("secret", lobby.getPassword());
        assertEquals(122, lobby.getRoundDurationSeconds());
        assertEquals("Zurich", lobby.getGameLocation());
        assertEquals(10, lobby.getMaxParticipants());
        assertEquals(2, lobby.getQuests().size());
        assertEquals(1L, lobby.getAdminId());
        assertTrue(lobby.getPasswordProtected());
        assertEquals("adminUser", lobby.getAdminUsername());
    }


    @Test
    public void testAddParticipant() {
        Lobby lobby = new Lobby();
        Participant participant = new Participant();
        participant.setToken("testToken");
        participant.setUsername("testUser");

        lobby.addParticipant(participant);

        assertEquals(1, lobby.getParticipants().size());
        assertEquals(participant, lobby.getParticipantByToken("testToken"));
        assertTrue(lobby.getUsernames().contains("testUser"));
        assertEquals(1, lobby.getJoinedParticipants());
    }

    @Test
    public void testRemoveParticipant() {
        Lobby lobby = new Lobby();
        Participant participant = new Participant();
        participant.setToken("testToken");
        participant.setUsername("testUser");

        lobby.addParticipant(participant);
        lobby.removeParticipant("testToken");

        assertEquals(0, lobby.getParticipants().size());
        assertNull(lobby.getParticipantByToken("testToken"));
        assertFalse(lobby.getUsernames().contains("testUser"));
        assertEquals(0, lobby.getJoinedParticipants());
    }
    /*
    @Test
    public void testUpdateActivityTime() {
        Lobby lobby = new Lobby();
        Participant participant = new Participant();
        participant.setToken("testToken");

        lobby.addParticipant(participant);
        long beforeTime = System.currentTimeMillis();
        lobby.updateActivityTime("testToken");
        long afterTime = System.currentTimeMillis();

        assertTrue(lobby.getLastActivityTimes().get("testToken") >= beforeTime);
        assertTrue(lobby.getLastActivityTimes().get("testToken") <= afterTime);
    }

    @Test
    public void testRemoveInactiveParticipants() {
        Lobby lobby = new Lobby();
        Participant participant1 = new Participant();
        participant1.setToken("token1");
        Participant participant2 = new Participant();
        participant2.setToken("token2");

        lobby.addParticipant(participant1);
        lobby.addParticipant(participant2);
        lobby.updateActivityTime("token1");  // Simulate activity for participant1

        // Set timeout to a low value to ensure participant2 is detected as inactive
        List<String> inactiveParticipants = lobby.removeInactiveParticipants(100);

        assertEquals(1, lobby.getParticipants().size());
        assertTrue(lobby.getParticipants().containsKey("token1"));
        assertFalse(lobby.getParticipants().containsKey("token2"));
        assertEquals(1, lobby.getJoinedParticipants());
        assertEquals(1, inactiveParticipants.size());
        assertTrue(inactiveParticipants.contains("token2"));
    }
    */
    @Test
    public void testResetLobby() throws InterruptedException {
        Lobby lobby = new Lobby();
        Participant participant = new Participant();
        participant.setToken("testToken");
        lobby.addParticipant(participant);
        lobby.setAdminUsername("adminUser");


        Thread.sleep(2000);

        lobby.resetLobby();

        Thread.sleep(6000);

        assertEquals(0, lobby.getJoinedParticipants());
        assertNull(lobby.getParticipantByToken("testToken"));
        assertTrue(lobby.getReUsed());
        assertNull(lobby.getAdminUsername());
    }

}
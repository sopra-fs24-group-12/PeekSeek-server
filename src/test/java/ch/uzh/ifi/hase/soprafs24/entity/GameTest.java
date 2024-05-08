package ch.uzh.ifi.hase.soprafs24.entity;
import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testAddParticipant() {
        // Create participant
        Participant participant = new Participant();
        String token = "token123";
        participant.setToken(token);

        // Add participant to the game
        game.getParticipants().put(token, participant);

        // Check if participant is added
        assertEquals(participant, game.getParticipantByToken(token));
    }


    @Test
    public void testNumberOfRounds() {
        game.getRounds().add(new Round());
        game.getRounds().add(new Round());
        game.getRounds().add(new Round());


        assertEquals(3, game.getRounds().size());
    }

    @Test
    public void testGameStatus() {
        game.setGameStatus(GameStatus.SUMMARY);


        assertEquals(GameStatus.SUMMARY, game.getGameStatus());
    }

    @Test
    public void testSetAndGetRoundDurationSeconds() {
        Integer roundDurationSeconds = 60;
        game.setRoundDurationSeconds(roundDurationSeconds);
        assertEquals(roundDurationSeconds, game.getRoundDurationSeconds());
    }

    @Test
    public void testSetAndGetGameLocation() {
        String gameLocation = "Location XYZ";
        game.setGameLocation(gameLocation);
        assertEquals(gameLocation, game.getGameLocation());
    }

    @Test
    public void testSetAndGetCurrentRound() {
        Integer currentRound = 1;
        game.setCurrentRound(currentRound);
        assertEquals(currentRound, game.getCurrentRound());
    }

    @Test
    public void testSetAndGetNumberRounds() {
        Integer numberRounds = 5;
        game.setNumberRounds(numberRounds);
        assertEquals(numberRounds, game.getNumberRounds());
    }

    @Test
    public void testSetAndGetAdminId() {
        Long adminId = 456L;
        game.setAdminId(adminId);
        assertEquals(adminId, game.getAdminId());
    }



    @Test
    public void testSetAndGetRounds() {
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round());
        game.setRounds(rounds);
        assertEquals(rounds, game.getRounds());
    }

    @Test
    public void testSetAndGetParticipants() {
        Map<String, Participant> participants = new HashMap<>();
        participants.put("token123", new Participant());
        game.setParticipants(participants);
        assertEquals(participants, game.getParticipants());
    }

    @Test
    public void testSetAndGetLobbyPassword() {
        String lobbyPassword = "password123";
        game.setLobbyPassword(lobbyPassword);
        assertEquals(lobbyPassword, game.getLobbyPassword());
    }

    @Test
    public void testSetAndGetActiveParticipants() {
        Integer activeParticipants = 5;
        game.setActiveParticipants(activeParticipants);
        assertEquals(activeParticipants, game.getActiveParticipants());
    }
}

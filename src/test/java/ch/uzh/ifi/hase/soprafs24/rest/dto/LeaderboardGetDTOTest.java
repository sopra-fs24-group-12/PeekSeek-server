package ch.uzh.ifi.hase.soprafs24.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeaderboardGetDTOTest {

    private LeaderboardGetDTO leaderboardGetDTO;

    @BeforeEach
    void setUp() {
        leaderboardGetDTO = new LeaderboardGetDTO();
    }

    @Test
    void getUsernameShouldReturnCorrectUsername() {
        String username = "testUser";
        leaderboardGetDTO.setUsername(username);
        assertEquals(username, leaderboardGetDTO.getUsername(), "getUsername should return the correct username.");
    }

    @Test
    void getScoreShouldReturnCorrectScore() {
        int score = 100;
        leaderboardGetDTO.setScore(score);
        assertEquals(score, leaderboardGetDTO.getScore(), "getScore should return the correct score.");
    }

    @Test
    void getStreakShouldReturnCorrectStreak() {
        int streak = 5;
        leaderboardGetDTO.setStreak(streak);
        assertEquals(streak, leaderboardGetDTO.getStreak(), "getStreak should return the correct streak.");
    }

    //@Test
    //public void getPositionShouldReturnCorrectPosition() {
    //   int position = 1;
    //    leaderboardGetDTO.setPosition(position);
    //    assertEquals(position, leaderboardGetDTO.getPosition(), "getPosition should return the correct position.");
    //}

    @Test
    void getIdShouldReturnCorrectId() {
        Long id = 10L;
        leaderboardGetDTO.setId(id);
        assertEquals(id, leaderboardGetDTO.getId(), "getId should return the correct ID.");
    }

    @Test
    void getPointsThisRoundShouldReturnCorrectPoints() {
        int pointsThisRound = 20;
        leaderboardGetDTO.setPointsThisRound(pointsThisRound);
        assertEquals(pointsThisRound, leaderboardGetDTO.getPointsThisRound(), "getPointsThisRound should return the correct points for this round.");
    }

    @Test
    void getLeftGameShouldReturnCorrectStatus() {
        boolean leftGame = true;
        leaderboardGetDTO.setLeftGame(leftGame);
        assertEquals(leftGame, leaderboardGetDTO.getLeftGame(), "getLeftGame should return the correct status for this player.");
    }
}

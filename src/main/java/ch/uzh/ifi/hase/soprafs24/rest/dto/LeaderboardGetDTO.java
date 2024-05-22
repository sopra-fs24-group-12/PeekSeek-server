package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class LeaderboardGetDTO {
    private String username;
    private int score;
    private int streak;
    private int position;
    private Boolean leftGame;
    private Long id;
    private int pointsThisRound;

    public int getPointsThisRound() {
        return pointsThisRound;
    }

    public void setPointsThisRound(int pointsThisRound) {
        this.pointsThisRound = pointsThisRound;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLeftGame() {
        return leftGame;
    }

    public void setLeftGame(Boolean leftGame) {
        this.leftGame = leftGame;
    }
}

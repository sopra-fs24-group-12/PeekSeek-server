package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.entity.Submission;

public class Participant {
    private final String username;
    private final long id;
    private final String token;
    private int score;
    private int winningSubmissions;
    private List<Submission> submissions;
    private int streak;
    private final Boolean isAdmin;
    private Boolean leftGame;

    public Participant(String username, long id, String token, Boolean isAdmin) {
        this.username = username;
        this.id = id;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWinningSubmissions() {
        return winningSubmissions;
    }

    public void setWinningSubmissions(int winningSubmissions) {
        this.winningSubmissions = winningSubmissions;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Boolean getLeftGame() {
        return leftGame;
    }

    public void setLeftGame(Boolean leftGame) {
        this.leftGame = leftGame;
    }
}

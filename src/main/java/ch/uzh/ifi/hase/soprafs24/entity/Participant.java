package ch.uzh.ifi.hase.soprafs24.entity;

public class Participant {
    private Long id;
    private String username;
    private String token;
    private Long lobbyId;
    private int score = 0;
    private int winningSubmissions = 0;
    private int streak = 0;
    private Boolean isAdmin = false;
    private Boolean leftGame = false;
    private Boolean hasSubmitted = false;
    private Boolean hasVoted = false;
    private static Long idCount = 1L;
    private int pointsThisRound = 0;


    public Participant() {
        this.id = idCount++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
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

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getLeftGame() {
        return leftGame;
    }

    public void setLeftGame(Boolean leftGame) {
        this.leftGame = leftGame;
    }

    public Boolean getHasSubmitted() {
        return hasSubmitted;
    }

    public void setHasSubmitted(Boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    public boolean getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(Boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public int getPointsThisRound() {
        return pointsThisRound;
    }

    public void setPointsThisRound(int pointsThisRound) {
        this.pointsThisRound = pointsThisRound;
    }
}

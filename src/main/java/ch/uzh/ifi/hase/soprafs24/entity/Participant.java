package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.entity.Submission;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Participant {
    private String username;
    @Column(nullable = false, unique = true)
    private String token;
    private int score = 0;
    private int winningSubmissions = 0;
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();
    private int streak = 0;
    private Boolean isAdmin = false;
    private Boolean leftGame = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "lobby_id")
    private Long lobby;
    @JoinColumn(name = "game_id")
    private Long game;

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

    public Boolean getIsAdmin() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLobby() {
        return lobby;
    }

    public void setLobby(Long lobby) {
        this.lobby = lobby;
    }

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }
}

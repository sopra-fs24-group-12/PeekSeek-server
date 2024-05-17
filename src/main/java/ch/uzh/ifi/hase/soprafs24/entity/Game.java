package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {
    private Long id;
    private Integer roundDurationSeconds;
    private String gameLocation;
    private Integer currentRound = -1;
    private Integer numberRounds;
    private Long adminId;
    private GameStatus gameStatus = GameStatus.RUNNING;
    private List<Round> rounds = new ArrayList<>();
    private Map<String, Participant> participants = new HashMap<>();
    private static Long idCount = 1L;
    private static Long roundsCount = 1L;
    private String lobbyPassword;
    private Map<String, Long> lastActivityTimes = new HashMap<>();
    private Integer activeParticipants;

    public Game() {
        this.id = idCount++;
    }

    public static Long getRoundsCount() {
        return roundsCount;
    }

    public static void setRoundsCount(Long roundsCount) {
        Game.roundsCount = roundsCount;
    }

    public void initializeActivityTime() {
        for (Participant p : participants.values()) {
            lastActivityTimes.put(p.getToken(), System.currentTimeMillis());
        }
    }

    public void updateActivityTime(String token) {
        lastActivityTimes.put(token, System.currentTimeMillis());
    }

    public List<String> removeInactiveParticipants(long timeout) {
        long currentTime = System.currentTimeMillis();
        List<String> inactiveParticipants = new ArrayList<>();
        for (Map.Entry<String, Long> entry : lastActivityTimes.entrySet()) {
            if (!participants.get(entry.getKey()).getLeftGame() && currentTime - entry.getValue() > timeout) {
                inactiveParticipants.add(entry.getKey());
            }
        }

        return inactiveParticipants;
    }

    public Participant getParticipantByToken(String token) {
        return participants.get(token);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoundDurationSeconds() {
        return roundDurationSeconds;
    }

    public void setRoundDurationSeconds(Integer roundDurationSeconds) {
        this.roundDurationSeconds = roundDurationSeconds;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Integer getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(Integer numberRounds) {
        this.numberRounds = numberRounds;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Map<String, Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Participant> participants) {
        this.participants = participants;
    }

    public String getLobbyPassword() {
        return lobbyPassword;
    }

    public void setLobbyPassword(String lobbyPassword) {
        this.lobbyPassword = lobbyPassword;
    }

    public Integer getActiveParticipants() {
        return activeParticipants;
    }

    public void setActiveParticipants(Integer activeParticipants) {
        this.activeParticipants = activeParticipants;
    }
}

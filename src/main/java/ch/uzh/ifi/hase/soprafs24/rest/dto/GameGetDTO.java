package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class GameGetDTO {
    private Long id;
    private Integer roundDurationSeconds;
    private String gameLocation;
    private Integer currentRound;
    private Integer numberRounds;
    private Long adminId;

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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Integer getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(Integer numberRounds) {
        this.numberRounds = numberRounds;
    }
}
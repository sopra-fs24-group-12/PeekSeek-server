package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.List;

public class LobbyGetDTO {
    private Long id;
    private String name;
    private Integer maxParticipants;
    private Integer joinedParticipants;
    private Integer RoundDurationSeconds;
    private String gameLocation;
    private List<String> quests;
    private Boolean passwordProtected;

    public Boolean getPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(Boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }

    public Integer getRoundDurationSeconds() {
        return RoundDurationSeconds;
    }

    public void setRoundDurationSeconds(Integer roundDurationSeconds) {
        RoundDurationSeconds = roundDurationSeconds;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getJoinedParticipants() {
        return joinedParticipants;
    }

    public void setJoinedParticipants(Integer joinedParticipants) {
        this.joinedParticipants = joinedParticipants;
    }
}

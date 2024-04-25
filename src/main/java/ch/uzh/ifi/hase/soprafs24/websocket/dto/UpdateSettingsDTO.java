package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import java.util.List;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

public class UpdateSettingsDTO {
    private String status = "update";
    private String gameLocation;
    private Integer roundDurationSeconds;
    private GeoCodingData gameLocationCoordinates;
    private List<String> quests;

    public UpdateSettingsDTO(String gameLocation, Integer roundDurationSeconds, GeoCodingData gameLocationCoordinates,
                             List<String> quests) {
        this.gameLocation = gameLocation;
        this.roundDurationSeconds = roundDurationSeconds;
        this.gameLocationCoordinates = gameLocationCoordinates;
        this.quests = quests;
        
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Integer getRoundDurationSeconds() {
        return roundDurationSeconds;
    }

    public void setRoundDurationSeconds(Integer roundDurationSeconds) {
        this.roundDurationSeconds = roundDurationSeconds;
    }

    public GeoCodingData getGameLocationCoordinates() {
        return gameLocationCoordinates;
    }

    public void setGameLocationCoordinates(GeoCodingData gameLocationCoordinates) {
        this.gameLocationCoordinates = gameLocationCoordinates;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }
}
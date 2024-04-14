package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.List;

public class LobbyPutDTO {
    private Integer roundDurationSeconds;
    private List<String> quests;
    private String gameLocation;
    private GeoCodingGetDTO gameLocationCoordinates;

    public Integer getRoundDurationSeconds() {
        return roundDurationSeconds;
    }

    public void setRoundDurationSeconds(Integer roundDurationSeconds) {
        this.roundDurationSeconds = roundDurationSeconds;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public GeoCodingGetDTO getGameLocationCoordinates() {
        return gameLocationCoordinates;
    }

    public void setGameLocationCoordinates(GeoCodingGetDTO gameLocationCoordinates) {
        this.gameLocationCoordinates = gameLocationCoordinates;
    }
}

package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

import java.util.List;

public class LobbyGetInformationDTO {
    private String name;
    private List<String> quests;
    private List<String> participants;
    private String gameLocation;
    private GeoCodingData gameLocationCoordinates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public GeoCodingData getGameLocationCoordinates() {
        return gameLocationCoordinates;
    }

    public void setGameLocationCoordinates(GeoCodingData gameLocationCoordinates) {
        this.gameLocationCoordinates = gameLocationCoordinates;
    }
}

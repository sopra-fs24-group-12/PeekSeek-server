package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;

import java.util.List;

public class SummaryGetDTO {
    private Long id;
    private String cityName;
    private int roundsPlayed;
    private List<Quest> quests;
    private String resLatNe;
    private String resLngNe;
    private String resLatSw;
    private String resLngSw;
    private String lat;
    private String lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public String getResLatNe() {
        return resLatNe;
    }

    public void setResLatNe(String resLatNe) {
        this.resLatNe = resLatNe;
    }

    public String getResLngNe() {
        return resLngNe;
    }

    public void setResLngNe(String resLngNe) {
        this.resLngNe = resLngNe;
    }

    public String getResLatSw() {
        return resLatSw;
    }

    public void setResLatSw(String resLatSw) {
        this.resLatSw = resLatSw;
    }

    public String getResLngSw() {
        return resLngSw;
    }

    public void setResLngSw(String resLngSw) {
        this.resLngSw = resLngSw;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

public class RoundGetDTO {
    private String quest;
    private Integer currentRound;
    private Integer numberRounds;
    private RoundStatus roundStatus;
    private Integer roundTime;
    private Integer remainingSeconds;
    private GeoCodingData geoCodingData;

    public Integer getCurrentRound() {
        return currentRound + 1;
    }

    public Integer getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(Integer numberRounds) {
        this.numberRounds = numberRounds;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public Integer getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(Integer roundTime) {
        this.roundTime = roundTime;
    }

    public Integer getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public GeoCodingData getGeoCodingData() {
        return geoCodingData;
    }

    public void setGeoCodingData(GeoCodingData geoCodingData) {
        this.geoCodingData = geoCodingData;
    }
}

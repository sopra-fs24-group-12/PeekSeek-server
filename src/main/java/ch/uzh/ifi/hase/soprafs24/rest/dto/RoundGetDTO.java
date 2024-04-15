package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;

public class RoundGetDTO {
    private String quest;
    private Integer currentRound;
    private Integer numberRounds;
    private RoundStatus roundStatus;
    private Integer remainingSeconds;

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

    public Integer getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }
}

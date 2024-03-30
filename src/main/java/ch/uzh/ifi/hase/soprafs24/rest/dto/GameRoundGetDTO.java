package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class GameRoundGetDTO {
    private String quest;
    private Integer currentRound;

    private Integer numberRounds;

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
}

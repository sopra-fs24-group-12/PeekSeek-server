package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class GameGetDTO {
    private Long id;
    private Integer roundDurationSeconds;
    private String gameLocation;
    private Integer currentRound;
    private Integer numberRounds;
    private Long adminId;

    private List<Round> rounds = new ArrayList<>();
    private List<Participant> participants = new ArrayList<>();


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

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Integer getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(Integer numberRounds) {
        this.numberRounds = numberRounds;
    }
}
package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Submission {
    private Integer submissionTimeSeconds;
    private SubmissionData submittedLocation;
    private Integer numberVotes;
    private Integer awardedPoints;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "participant_id")
    private Long participant;
    @JoinColumn(name = "round_id")
    private Long round;

    public Integer getSubmissionTimeSeconds() {
        return submissionTimeSeconds;
    }

    public void setSubmissionTimeSeconds(Integer submissionTimeSeconds) {
        this.submissionTimeSeconds = submissionTimeSeconds;
    }

    public SubmissionData getSubmittedLocation() {
        return submittedLocation;
    }

    public void setSubmittedLocation(SubmissionData submittedLocation) {
        this.submittedLocation = submittedLocation;
    }

    public Integer getNumberVotes() {
        return numberVotes;
    }

    public void setNumberVotes(Integer numberVotes) {
        this.numberVotes = numberVotes;
    }

    public Integer getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(Integer awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipant() {
        return participant;
    }

    public void setParticipant(Long participant) {
        this.participant = participant;
    }

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }
}

package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.io.Serializable;


public class Submission {
    private Integer submissionTimeSeconds;
    private SubmissionData submittedLocation;
    private Integer numberVotes = 0;
    private Integer numberBanVotes = 0;
    private Integer awardedPoints = 0;
    private Long id;
    private String token;
    private boolean noSubmission = false; // if the user clicked on "Can`t find that"

    private byte[] image;

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

    public Integer getNumberBanVotes() {
        return numberBanVotes;
    }

    public void setNumberBanVotes(Integer numberBanVotes) {
        this.numberBanVotes = numberBanVotes;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getNoSubmission() {
        return noSubmission;
    }

    public void setNoSubmission(boolean noSubmission) {
        this.noSubmission = noSubmission;

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

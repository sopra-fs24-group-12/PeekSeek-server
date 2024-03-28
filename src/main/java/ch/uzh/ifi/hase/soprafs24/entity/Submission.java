package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.io.Serializable;

public class Submission {
    @Column(nullable = false, unique = true)
    private Long participantId;
    @Column(nullable = false, unique = true)
    private Long submissionId;

    @Column(nullable = false, unique = true)
    private Integer submissionTimeSeconds;

    @Column(nullable = false, unique = true)
    private SubmissionData submittedLocation;
    @Column(nullable = false, unique = true)
    private Integer numberVotes;

    @Column(nullable = false, unique = true)
    private Integer awardedPoints;

    public Integer getAwardedPoints() {
        return awardedPoints;
    }

    public Integer getNumberVotes() {
        return numberVotes;
    }

    public Integer getSubmissionTimeSeconds() {
        return submissionTimeSeconds;
    }

    public Integer getSubmissionTime(){
        return submissionTimeSeconds * 60;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public SubmissionData getSubmittedLocation() {
        return submittedLocation;
    }

    public void setAwardedPoints(Integer awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public void setNumberVotes(Integer numberVotes) {
        this.numberVotes = numberVotes;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public void setSubmissionTimeSeconds(Integer submissionTimeSeconds) {
        this.submissionTimeSeconds = submissionTimeSeconds;
    }

    public void setSubmittedLocation(SubmissionData submittedLocation) {
        this.submittedLocation = submittedLocation;
    }
    /*
    public Submission Submission(Long participantId){
        if findbyParticipantId(participantId).equalto(NUll){
        return Http.StatusUnknown()}
        Submission s = findbyParticipantId(participantId);

        return s;
    }*/
    public SubmissionData updateData( String lat, String lng, String heading, String pitch){
        SubmissionData s = new SubmissionData();
        s.setLng(lng);
        s.setPitch(pitch);
        s.setHeading(heading);
        s.setLat(lat);
        return s;

    }

    public Integer calculatePoints(){
        return null;

    }


    
}

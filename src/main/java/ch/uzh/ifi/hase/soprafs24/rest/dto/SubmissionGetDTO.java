package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;

public class SubmissionGetDTO {
  private Long id;
  private Integer submissionTimeSeconds;
  private SubmissionData submittedLocation;
  private Integer numberVotes;
  private Integer numberBanVotes;
  private Integer awardedPoints;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSubmissionTimeSeconds() {
    return submissionTimeSeconds;
  }

  public void setSubmissionTimeSeconds(Integer submissionTimeSeconds) {
    this.submissionTimeSeconds = submissionTimeSeconds;
  }

  public SubmissionData getSubmittedLocation() {
    return submittedLocation;
  }

  public void setSubmittedLocation(SubmissionData submissionLocation) {
    this.submittedLocation = submissionLocation;
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
}

package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
    private Long id;
    private String quest;
    private GeoCodingData geoCodingData;
    private int roundTime;
    private int remainingSeconds;
    private RoundStatus roundStatus = RoundStatus.PREPARED;
    private Map<Long, Submission> submissions = new HashMap<>();
    private List<Submission> shuffledSubmissions = new ArrayList<>();
    private Submission winningSubmission;
    private Integer summaryTime = 22;
    private Integer participantsDone = 0;
    private Integer bufferTime = 1;
    private Long lastPhaseChangeTime;


    public static Long submissionCount = 1L;

    public void addSubmission(Submission submission) {
        submissions.put(submission.getId(), submission);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public GeoCodingData getGeoCodingData() {
        return geoCodingData;
    }

    public void setGeoCodingData(GeoCodingData geoCodingData) {
        this.geoCodingData = geoCodingData;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public Map<Long, Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Map<Long, Submission> submissions) {
        this.submissions = submissions;
    }

    public List<Submission> getShuffledSubmissions() {
        return shuffledSubmissions;
    }

    public void setShuffledSubmissions(List<Submission> shuffledSubmissions) {
        this.shuffledSubmissions = shuffledSubmissions;
    }

    public Submission getWinningSubmission() {
        return winningSubmission;
    }

    public void setWinningSubmission(Submission winningSubmission) {
        this.winningSubmission = winningSubmission;
    }

    public static Long getSubmissionCount() {
        return submissionCount;
    }

    public static void setSubmissionCount(Long submissionCount) {
        Round.submissionCount = submissionCount;
    }

    public Integer getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(Integer summaryTime) {
        this.summaryTime = summaryTime;
    }

    public Integer getParticipantsDone() {
        return participantsDone;
    }

    public void setParticipantsDone(Integer participantsDone) {
        this.participantsDone = participantsDone;
    }
    public Integer getBufferTime() {
        return bufferTime;
    }

    public void setBufferTime(Integer bufferTime) {
        this.bufferTime = bufferTime;
    }

    public Long getLastPhaseChangeTime() {
        return lastPhaseChangeTime;
    }

    public void setLastPhaseChangeTime(Long lastPhaseChangeTime) {
        this.lastPhaseChangeTime = lastPhaseChangeTime;
    }
}
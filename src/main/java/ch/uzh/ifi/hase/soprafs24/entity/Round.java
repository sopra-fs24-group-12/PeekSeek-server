package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
    private Long id;
    private String quest;
    private int roundTime;
    private int remainingSeconds;
    private RoundStatus roundStatus = RoundStatus.PREPARED;
    private Map<Long, Submission> submissions = new HashMap<>();
    private Submission winningSubmission;
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
}
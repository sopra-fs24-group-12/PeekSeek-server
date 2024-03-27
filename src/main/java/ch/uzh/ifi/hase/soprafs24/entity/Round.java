package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;

import java.util.List;

public class Round {
    private static int initializedRounds;
    private final int id;
    private final String quest;
    private List<Submission> submissions;
    private Submission winningSubmission;
    private RoundStatus roundStatus;
    private final int roundTime;

    public Round(String quest, int roundTime) {
        initializedRounds += 1;
        this.id = initializedRounds;
        this.quest = quest;
        this.roundTime = roundTime;
        this.submissions = new ArrayList<Submission>();
    }
    public static int getInitializedRounds() {
        return initializedRounds;
    }

    public static void setInitializedRounds(int initializedRounds) {
        Round.initializedRounds = initializedRounds;
    }

    public int getId() {
        return id;
    }

    public String getQuest() {
        return quest;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Submission getWinningSubmission() {
        return winningSubmission;
    }

    public void setWinningSubmission(Submission winningSubmission) {
        this.winningSubmission = winningSubmission;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public int getRoundTime() {
        return roundTime;
    }
}
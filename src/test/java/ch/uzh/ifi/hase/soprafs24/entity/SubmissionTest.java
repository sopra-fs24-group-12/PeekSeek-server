package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubmissionTest {

    private Submission submission;

    @BeforeEach
    public void setUp() {
        submission = new Submission();
    }

    @Test
    public void testSetAndGetSubmissionTimeSeconds() {
        Integer submissionTimeSeconds = 30;
        submission.setSubmissionTimeSeconds(submissionTimeSeconds);
        assertEquals(submissionTimeSeconds, submission.getSubmissionTimeSeconds());
    }

    @Test
    public void testSetAndGetSubmittedLocation() {
        SubmissionData submittedLocation = new SubmissionData();
        submission.setSubmittedLocation(submittedLocation);
        assertEquals(submittedLocation, submission.getSubmittedLocation());
    }

    @Test
    public void testSetAndGetNumberVotes() {
        Integer numberVotes = 5;
        submission.setNumberVotes(numberVotes);
        assertEquals(numberVotes, submission.getNumberVotes());
    }

    @Test
    public void testSetAndGetNumberBanVotes() {
        Integer numberBanVotes = 2;
        submission.setNumberBanVotes(numberBanVotes);
        assertEquals(numberBanVotes, submission.getNumberBanVotes());
    }

    @Test
    public void testSetAndGetAwardedPoints() {
        Integer awardedPoints = 100;
        submission.setAwardedPoints(awardedPoints);
        assertEquals(awardedPoints, submission.getAwardedPoints());
    }

    @Test
    public void testSetAndGetId() {
        Long id = 123L;
        submission.setId(id);
        assertEquals(id, submission.getId());
    }

    @Test
    public void testSetAndGetToken() {
        String token = "token123";
        submission.setToken(token);
        assertEquals(token, submission.getToken());
    }

    @Test
    public void testSetAndGetNoSubmission() {
        boolean noSubmission = true;
        submission.setNoSubmission(noSubmission);
        assertEquals(noSubmission, submission.getNoSubmission());
    }

    @Test
    public void testSetAndGetUsername() {
        String username = "user123";
        submission.setUsername(username);
        assertEquals(username, submission.getUsername());
    }
}

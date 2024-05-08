package ch.uzh.ifi.hase.soprafs24.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;

public class RoundTest {

    @Test
    public void testConstructor() {
        Round round = new Round();

        assertEquals(RoundStatus.PREPARED, round.getRoundStatus());
        assertEquals(0, round.getParticipantsDone());
        assertNotNull(round.getSubmissions());
        assertTrue(round.getSubmissions().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        Round round = new Round();

        round.setId(1L);
        round.setQuest("Test Quest");
        round.setGeoCodingData(new GeoCodingData());
        round.setRoundTime(60);
        round.setRemainingSeconds(60);
        round.setSummaryTime(10);
        round.setBufferTime(5);
        round.setLastPhaseChangeTime(System.currentTimeMillis());

        assertEquals(1L, round.getId());
        assertEquals("Test Quest", round.getQuest());
        assertNotNull(round.getGeoCodingData());
        assertEquals(60, round.getRoundTime());
        assertEquals(60, round.getRemainingSeconds());
        assertEquals(10, round.getSummaryTime());
        assertEquals(5, round.getBufferTime());
        assertNotNull(round.getLastPhaseChangeTime());
    }

    @Test
    public void testAddSubmission() {
        Round round = new Round();
        Submission submission = new Submission();
        submission.setId(Round.submissionCount++);

        round.addSubmission(submission);

        assertEquals(1, round.getSubmissions().size());
        assertTrue(round.getSubmissions().containsKey(submission.getId()));
    }

    @Test
    public void testSetRoundStatus() {
        Round round = new Round();

        round.setRoundStatus(RoundStatus.VOTING);

        assertEquals(RoundStatus.VOTING, round.getRoundStatus());
    }

    @Test
    public void testSetWinningSubmission() {
        Round round = new Round();
        Submission submission = new Submission();
        submission.setId(Round.submissionCount++);

        round.addSubmission(submission);
        round.setWinningSubmission(submission);

        assertEquals(submission, round.getWinningSubmission());
    }

    @Test
    public void testGetParticipantsDone() {
        Round round = new Round();

        round.setParticipantsDone(2);

        assertEquals(2, round.getParticipantsDone());
    }
}

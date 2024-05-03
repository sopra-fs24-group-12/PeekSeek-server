package ch.uzh.ifi.hase.soprafs24.entity;


import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SubmissionDataTest {

    private SubmissionData submissionData;

    @BeforeEach
    public void setUp() {
        submissionData = new SubmissionData();
    }
    @AfterEach
    public void tearDown() { submissionData = null; }

    @Test
    public void testSetAndGetLat() {
        String lat = "47.1234";
        submissionData.setLat(lat);
        assertEquals(lat, submissionData.getLat());
    }

    @Test
    public void testSetAndGetLng() {
        String lng = "8.5678";
        submissionData.setLng(lng);
        assertEquals(lng, submissionData.getLng());
    }

    @Test
    public void testSetAndGetHeading() {
        String heading = "90";
        submissionData.setHeading(heading);
        assertEquals(heading, submissionData.getHeading());
    }

    @Test
    public void testSetAndGetPitch() {
        String pitch = "45";
        submissionData.setPitch(pitch);
        assertEquals(pitch, submissionData.getPitch());
    }

    @Test
    public void testSetAndGetNoSubmission() {
        Boolean noSubmission = true;
        submissionData.setNoSubmission(noSubmission);
        assertEquals(noSubmission, submissionData.getNoSubmission());
    }
}
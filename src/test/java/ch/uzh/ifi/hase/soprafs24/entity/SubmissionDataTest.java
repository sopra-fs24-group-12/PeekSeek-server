package ch.uzh.ifi.hase.soprafs24.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SubmissionDataTest {

    private SubmissionData submissionData;

    @BeforeEach
    public void setUp() {
        submissionData = new SubmissionData();
    }
    @AfterEach
    public void tearDown() { submissionData = null; }

    @Test
    void testSetAndGetLat() {
        String lat = "47.1234";
        submissionData.setLat(lat);
        assertEquals(lat, submissionData.getLat());
    }

    @Test
    void testSetAndGetLng() {
        String lng = "8.5678";
        submissionData.setLng(lng);
        assertEquals(lng, submissionData.getLng());
    }

    @Test
    void testSetAndGetHeading() {
        String heading = "90";
        submissionData.setHeading(heading);
        assertEquals(heading, submissionData.getHeading());
    }

    @Test
    void testSetAndGetPitch() {
        String pitch = "45";
        submissionData.setPitch(pitch);
        assertEquals(pitch, submissionData.getPitch());
    }

    @Test
    void testSetAndGetNoSubmission() {
        Boolean noSubmission = true;
        submissionData.setNoSubmission(noSubmission);
        assertEquals(noSubmission, submissionData.getNoSubmission());
    }
}
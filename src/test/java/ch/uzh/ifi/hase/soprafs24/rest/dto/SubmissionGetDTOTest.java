package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionGetDTOTest {

    private SubmissionGetDTO submissionGetDTO;

    @BeforeEach
    public void setUp() {
        submissionGetDTO = new SubmissionGetDTO();
    }

    @Test
    void getId_setId_returnsCorrectValue() {
        Long id = 42L;
        submissionGetDTO.setId(id);
        
        assertEquals(id, submissionGetDTO.getId(), "The id should match the set value.");
    }

    @Test
    void getSubmissionTimeSeconds_setSubmissionTimeSeconds_returnsCorrectValue() {
        Integer submissionTimeSeconds = 120;
        submissionGetDTO.setSubmissionTimeSeconds(submissionTimeSeconds);
        
        assertEquals(submissionTimeSeconds, submissionGetDTO.getSubmissionTimeSeconds(), "The submission time seconds should match the set value.");
    }

    @Test
    void getSubmittedLocation_setSubmittedLocation_returnsCorrectValue() {
        SubmissionData submittedLocation = new SubmissionData();
        submittedLocation.setLat("47.36667");
        submittedLocation.setLng("8.55");
        submissionGetDTO.setSubmittedLocation(submittedLocation);
        
        assertEquals(submittedLocation, submissionGetDTO.getSubmittedLocation(), "The submitted location should match the set value.");
    }

    @Test
    void getNumberVotes_setNumberVotes_returnsCorrectValue() {
        Integer numberVotes = 10;
        submissionGetDTO.setNumberVotes(numberVotes);
        
        assertEquals(numberVotes, submissionGetDTO.getNumberVotes(), "The number of votes should match the set value.");
    }

    @Test
    void getNumberBanVotes_setNumberBanVotes_returnsCorrectValue() {
        Integer numberBanVotes = 2;
        submissionGetDTO.setNumberBanVotes(numberBanVotes);
        
        assertEquals(numberBanVotes, submissionGetDTO.getNumberBanVotes(), "The number of ban votes should match the set value.");
    }

    @Test
    void getAwardedPoints_setAwardedPoints_returnsCorrectValue() {
        Integer awardedPoints = 15;
        submissionGetDTO.setAwardedPoints(awardedPoints);
        
        assertEquals(awardedPoints, submissionGetDTO.getAwardedPoints(), "The awarded points should match the set value.");
    }

    @Test
    void getNoSubmission_setNoSubmission_returnsCorrectValue() {
        Boolean noSubmission = true;
        submissionGetDTO.setNoSubmission(noSubmission);
        
        assertEquals(noSubmission, submissionGetDTO.getNoSubmission(), "The no submission flag should match the set value.");
    }
}

package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoundGetDTOTest {

    private RoundGetDTO roundGetDTO;

    @BeforeEach
    public void setUp() {
        roundGetDTO = new RoundGetDTO();
    }

    @Test
    public void getCurrentRound_setCurrentRound_returnsCorrectValuePlusOne() {
        Integer currentRound = 2;
        roundGetDTO.setCurrentRound(currentRound);
        
        assertEquals(Integer.valueOf(currentRound + 1), roundGetDTO.getCurrentRound(), "The current round should be 1 more than the set value.");
    }

    @Test
    public void getNumberRounds_setNumberRounds_returnsCorrectValue() {
        Integer numberRounds = 10;
        roundGetDTO.setNumberRounds(numberRounds);
        
        assertEquals(numberRounds, roundGetDTO.getNumberRounds(), "The number of rounds should match the set value.");
    }

    @Test
    public void getQuest_setQuest_returnsCorrectValue() {
        String quest = "Fountain";
        roundGetDTO.setQuest(quest);
        
        assertEquals(quest, roundGetDTO.getQuest(), "The quest should match the set value.");
    }

    @Test
    public void getRoundStatus_setRoundStatus_returnsCorrectValue() {
        RoundStatus roundStatus = RoundStatus.PLAYING;
        roundGetDTO.setRoundStatus(roundStatus);
        
        assertEquals(roundStatus, roundGetDTO.getRoundStatus(), "The round status should match the set value.");
    }

    @Test
    public void getRoundTime_setRoundTime_returnsCorrectValue() {
        Integer roundTime = 60;
        roundGetDTO.setRoundTime(roundTime);
        
        assertEquals(roundTime, roundGetDTO.getRoundTime(), "The round time should match the set value.");
    }

    @Test
    public void getRemainingSeconds_setRemainingSeconds_returnsCorrectValue() {
        Integer remainingSeconds = 30;
        roundGetDTO.setRemainingSeconds(remainingSeconds);
        
        assertEquals(remainingSeconds, roundGetDTO.getRemainingSeconds(), "The remaining seconds should match the set value.");
    }

    @Test
    public void getGeoCodingData_setGeoCodingData_returnsCorrectValue() {
        GeoCodingData geoCodingData = new GeoCodingData();
        geoCodingData.setLocation("Berlin");
        roundGetDTO.setGeoCodingData(geoCodingData);
        
        assertEquals(geoCodingData, roundGetDTO.getGeoCodingData(), "The GeoCodingData should match the set value.");
    }
}

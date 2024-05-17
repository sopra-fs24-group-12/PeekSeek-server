package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryGetDTOTest {

    private SummaryGetDTO summaryGetDTO;

    @BeforeEach
    public void setUp() {
        summaryGetDTO = new SummaryGetDTO();
    }

    @Test
    void getId_setId_returnsCorrectValue() {
        Long id = 42L;
        summaryGetDTO.setId(id);
        
        assertEquals(id, summaryGetDTO.getId(), "The ID should match the set value.");
    }

    @Test
    void getCityName_setCityName_returnsCorrectValue() {
        String cityName = "Gotham";
        summaryGetDTO.setCityName(cityName);
        
        assertEquals(cityName, summaryGetDTO.getCityName(), "The city name should match the set value.");
    }

    @Test
    void getRoundsPlayed_setRoundsPlayed_returnsCorrectValue() {
        int roundsPlayed = 100;
        summaryGetDTO.setRoundsPlayed(roundsPlayed);
        
        assertEquals(roundsPlayed, summaryGetDTO.getRoundsPlayed(), "The rounds played should match the set value.");
    }

    @Test
    void getQuests_setQuests_returnsCorrectValue() {
        Quest quest1 = new Quest();
        Quest quest2 = new Quest();
        quest1.setName("Quest 1");
        quest2.setName("Quest 2");
        List<Quest> quests = Arrays.asList(quest1, quest2);
        summaryGetDTO.setQuests(quests);
        
        assertEquals(quests, summaryGetDTO.getQuests(), "The quests list should match the set value.");
    }
}

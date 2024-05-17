package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SummaryTest {

    private Summary summary;

    @BeforeEach
    public void setUp() {
        summary = new Summary();
    }

    @Test
    void testSetAndGetId() {
        Long id = 123L;
        summary.setId(id);
        assertEquals(id, summary.getId());
    }

    @Test
    void testSetAndGetCityName() {
        String cityName = "Zurich";
        summary.setCityName(cityName);
        assertEquals(cityName, summary.getCityName());
    }

    @Test
    void testSetAndGetRoundsPlayed() {
        int roundsPlayed = 5;
        summary.setRoundsPlayed(roundsPlayed);
        assertEquals(roundsPlayed, summary.getRoundsPlayed());
    }

    @Test
    void testSetAndGetQuests() {
        List<Quest> quests = new ArrayList<>();
        quests.add(new Quest());
        summary.setQuests(quests);
        assertEquals(quests, summary.getQuests());
    }

    @Test
    void testSetAndGetPassword() {
        String password = "password123";
        summary.setPassword(password);
        assertEquals(password, summary.getPassword());
    }
}

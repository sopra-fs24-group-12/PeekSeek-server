package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestTest {

    @Autowired
    private TestEntityManager entityManager;

    private Summary newSummary;

    @BeforeEach
    public void setUp() {
        // Creating and persisting a Summary instance to associate with Quest
        newSummary = new Summary();
        newSummary.setCityName("Zurich");
        newSummary.setRoundsPlayed(10);
        newSummary.setPassword("securepassword123");
        entityManager.persist(newSummary);
        entityManager.flush();
    }

    @Test
    void whenCreateQuest_thenPersistQuest() {
        // Arrange
        Quest quest = new Quest();
        quest.setDescription("Save the princess!");
        quest.setName("Royal Rescue");
        quest.setLink("http://example.com/quest1");
        quest.setNoSubmission(false);
        quest.setLat("47.3769");
        quest.setLng("8.5417");

        // Associate summary with quest
        quest.setSummary(newSummary);

        // Act
        entityManager.persist(quest);
        entityManager.flush();

        // Assert - Fetching from DB by ID and checking values
        Quest foundQuest = entityManager.find(Quest.class, quest.getId());
        assertThat(foundQuest).isNotNull();
        assertThat(foundQuest.getDescription()).isEqualTo("Save the princess!");
        assertThat(foundQuest.getName()).isEqualTo("Royal Rescue");
        assertThat(foundQuest.getLink()).isEqualTo("http://example.com/quest1");
        assertThat(foundQuest.getNoSubmission()).isFalse();
        assertThat(foundQuest.getLat()).isEqualTo("47.3769");
        assertThat(foundQuest.getLng()).isEqualTo("8.5417");

        // Ensure the association with Summary is set correctly
        assertThat(foundQuest.getSummary()).isNotNull();
        assertThat(foundQuest.getSummary().getId()).isEqualTo(newSummary.getId());
    }
}

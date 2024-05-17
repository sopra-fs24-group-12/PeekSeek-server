package ch.uzh.ifi.hase.soprafs24.repository;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SummaryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SummaryRepository summaryRepository;

    @BeforeEach
    public void setup() {
        // Setup data or configurations before each test if needed
    }

    @Test
    void whenFindById_thenReturnSummary() {
        // Arrange
        Summary newSummary = new Summary();
        newSummary.setCityName("Zurich");
        newSummary.setRoundsPlayed(10);
        newSummary.setPassword("securepassword123");
        entityManager.persist(newSummary);
        entityManager.flush();

        // Act
        Summary foundSummary = summaryRepository.findSummaryById(newSummary.getId());

        // Assert
        assertThat(foundSummary).isNotNull();
        assertThat(foundSummary.getCityName()).isEqualTo("Zurich");
        assertThat(foundSummary.getRoundsPlayed()).isEqualTo(10);
        assertThat(foundSummary.getPassword()).isEqualTo("securepassword123");
    }
}

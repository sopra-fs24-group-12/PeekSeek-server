package ch.uzh.ifi.hase.soprafs24.repository;
import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GeoCodingDataRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GeoCodingDataRepository geoCodingDataRepository;

    @BeforeEach
    public void setUp() {
        // Given
        GeoCodingData geoCodingData = new GeoCodingData();
        geoCodingData.setLocation("Zurich");
        geoCodingData.setFormAddress("Zurich, Switzerland");
        entityManager.persist(geoCodingData);

        geoCodingData = new GeoCodingData();
        geoCodingData.setLocation("Geneva");
        geoCodingData.setFormAddress("Geneva, Switzerland");
        entityManager.persist(geoCodingData);
        
        entityManager.flush();
    }

    @Test
    void whenFindByLocation_thenReturnGeoCodingData() {
        // When
        GeoCodingData found = geoCodingDataRepository.findGeoCodingDataByLocation("Zurich");

        // Then
        assertThat(found.getFormAddress()).isEqualTo("Zurich, Switzerland");
    }

    @Test
    void whenFindAllCityNames_thenReturnListOfCityNames() {
        // When
        List<String> cityNames = geoCodingDataRepository.findAllCityNames();

        // Then
        assertThat(cityNames).hasSize(2);
        assertThat(cityNames).containsExactlyInAnyOrder("Zurich, Switzerland", "Geneva, Switzerland");
    }
}

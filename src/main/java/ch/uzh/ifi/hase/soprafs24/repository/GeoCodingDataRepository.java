package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("geoCodingDataRepository")
public interface GeoCodingDataRepository extends JpaRepository<GeoCodingData, Long> {
    public GeoCodingData findGeoCodingDataByLocation(String location);

    @Query("SELECT DISTINCT g.formAddress FROM GeoCodingData g")
    List<String> findAllCityNames();
}

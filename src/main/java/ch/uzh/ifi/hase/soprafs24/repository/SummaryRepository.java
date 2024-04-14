package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("summaryRepository")
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    public Summary findSummaryById(long id);
}

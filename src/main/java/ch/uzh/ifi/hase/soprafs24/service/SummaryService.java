package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class SummaryService {
    private final SummaryRepository summaryRepository;

    public SummaryService(@Qualifier("summaryRepository") SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    public Summary getSummary(Long id, String password) {
        Summary summary = summaryRepository.findSummaryById(id);
        if (summary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summary with this id does not exist");
        }

//        if (!password.equals(summary.getPassword())) {
//            //System.out.println(password + " " + summary.getPassword());
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
//        }

        return summary;
    }
}

package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.SummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SummaryController {
    private final SummaryService summaryService;


    SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }


    @GetMapping("/summaries/{summaryId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SummaryGetDTO getSummary(@PathVariable long summaryId) {
        Summary summary = summaryService.getSummary(summaryId, "replace with password");
        return DTOMapper.INSTANCE.convertSummaryToSummaryGetDTO(summary);
    }

}

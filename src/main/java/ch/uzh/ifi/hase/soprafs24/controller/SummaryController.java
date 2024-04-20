package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.SummaryService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameStartedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantJoinedDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ParticipantLeftDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.UpdateSettingsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

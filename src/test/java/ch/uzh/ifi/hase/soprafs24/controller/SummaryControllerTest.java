package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SummaryGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.SummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SummaryControllerTest {


    private MockMvc mockMvc;

    @Mock
    private SummaryService summaryService;

    @MockBean
    private DTOMapper dtoMapper;

    @InjectMocks
    private SummaryController summaryController;

    private ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(summaryController).build();
    }

    @Test
    public void testGetSummary() throws Exception {

        long summaryId = 1L;
        Summary summary = new Summary();
        summary.setId(summaryId);


        SummaryGetDTO summaryGetDTO = new SummaryGetDTO();
        summaryGetDTO.setId(summary.getId());


        when(summaryService.getSummary(summaryId)).thenReturn(summary);
        when(dtoMapper.convertSummaryToSummaryGetDTO(summary)).thenReturn(summaryGetDTO);


        mockMvc.perform(get("/summaries/{summaryId}", summaryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(summaryGetDTO.getId()));

        verify(summaryService).getSummary(summaryId);

    }
}

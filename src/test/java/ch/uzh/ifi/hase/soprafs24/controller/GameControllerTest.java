package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotingPostDTO;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void getRoundInformation_ReturnsRoundInfo() throws Exception {

        //given
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setCurrentRound(3);
        game.setNumberRounds(5);
        game.setAdminId(1L);

        Round currentround = new Round();
        currentround.setId(2L);
        currentround.setQuest("test_quest");
        currentround.setRemainingSeconds(60);
        currentround.setRoundStatus(RoundStatus.PLAYING);
        String token = "mocktoken123";


        given(gameService.getGameInformation(token, game.getId())).willReturn(game);
        given(gameService.getRoundInformation(token, game.getId())).willReturn(currentround);

        //when
        MockHttpServletRequestBuilder getRequest = get("/games/{id}/round", gameId)
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quest", is(currentround.getQuest())))
                .andExpect(jsonPath("$.currentRound", is(game.getCurrentRound()+1)))
                .andExpect(jsonPath("$.remainingSeconds", is(currentround.getRemainingSeconds())))
                .andExpect(jsonPath("$.numberRounds", is(game.getNumberRounds())))
                .andExpect(jsonPath("$.roundStatus", is(currentround.getRoundStatus().toString())));
    }

    @Test
    public void startNextRound_StartsRoundSuccessfully() throws Exception {
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setCurrentRound(3);
        game.setNumberRounds(5);
        game.setAdminId(1L);
        String token = "mocktoken123";

        // Setup mocking
        // Assume startNextRound does not return anything
        doNothing().when(gameService).startNextRound(gameId);

        // Perform request
        MockHttpServletRequestBuilder postRequest = post("/games/{id}/nextRound", gameId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON);

        // Verify the results
        mockMvc.perform(postRequest)
                .andExpect(status().isOk());


        // Verify that the service method was called
        verify(gameService).startNextRound(gameId);
    }

    @Test
    public void postSubmission_WithValidInput_SubmissionAccepted() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Create a mock SubmissionPostDTO
        SubmissionPostDTO submissionPostDTO = new SubmissionPostDTO();
        submissionPostDTO.setLat("10");
        submissionPostDTO.setLng("10");
        submissionPostDTO.setHeading("10");
        submissionPostDTO.setPitch("10");

        // Setup mocking
        doNothing().when(gameService).postSubmission(gameId, token, submissionPostDTO);

        // Serialize the DTO to JSON for the request body
        String submissionJson = asJsonString(submissionPostDTO);

        // Perform request
        MockHttpServletRequestBuilder postRequest = post("/games/{id}/submission", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(submissionJson)
                .header("Authorization", token);

        // Verify the results
        mockMvc.perform(postRequest)
                .andExpect(status().isOk());

        // Verify that the service method was called with correct parameters
        verify(gameService).postSubmission(eq(gameId), eq(token), any(SubmissionPostDTO.class));
    }

    @Test
    public void postVoting_WithValidInput_VotingProcessedSuccessfully() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Create a mock VotingPostDTO
        VotingPostDTO votingPostDTO = new VotingPostDTO();
        HashMap<Long, String> votes = new HashMap<>();
        votes.put(101L, "winning");
        votes.put(102L, "ban");
        votingPostDTO.setVotes(votes);

        // Argument Captor for VotingPostDTO
        ArgumentCaptor<VotingPostDTO> votingCaptor = ArgumentCaptor.forClass(VotingPostDTO.class);

        // Serialize the DTO to JSON for the request body
        String votingJson = asJsonString(votingPostDTO);

        // Perform request
        MockHttpServletRequestBuilder postRequest = post("/games/{id}/voting", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(votingJson)
                .header("Authorization", token);

        // Verify the results
        mockMvc.perform(postRequest)
                .andExpect(status().isOk());

        // Verify that the service method was called with correct parameters
        verify(gameService).postVoting(eq(gameId), eq(token), votingCaptor.capture());

        // Assertions on the captured arguments
        VotingPostDTO capturedDTO = votingCaptor.getValue();
        assertThat(capturedDTO.getVotes(), hasEntry(101L, "winning"));
        assertThat(capturedDTO.getVotes(), hasEntry(102L, "ban"));
    }

//    @Test
//    public void getWinningSubmissionCurrent_ReturnsWinningSubmission() throws Exception {
//        Long gameId = 1L;
//        String token = "mocktoken123";
//
//        // Mock setup for the Round and Submission
//        Round mockRound = new Round();
//        Submission mockWinningSubmission = new Submission();
//        mockWinningSubmission.setId(101L);  // Example properties
//        mockWinningSubmission.setCaption("Winning caption");
//
//        when(gameService.getRoundInformation(token, gameId)).thenReturn(mockRound);
//        when(mockRound.getWinningSubmission()).thenReturn(mockWinningSubmission);
//
//        SubmissionGetDTO mockSubmissionDTO = new SubmissionGetDTO();
//        mockSubmissionDTO.setId(mockWinningSubmission.getId());
//        mockSubmissionDTO.setCaption(mockWinningSubmission.getCaption());
//
//        when(DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(mockWinningSubmission)).thenReturn(mockSubmissionDTO);
//
//        // Perform request
//        MockHttpServletRequestBuilder getRequest = get("/games/{id}/winningSubmission", gameId)
//                .header("Authorization", token)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // Verify the results
//        mockMvc.perform(getRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(mockSubmissionDTO.getId().intValue())))
//                .andExpect(jsonPath("$.caption", is(mockSubmissionDTO.getCaption())));
//    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
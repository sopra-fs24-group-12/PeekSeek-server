package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LeaderboardGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotingPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
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

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private DTOMapper dtoMapper;


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

    @Test
    public void getWinningSubmissionCurrent_ReturnsCorrectSubmission() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Mock setup for the Round and Winning Submission
        Round mockRound = new Round();
        Submission mockWinningSubmission = new Submission();
        mockWinningSubmission.setId(101L);
        mockWinningSubmission.setSubmissionTimeSeconds(120);

        SubmissionGetDTO mockSubmissionDTO = new SubmissionGetDTO();
        mockSubmissionDTO.setId(mockWinningSubmission.getId());
        mockSubmissionDTO.setSubmissionTimeSeconds(mockWinningSubmission.getSubmissionTimeSeconds());

        given(gameService.getRoundInformation(token, gameId)).willReturn(mockRound);
        given(dtoMapper.convertSubmissionToSubmissionGetDTO(mockWinningSubmission)).willReturn(mockSubmissionDTO);

        // Perform request
        MockHttpServletRequestBuilder getRequest = get("/games/{id}/winningSubmission", gameId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON);

        // Verify the results
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());

        // Verify interaction
        verify(gameService).getRoundInformation(token, gameId);
    }

    @Test
    public void getSubmissions_ReturnsListOfSubmissions() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Setup domain objects
        Submission submission1 = new Submission();
        submission1.setId(1L);
        submission1.setSubmissionTimeSeconds(120);
        submission1.setNumberVotes(10);
        submission1.setNumberBanVotes(2);
        submission1.setAwardedPoints(50);
        submission1.setNoSubmission(false);

        Submission submission2 = new Submission();
        submission2.setId(2L);
        submission2.setSubmissionTimeSeconds(100);
        submission2.setNumberVotes(5);
        submission2.setNumberBanVotes(1);
        submission2.setAwardedPoints(30);
        submission2.setNoSubmission(true);

        Round currentRound = new Round();
        List<Submission> shuffledSubmissions = new ArrayList<>();
        shuffledSubmissions.add(submission1);
        shuffledSubmissions.add(submission2);
        currentRound.setShuffledSubmissions(shuffledSubmissions);

        // Setup DTOs
        SubmissionGetDTO dto1 = new SubmissionGetDTO();
        dto1.setId(submission1.getId());
        dto1.setSubmissionTimeSeconds(submission1.getSubmissionTimeSeconds());
        dto1.setNumberVotes(submission1.getNumberVotes());
        dto1.setNumberBanVotes(submission1.getNumberBanVotes());
        dto1.setAwardedPoints(submission1.getAwardedPoints());
        dto1.setNoSubmission(submission1.getNoSubmission());

        SubmissionGetDTO dto2 = new SubmissionGetDTO();
        dto2.setId(submission2.getId());
        dto2.setSubmissionTimeSeconds(submission2.getSubmissionTimeSeconds());
        dto2.setNumberVotes(submission2.getNumberVotes());
        dto2.setNumberBanVotes(submission2.getNumberBanVotes());
        dto2.setAwardedPoints(submission2.getAwardedPoints());
        dto2.setNoSubmission(submission2.getNoSubmission());

        // Mock service and mapper
        given(gameService.getRoundInformation(token, gameId)).willReturn(currentRound);
        given(dtoMapper.convertSubmissionToSubmissionGetDTO(submission1)).willReturn(dto1);
        given(dtoMapper.convertSubmissionToSubmissionGetDTO(submission2)).willReturn(dto2);

        // Perform request
        MockHttpServletRequestBuilder getRequest = get("/games/{id}/submissions", gameId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON);

        // Execute and verify
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(dto1.getId().intValue())))
                .andExpect(jsonPath("$[0].submissionTimeSeconds", is(dto1.getSubmissionTimeSeconds())))
                .andExpect(jsonPath("$[0].numberVotes", is(dto1.getNumberVotes())))
                .andExpect(jsonPath("$[0].numberBanVotes", is(dto1.getNumberBanVotes())))
                .andExpect(jsonPath("$[0].awardedPoints", is(dto1.getAwardedPoints())))
                .andExpect(jsonPath("$[0].noSubmission", is(dto1.getNoSubmission())))
                .andExpect(jsonPath("$[1].id", is(dto2.getId().intValue())))
                .andExpect(jsonPath("$[1].submissionTimeSeconds", is(dto2.getSubmissionTimeSeconds())))
                .andExpect(jsonPath("$[1].numberVotes", is(dto2.getNumberVotes())))
                .andExpect(jsonPath("$[1].numberBanVotes", is(dto2.getNumberBanVotes())))
                .andExpect(jsonPath("$[1].awardedPoints", is(dto2.getAwardedPoints())))
                .andExpect(jsonPath("$[1].noSubmission", is(dto2.getNoSubmission())));
    }

    @Test
    public void getWinningSubmissions_ReturnsCorrectSubmissions() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Mock setup for the Game and Rounds
        Game mockGame = new Game();
        Round mockRound1 = new Round();
        Round mockRound2 = new Round();
        mockGame.setRounds(Arrays.asList(mockRound1, mockRound2));

        Submission mockWinningSubmission1 = new Submission();
        mockWinningSubmission1.setId(101L);
        Submission mockWinningSubmission2 = new Submission();
        mockWinningSubmission2.setId(102L);

        mockRound1.setWinningSubmission(mockWinningSubmission1);
        mockRound2.setWinningSubmission(mockWinningSubmission2);

        SubmissionGetDTO mockSubmissionDTO1 = new SubmissionGetDTO();
        mockSubmissionDTO1.setId(mockWinningSubmission1.getId());
        SubmissionGetDTO mockSubmissionDTO2 = new SubmissionGetDTO();
        mockSubmissionDTO2.setId(mockWinningSubmission2.getId());

        given(gameService.getGameInformation(token, gameId)).willReturn(mockGame);
        given(dtoMapper.convertSubmissionToSubmissionGetDTO(mockWinningSubmission1)).willReturn(mockSubmissionDTO1);
        given(dtoMapper.convertSubmissionToSubmissionGetDTO(mockWinningSubmission2)).willReturn(mockSubmissionDTO2);

        // Perform request
        MockHttpServletRequestBuilder getRequest = get("/games/{id}/winningSubmissions", gameId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON);

        // Verify the results
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(mockSubmissionDTO1.getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(mockSubmissionDTO2.getId().intValue())));

        // Verify interactions
        verify(gameService).getGameInformation(token, gameId);
    }

    @Test
    public void getLeaderboard_ReturnsLeaderboardSuccessfully() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Prepare mock data
        Participant participant1 = new Participant();
        participant1.setId(1L);
        participant1.setUsername("playerOne");
        participant1.setScore(100);
        participant1.setStreak(5);

        Participant participant2 = new Participant();
        participant2.setId(2L);
        participant2.setUsername("playerTwo");
        participant2.setScore(150);
        participant2.setStreak(3);

        // Mocking the service layer to return participants
        given(gameService.getLeaderboard(token, gameId)).willReturn(Arrays.asList(participant1, participant2));

        // Mocking the conversion to DTO
        LeaderboardGetDTO dto1 = new LeaderboardGetDTO();
        dto1.setId(participant1.getId());
        dto1.setUsername(participant1.getUsername());
        dto1.setScore(participant1.getScore());
        dto1.setStreak(participant1.getStreak());

        LeaderboardGetDTO dto2 = new LeaderboardGetDTO();
        dto2.setId(participant2.getId());
        dto2.setUsername(participant2.getUsername());
        dto2.setScore(participant2.getScore());
        dto2.setStreak(participant2.getStreak());

        given(dtoMapper.convertParticipantToLeaderboardGetDTO(participant1)).willReturn(dto1);
        given(dtoMapper.convertParticipantToLeaderboardGetDTO(participant2)).willReturn(dto2);

        // Perform the GET request
        mockMvc.perform(get("/games/{id}/leaderboard", gameId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(dto1.getUsername())))
                .andExpect(jsonPath("$[0].score", is(dto1.getScore())))
                .andExpect(jsonPath("$[0].streak", is(dto1.getStreak())))
                .andExpect(jsonPath("$[1].username", is(dto2.getUsername())))
                .andExpect(jsonPath("$[1].score", is(dto2.getScore())))
                .andExpect(jsonPath("$[1].streak", is(dto2.getStreak())));

        // Verify interactions
        verify(gameService).getLeaderboard(token, gameId);
    }

    @Test
    public void leaveGame_LeavesGameSuccessfully() throws Exception {
        Long gameId = 1L;
        String token = "mocktoken123";

        // Setup mocking behavior
        doNothing().when(gameService).leaveGame(gameId, token);

        // Perform the delete request
        mockMvc.perform(delete("/games/{id}/leave", gameId)
                        .header("Authorization", token))
                .andExpect(status().isNoContent());

        // Verify that the service method was called
        verify(gameService).leaveGame(gameId, token);
    }

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
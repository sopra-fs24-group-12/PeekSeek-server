package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebsocketController.class)
public class YoussefGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;


    @Test
    public void givenUsers_whenGetRoundInformation_thenReturnJsonArray() throws Exception {
        // given
        Game game = new Game();
        game.setId(3L);
        //game.setRoundDurationSeconds(33);
        //game.setGameLocation("Munich");
        game.setCurrentRound(3);
        game.setNumberRounds(5);
        game.setAdminId(1L);

        Round currentround = new Round();
        currentround.setId(2L);
        currentround.setRemainingSeconds(12);
        currentround.setRoundStatus(RoundStatus.PLAYING);
        String token = "knauwr7";

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(gameService.getGameInformation(token, game.getId())).willReturn(game);
        given(gameService.getRoundInformation(token, game.getId())).willReturn(currentround);
                // when
        MockHttpServletRequestBuilder getRequest = get("/games/{id}/round").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].currentRound", is(game.getCurrentRound())))
                .andExpect((ResultMatcher) jsonPath("$[0].remainingSeconds", is(currentround.getRemainingSeconds())))
                .andExpect((ResultMatcher) jsonPath("$[0].numberRounds", is(game.getNumberRounds())))
                .andExpect((ResultMatcher) jsonPath("$[0].status", is(currentround.getRoundStatus().toString())));
    }

    @Test
    public void getGetRoundInformationWithIDFailedTest() throws Exception{
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not found");
        given(gameService.getRoundInformation(Mockito.any(), (long) anyInt())).willThrow(exception);

        MockHttpServletRequestBuilder postRequest = get("/games/5555/round")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest)
                .andExpect(status().isNotFound())
                .andExpect(status().is4xxClientError());

    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}

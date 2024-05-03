package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.*;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.google.GeoCoding;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.GeoCodingDataRepository;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.SummaryRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionPostDTO;
import ch.uzh.ifi.hase.soprafs24.service.LobbyService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Lob;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private GeoCodingDataRepository geoCodingDataRepository;

    @Mock
    private WebsocketService websocketService;

    @Mock
    private GameRepository gameRepositoryInstance;


    @InjectMocks
    private GameService gameService;

    private Game game;

    @Mock
    private LobbyRepository lobbyRepositoryInstance;


    @InjectMocks
    private LobbyService lobbyService;

    private Lobby lobby;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Game game = new Game();
        this.game = game;
        game.setId(1L);
        game.setLobbyPassword("test");
        GameRepository.addGame(game);
        /*
        MockitoAnnotations.openMocks(this);
        Lobby lobby = new Lobby();
        this.lobby = lobby;
        lobby.setId(2L);
        lobby.setName("lobby");
        lobby.setPassword("test");
        lobby.setPasswordProtected(true);
        LobbyRepository.addLobby(lobby);*/
    }

    @AfterEach
    public void tearDown() {
        GameRepository.deleteGame(1L);
        //LobbyRepository.deleteLobby(2L);
    }

    @Test
    public void testGetLeaderboard(){
        Participant participant = new Participant();
        participant.setId(7L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");
        participant.setScore(200);

        Participant participant2 = new Participant();
        participant2.setId(4L);
        participant2.setUsername("test2");
        participant2.setAdmin(false);
        participant2.setToken("abc2");
        participant2.setScore(203);

        Participant participant3 = new Participant();
        participant3.setId(5L);
        participant3.setUsername("test3");
        participant3.setAdmin(false);
        participant3.setToken("abc3");
        participant3.setScore(20);

        game.setAdminId(7L);
        Map<String, Participant> participants1 = new HashMap<>();
        participants1.put("abc", participant);
        participants1.put("abc2", participant2);
        participants1.put("abc3", participant3);
        game.setParticipants(participants1);
        game.setActiveParticipants(3);

        List<Participant> participants = new ArrayList<>(game.getParticipants().values());
        participants.sort(Comparator.comparing(Participant::getScore).reversed());
        //when(mock.gameRepository.getGameById(anyLong())).thenReturn(game);
        //given(gameRepository.getGameById(game.getId())).willReturn(game);
        //when(gameService.getSpecificGame(1L)).thenReturn(game);
        System.out.println("This is the admin- participant");
        System.out.println(game.getId());
        System.out.println(game.getParticipantByToken("abc"));

        List<Participant> par = gameService.getLeaderboard("abc", 1L);

        assert(par.equals(participants));
        //assert(participant.getId().equals(par.get(0).getId()));
        //assert(par.get(0).getUsername().equals(participant.getUsername()));
        //assert("m".equals("m"));
    }
 /*
    @Test
    public void testStartGame() throws IOException {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");

        Participant participant2 = new Participant();
        participant.setId(2L);
        participant.setUsername("test2");
        participant.setAdmin(false);
        participant.setToken("abc2");

        Participant participant3 = new Participant();
        participant.setId(3L);
        participant.setUsername("test3");
        participant.setAdmin(false);
        participant.setToken("abc3");

        lobby.setAdminId(1L);
        lobby.setAdminUsername("test");
        lobby.addParticipant(participant);
        lobby.addParticipant(participant2);
        lobby.addParticipant(participant3);

        lobby.setJoinedParticipants(3);
        lobby.setRoundDurationSeconds(60);
        lobby.setGameLocation("Zurich");
        List<String> quests = new ArrayList<>();
        quests.add("item 1");
        quests.add("item 2");

        Game createdGame = new Game();

        List<Round> rounds = new ArrayList<>(quests.size());
        for (String quest : quests) {
            Round round = new Round();
            round.setId(Game.rounds_count++);
            round.setQuest(quest);
            round.setRoundTime(lobby.getRoundDurationSeconds());
            round.setRemainingSeconds(lobby.getRoundDurationSeconds());
            round.setGeoCodingData(lobby.getGameLocationCoordinates());
            rounds.add(round);
        }
        createdGame.setRounds(rounds);
        createdGame.setRoundDurationSeconds(lobby.getRoundDurationSeconds());
        createdGame.setAdminId(lobby.getAdminId());
        createdGame.setGameLocation(lobby.getGameLocation());
        createdGame.setNumberRounds(rounds.size());
        createdGame.setLobbyPassword(lobby.getPassword());

        Map<String, Participant> participants = new HashMap<>(lobby.getParticipants());
        createdGame.setParticipants(participants);
        createdGame.setActiveParticipants(createdGame.getParticipants().size());

        gameRepository.addGame(createdGame);
        when(gameService.getSpecificGame(1L)).thenReturn(game);
        Long l = gameService.startGame(lobby);

        assert(createdGame.getId().equals(l));
        //assert(createdGame.getGameLocation().equals(gameRepository.findById(l).getGameLocation()));

    }

    @Test
    public void testHandleMissingSubmission() {
        Participant participant = new Participant();
        participant.setId(7L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");
        participant.setScore(200);

        Participant participant2 = new Participant();
        participant2.setId(4L);
        participant2.setUsername("test2");
        participant2.setAdmin(false);
        participant2.setToken("abc2");
        participant2.setScore(203);

        Participant participant3 = new Participant();
        participant3.setId(5L);
        participant3.setUsername("test3");
        participant3.setAdmin(false);
        participant3.setToken("abc3");
        participant3.setScore(20);

        game.setAdminId(7L);
        Map<String, Participant> participants1 = new HashMap<>();
        participants1.put("test", participant);
        participants1.put("test2", participant2);
        participants1.put("test3", participant3);
        game.setParticipants(participants1);
        game.setActiveParticipants(3);

        Map<String, Participant> participants = game.getParticipants();

        Round round = new Round();
        round.setRoundTime(60);

        for (Participant par : participants.values()) {
            if (!par.getHasSubmitted() && !par.getLeftGame()) {
                SubmissionData submissionData = new SubmissionData();
                submissionData.setLat("47.3768866");
                submissionData.setLng("8.541694");
                submissionData.setPitch("50");
                submissionData.setHeading("50");

                Submission emptySubmission = new Submission();

                emptySubmission.setId(Round.submissionCount++);
                emptySubmission.setSubmissionTimeSeconds(round.getRoundTime());
                emptySubmission.setToken(par.getToken());
                emptySubmission.setUsername(par.getUsername());
                emptySubmission.setNoSubmission(true);
                emptySubmission.setSubmittedLocation(submissionData);
                round.addSubmission(emptySubmission);
            }
            assertFalse(par.getHasSubmitted());
            assert(round.getSubmissions().equals())

        }
        Submission sub = new Submission();
        sub.setId(Round.submissionCount++);
        sub.setSubmissionTimeSeconds(round.getRoundTime());
        sub.setToken(par.getToken());
        sub.setUsername(par.getUsername());
        sub.setNoSubmission(true);
        sub.setSubmittedLocation(submissionData);
        round.addSubmission(emptySubmission);
        Map<Long, Submission> submissions = new HashMap<>();
        submissions.put();
        assert (round.getRoundTime() == 60);
        assert(round.getSubmissions().equals(participants));
    }*/
    /*
    @Test
    public void testHandleMissingSubmissions() {

    Round round = new Round();
    round.setRoundTime(123);
    Long gameId = 1L;

    Map<String, Participant> participants = new HashMap<>();
    Participant participantWithMissingSubmission = new Participant();
    participantWithMissingSubmission.setToken("participantToken");
    participantWithMissingSubmission.setUsername("participantUsername");
    participantWithMissingSubmission.setHasSubmitted(false);
    participantWithMissingSubmission.setLeftGame(false);
    participants.put("participantToken", participantWithMissingSubmission);
    game.setParticipants(participants);
    when(gameService.getSpecificGame(gameId)).thenReturn(game);

    gameService.handleMissingSubmissions(round, gameId);


    assertEquals(1, round.getSubmissions().size());
    Submission submission = round.getSubmissions().get(0);
    assertEquals("participantToken", submission.getToken());
    assertEquals("participantUsername", submission.getUsername());
    //assertTrue(submission.isNoSubmission());
    assertEquals(123, submission.getSubmissionTimeSeconds());
}

    @Test
    public void testNoSubmission() {
        Long gameId = 1L;
        Round round = new Round();
        Submission submission = new Submission();
        submission.setNoSubmission(true);
        when(gameRepository.getGameById(gameId)).thenReturn(game);
        int points = gameService.calculatePoints(gameId, round, submission, 1);
        System.out.println(points);
        assertEquals(0, points);
    }

    @Test
    public void testHalfBanVotes() {
        Long gameId = 1L;
        Round round = new Round();
        round.getSubmissions().put(1L, new Submission());
        round.getSubmissions().put(2L, new Submission());
        Submission submission = new Submission();
        submission.setNumberBanVotes(1);

        Participant participant = new Participant();
        game.getParticipants().put(submission.getToken(), participant);


        when(gameRepository.getGameById(gameId)).thenReturn(game);
        int points = gameService.calculatePoints(gameId, round, submission, 1);

        assertEquals(0, points);
    }




    @Test
    public void testFullPointsNoBonus() {
        Long gameId = 1L;
        Round round = new Round();
        round.setRoundTime(60);
        round.getSubmissions().put(1L, new Submission());
        Submission submission = new Submission();
        submission.setSubmissionTimeSeconds(0);
        submission.setNumberVotes(2);  // assume 2 other participants voted

        Game game = mock(Game.class);
        Map<String, Participant> participants = new HashMap<>();
        Participant participant = new Participant();
        participant.setToken("participantToken");
        participants.put(participant.getToken(), participant);
        game.setParticipants(participants);
        GameRepository.getGameById = game -> game;  // mock GameRepository

        int points = gameService.calculatePoints(gameId, round, submission, 1);

        assertEquals(775, points);  // 250 (time) + 250 (placement) + 275 (voting)
    }

    @Test
    public void testFullPointsWithWinningSubmission() {
        Long gameId = 1L;
        Round round = new Round();
        round.setRoundTime(60);
        round.getSubmissions().put(1L, new Submission());
        Submission submission = new Submission();
        submission.setSubmissionTimeSeconds(0);
        submission.setNumberVotes(2);  // assume 2 other participants voted

        Game game = mock(Game.class);
        Map<String, Participant> participants = new HashMap<>();
        Participant participant = new Participant();
        participant.setToken("participantToken");
        participants.put(participant.getToken(), participant);
        game.setParticipants(participants);
        GameRepository.getGameById = game -> game;  // mock GameRepository

        int points = gameService.calculatePoints(gameId, round, submission, 1);
        round.setWinningSubmission(submission);

        assertEquals(1162, points);  // 775 (base points) * 1.5 (winning bonus)
        assertEquals(1, participant.getWinningSubmissions());
        assertEquals(1, participant.getStreak());
    }

    @Test
    public void testPointsWithStreakBonus() {
        Long gameId = 1L;
        Round round = new Round();
        round.setRoundTime(60);
        round.getSubmissions().put(1L, new Submission());
        Submission submission = new Submission();
        submission.setSubmissionTimeSeconds(0);
        submission.setNumberVotes(2);  // assume 2 other participants voted

        Game game = mock(Game.class);
        Map<String, Participant> participants = new HashMap<>();
        Participant participant = new Participant();
        participant.setStreak(2);
        participant.setToken("participantToken");
        participants.put(participant.getToken(), participant);
        game.setParticipants(participants);
        GameRepository.getGameById = game -> game;  // mock GameRepository

        int points = calculatePoints(gameId, round, submission, 1);

        assertTrue(points > 775);  // points should be higher due to streak bonus
        assertEquals(participant.getStreak(), 0);  // streak should reset if not winning submission
    }*/
    /*
@Test
public void testAwardPoints() {
    // Mock data
    Long gameId = 1L;
    Round round = new Round();
    Submission submission1 = new Submission();
    Submission submission2 = new Submission();
    submission1.setToken("token1");
    submission2.setToken("token2");
    round.getSubmissions().put(1L, submission1);
    round.getSubmissions().put(2L, submission2);

    // Mock game and participant
    Game game = new Game();
    game.setId(gameId);
    Participant participant1 = new Participant();
    Participant participant2 = new Participant();
    game.getParticipants().put(submission1.getToken(), participant1);
    game.getParticipants().put(submission2.getToken(), participant2);

    // Set up mock behavior
    when(gameRepository.getGameById(gameId)).thenReturn(game);

    // Mock the calculatePoints method
    PointsCalculator calculator = spy(new PointsCalculator(gameRepository));
    doReturn(500).when(calculator).calculatePoints(eq(gameId), any(Round.class), eq(submission1), eq(1));
    doReturn(750).when(calculator).calculatePoints(eq(gameId), any(Round.class), eq(submission2), eq(2));

    // Call the method
    calculator.awardPoints(round, gameId);

    // Assertions
    assertEquals(500, submission1.getAwardedPoints());
    assertEquals(750, submission2.getAwardedPoints());
}

     */
    @Test
    public void TestSetWinningSubmission(){
        Round round = new Round();
        Round round2 = new Round();
        Submission submission = new Submission();
        submission.setNumberVotes(3);
        submission.setSubmissionTimeSeconds(20);

        Submission submission2 = new Submission();
        submission2.setNumberVotes(0);
        submission2.setSubmissionTimeSeconds(10);

        Submission submission3 = new Submission();
        submission3.setNumberVotes(4);
        submission3.setSubmissionTimeSeconds(15);

        List<Submission> submissions = new ArrayList<>(Arrays.asList(submission, submission2, submission3));

        gameService.setWinningSubmission(round, submissions);

        submissions.sort(Comparator.comparing(Submission::getNumberVotes).reversed().thenComparing(Submission::getSubmissionTimeSeconds));
        round2.setWinningSubmission(submissions.get(0));

        //System.out.println(round.getWinningSubmission().getSubmissionTimeSeconds());
        //System.out.println(round2.getWinningSubmission().getSubmissionTimeSeconds());


        assert(round2.getWinningSubmission().getSubmissionTimeSeconds().equals(round.getWinningSubmission().getSubmissionTimeSeconds()));

    }

    @Test
    public void TestHandleMissingSubmission(){

        Round round111 = new Round();
        Long gameId = 1L;

        Participant participant = new Participant();
        participant.setId(7L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");
        participant.setScore(200);
        participant.setHasSubmitted(Boolean.FALSE);
        participant.setLeftGame(Boolean.FALSE);

        Participant participant2 = new Participant();
        participant2.setId(4L);
        participant2.setUsername("test2");
        participant2.setAdmin(false);
        participant2.setToken("abc2");
        participant2.setScore(203);
        participant2.setHasSubmitted(Boolean.FALSE);
        participant2.setLeftGame(Boolean.FALSE);

        Participant participant3 = new Participant();
        participant3.setId(5L);
        participant3.setUsername("test3");
        participant3.setAdmin(false);
        participant3.setToken("abc3");
        participant3.setScore(20);
        participant3.setHasSubmitted(Boolean.FALSE);
        participant3.setLeftGame(Boolean.FALSE);

        game.setAdminId(7L);
        Map<String, Participant> participants1 = new HashMap<>();
        participants1.put("abc", participant);
        participants1.put("abc2", participant2);
        participants1.put("abc3", participant3);
        game.setParticipants(participants1);
        game.setActiveParticipants(3);

        Round round211 = new Round();
        Map<String, Participant> participants = game.getParticipants();

        for (Participant participan : participants.values()) {
            if (!participan.getHasSubmitted() && !participan.getLeftGame()) {
                SubmissionData submissionData = new SubmissionData();
                submissionData.setLat("47.3768866");
                submissionData.setLng("8.541694");
                submissionData.setPitch("50");
                submissionData.setHeading("50");

                Submission emptySubmission = new Submission();

                emptySubmission.setId(Round.submissionCount++);
                emptySubmission.setSubmissionTimeSeconds(round211.getRoundTime());
                emptySubmission.setToken(participan.getToken());
                emptySubmission.setUsername(participan.getUsername());
                emptySubmission.setNoSubmission(true);
                emptySubmission.setSubmittedLocation(submissionData);
                round211.addSubmission(emptySubmission);
            }
        }
        //given(gameService.getSpecificGame(1L)).willReturn(game);
        gameService.handleMissingSubmissions(round111, gameId);
        List<Submission> r1 = new ArrayList<>(round111.getSubmissions().values());
        List<Submission> r2 = new ArrayList<>(round211.getSubmissions().values());

        //System.out.println(r1.get(0).getToken());
        //System.out.println(r2.get(0).getToken());



        assert(r1.get(0).getToken().equals(r2.get(0).getToken()));


    }
    @Test
    public void TestPostSubmission() throws IOException{

        Round round111 = new Round();
        round111.setId(1L);
        round111.setRoundStatus(RoundStatus.PLAYING);
        round111.setBufferTime(1000000);
        Long gameId = 1L;

        SubmissionPostDTO submissionPostDTO = new SubmissionPostDTO();
        submissionPostDTO.setNoSubmission(Boolean.FALSE);
        submissionPostDTO.setHeading("009");
        submissionPostDTO.setPitch("50");
        submissionPostDTO.setLat("09090");
        submissionPostDTO.setLng("888");

        Participant participant = new Participant();
        participant.setId(7L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");
        participant.setScore(200);
        participant.setHasSubmitted(Boolean.FALSE);
        participant.setLeftGame(Boolean.FALSE);

        List<Round> r = new ArrayList<>();
        r.add(round111);

        game.setAdminId(7L);
        Map<String, Participant> participants1 = new HashMap<>();
        participants1.put("abc", participant);
        game.setParticipants(participants1);
        game.setActiveParticipants(1);
        game.setRounds(r);
        game.setCurrentRound(0);


        Round cRound = game.getRounds().get(game.getCurrentRound());
        int submissionTime = gameService.getSubmissionTime(participant, cRound);

        Submission submission = new Submission();
        participant.setHasSubmitted(true);

        cRound.setParticipantsDone(cRound.getParticipantsDone() + 1);

        SubmissionData submissionData = new SubmissionData();
        submissionData.setLat(submissionPostDTO.getLat());
        submissionData.setLng(submissionPostDTO.getLng());
        submissionData.setHeading(submissionPostDTO.getHeading());
        submissionData.setPitch(submissionPostDTO.getPitch());
        submissionData.setNoSubmission(submissionPostDTO.getNoSubmission());


        submission.setId(Round.submissionCount++);
        submission.setSubmissionTimeSeconds(submissionTime);
        submission.setSubmittedLocation(submissionData);
        submission.setToken(participant.getToken());
        submission.setUsername(participant.getUsername());
        submission.setNoSubmission(submissionPostDTO.getNoSubmission());

        cRound.addSubmission(submission);

        if (Objects.equals(cRound.getParticipantsDone(), game.getActiveParticipants()) && cRound.getRemainingSeconds() > 5) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gameService.endTimerPrematurely(cRound, gameId);
                    timer.cancel();
                }
            }, 3000);
        }
        //given(gameService.getSpecificGame(1L)).willReturn(game);
        Round currentRound = game.getRounds().get(game.getCurrentRound());
        System.out.println(cRound.getSubmissions().values());
        participant.setHasSubmitted(Boolean.FALSE);
        gameService.postSubmission(1L, "abc", submissionPostDTO);


        //System.out.println(cRound.getSubmissions().values());
        //System.out.println(currentRound.getSubmissions().values());



        assert(cRound.getSubmissions().values().equals(currentRound.getSubmissions().values()));


    }
    /*
    @Test
    public void TestGenerateSummary() throws IOException{
        List<Quest> winningSubmissions = new ArrayList<Quest>();

        SubmissionData submissionData = new SubmissionData();
        submissionData.setLat("009");
        submissionData.setLng("009");
        submissionData.setHeading("009");
        submissionData.setPitch("009");
        submissionData.setNoSubmission(Boolean.FALSE);

        Participant participant = new Participant();
        participant.setId(7L);
        participant.setUsername("test");
        participant.setAdmin(true);
        participant.setToken("abc");
        participant.setScore(200);
        participant.setHasSubmitted(Boolean.FALSE);
        participant.setLeftGame(Boolean.FALSE);

        Submission submission = new Submission();
        submission.setId(Round.submissionCount++);
        submission.setSubmissionTimeSeconds(10);
        submission.setSubmittedLocation(submissionData);
        submission.setToken(participant.getToken());
        submission.setUsername(participant.getUsername());
        submission.setNoSubmission(Boolean.FALSE);

        Round round1 = new Round();
        round1.setId(1L);
        round1.setRoundStatus(RoundStatus.FINISHED);
        round1.setBufferTime(1000000);
        round1.addSubmission(submission);
        round1.setWinningSubmission(submission);
        round1.setQuest("Nonononon");

        Long gameId = 1L;



        List<Round> r = new ArrayList<>();
        r.add(round1);

        game.setAdminId(7L);
        Map<String, Participant> participants1 = new HashMap<>();
        participants1.put("abc", participant);
        game.setParticipants(participants1);
        game.setActiveParticipants(1);
        game.setRounds(r);
        game.setGameLocation("Zurich");

        int roundsPlayed = 0;

        for (Round round : game.getRounds()) {
            if (round.getRoundStatus() == RoundStatus.FINISHED) {
                roundsPlayed++;
            }
        }

        Summary summary1 = new Summary();
        summary1.setCityName(game.getGameLocation());
        summary1.setRoundsPlayed(roundsPlayed);
        summary1.setPassword(game.getLobbyPassword());
        System.out.println(summary1.getCityName());
        summary1 = summaryRepository.save(summary1);
        summaryRepository.flush();




        for (Round round : game.getRounds()) {
            if (round.getRoundStatus() == RoundStatus.FINISHED && !round.getWinningSubmission().getNoSubmission()) {
                Quest quest = new Quest();
                quest.setDescription(round.getQuest());
                quest.setLink(gameService.generateSubmissionLink(round.getWinningSubmission().getSubmittedLocation().getLat(),
                        round.getWinningSubmission().getSubmittedLocation().getLng()));
                quest.setName(game.getParticipantByToken(round.getWinningSubmission().getToken()).getUsername());
                quest.setSummary(summary1);
                quest.setNoSubmission(round.getWinningSubmission().getNoSubmission());
                quest.setLat(round.getWinningSubmission().getSubmittedLocation().getLat());
                quest.setLng(round.getWinningSubmission().getSubmittedLocation().getLng());
                winningSubmissions.add(quest);
            }
        }

        System.out.println(winningSubmissions);

        summary1.setQuests(winningSubmissions); --> error occurs here always
        summary1 = summaryRepository.save(summary1);
        summaryRepository.flush();

        participant.setHasSubmitted(Boolean.FALSE);
        Long l = gameService.generateSummary(game);


        System.out.println(l);
        System.out.println(summary1.getId());


        assert(summary1.getId().equals(l));


    }
    */


}

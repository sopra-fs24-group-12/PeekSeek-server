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

    }

    @AfterEach
    public void tearDown() {
        GameRepository.deleteGame(1L);

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



        List<Participant> par = gameService.getLeaderboard("abc", 1L);

        assert(par.equals(participants));
        //assert(participant.getId().equals(par.get(0).getId()));
        //assert(par.get(0).getUsername().equals(participant.getUsername()));
        //assert("m".equals("m"));
    }

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

        gameService.handleMissingSubmissions(round111, gameId);
        List<Submission> r1 = new ArrayList<>(round111.getSubmissions().values());
        List<Submission> r2 = new ArrayList<>(round211.getSubmissions().values());



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

        Round currentRound = game.getRounds().get(game.getCurrentRound());
        System.out.println(cRound.getSubmissions().values());
        participant.setHasSubmitted(Boolean.FALSE);
        gameService.postSubmission(1L, "abc", submissionPostDTO);






        assert(cRound.getSubmissions().values().equals(currentRound.getSubmissions().values()));


    }

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

        GeoCodingData gcd = new GeoCodingData();
        gcd.setId(2L);
        gcd.setResLatNe("72563");
        gcd.setResLngNe("98829");
        gcd.setResLatSw("87362");
        gcd.setResLngSw("92866");
        gcd.setLng("65243");
        gcd.setLat("092762");

        Round round1 = new Round();
        round1.setId(1L);
        round1.setRoundStatus(RoundStatus.FINISHED);
        round1.setBufferTime(1000000);
        round1.addSubmission(submission);
        round1.setWinningSubmission(submission);
        round1.setQuest("Nonononon");
        round1.setGeoCodingData(gcd);

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
        summary1.setId(game.getId());
        System.out.println(summary1.getCityName());

        //when(summaryRepository.save(any())).thenReturn(summary1);
        given(summaryRepository.save(any())).willReturn(summary1);

        //Round


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

        summary1.setQuests(winningSubmissions);
        summary1 = summaryRepository.save(summary1);
        summaryRepository.flush();

        participant.setHasSubmitted(Boolean.FALSE);
        Long l = gameService.generateSummary(game);


        System.out.println(l);
        System.out.println(summary1.getId());


        assert(summary1.getId().equals(l));


    }

    @Test
    public void testStartNextRound_Success() {
        // Given
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setCurrentRound(0);
        game.setNumberRounds(3);

        Round round1 = new Round();
        round1.setId(1L);
        round1.setRoundStatus(RoundStatus.FINISHED);
        round1.setBufferTime(1000000);
        round1.setQuest("Nonononon");


        Round round = new Round();
        round.setRoundStatus(RoundStatus.FINISHED);

        List<Round> r = new ArrayList<>();
        r.add(round);
        r.add(round1);

        game.setRounds(r);

        Map<String, Participant> participants = new HashMap<>();
        participants.put("participant1", new Participant());
        participants.put("participant2", new Participant());

        game.setParticipants(participants);

        for (Participant participant : participants.values()) {
            participant.setHasSubmitted(false);
            participant.setHasVoted(false);
            participant.setPointsThisRound(0);
        }

        gameRepository.addGame(game);
        gameService.startNextRound(gameId);


        assertEquals(1, game.getCurrentRound());
        //assertEquals(RoundStatus.VOTING, game.getRounds().get(1).getRoundStatus());
        assertTrue(game.getParticipants().values().stream().allMatch(participant ->
                !participant.getHasSubmitted() && !participant.getHasVoted() && participant.getPointsThisRound() == 0));

    }

    @Test
    public void testStartNextRound_Fail() {
        // Given
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setCurrentRound(0);
        game.setNumberRounds(3);

        Round round1 = new Round();
        round1.setId(1L);
        round1.setRoundStatus(RoundStatus.PLAYING);
        round1.setBufferTime(1000000);
        round1.setQuest("Nonononon");


        Round round = new Round();
        round.setRoundStatus(RoundStatus.PLAYING);

        List<Round> r = new ArrayList<>();
        r.add(round);
        r.add(round1);

        game.setRounds(r);

        Map<String, Participant> participants = new HashMap<>();
        participants.put("participant1", new Participant());
        participants.put("participant2", new Participant());

        game.setParticipants(participants);

        for (Participant participant : participants.values()) {
            participant.setHasSubmitted(false);
            participant.setHasVoted(false);
            participant.setPointsThisRound(0);
        }

        gameRepository.addGame(game);

        assertThrows(ResponseStatusException.class, () -> gameService.startNextRound(gameId));
    }


    @Test
    public void testStartGame_Success() {
        List<String> q = new ArrayList<>();
        q.add("abc");
        q.add("def");
        q.add("ghi");

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

        Lobby l = new Lobby();
        l.setQuests(q);
        l.setAdminId(7L);
        l.setParticipants(participants1);
        l.setId(1L);
        l.setRoundDurationSeconds(30);
        l.setJoinedParticipants(3);

        Game createdGame1 = new Game();
        List<Round> rounds = new ArrayList<>(q.size());
        for (String quest : q) {
            Round round = new Round();
            round.setId(Game.rounds_count++);
            round.setQuest(quest);
            round.setRoundTime(l.getRoundDurationSeconds());
            round.setRemainingSeconds(l.getRoundDurationSeconds());
            round.setGeoCodingData(l.getGameLocationCoordinates());
            rounds.add(round);
        }
        createdGame1.setRounds(rounds);
        createdGame1.setRoundDurationSeconds(l.getRoundDurationSeconds());
        createdGame1.setAdminId(l.getAdminId());
        createdGame1.setGameLocation(l.getGameLocation());
        createdGame1.setNumberRounds(rounds.size());
        createdGame1.setLobbyPassword(l.getPassword());
        createdGame1.setId(gameService.startGame(l));

        Long lon = gameService.startGame(l);

        assertEquals(lon, createdGame1.getId()+1L);
    }

    @Test
    public void testStartGame_Fail1() {
        List<String> q = new ArrayList<>();
        q.add("abc");
        q.add("def");
        q.add("ghi");

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
        game.setActiveParticipants(1);

        Lobby l = new Lobby();
        l.setQuests(q);
        l.setAdminId(7L);
        l.setParticipants(participants1);
        l.setId(1L);
        l.setRoundDurationSeconds(30);
        l.setJoinedParticipants(1);

        Game createdGame1 = new Game();
        List<Round> rounds = new ArrayList<>(q.size());
        for (String quest : q) {
            Round round = new Round();
            round.setId(Game.rounds_count++);
            round.setQuest(quest);
            round.setRoundTime(l.getRoundDurationSeconds());
            round.setRemainingSeconds(l.getRoundDurationSeconds());
            round.setGeoCodingData(l.getGameLocationCoordinates());
            rounds.add(round);
        }
        createdGame1.setRounds(rounds);
        createdGame1.setRoundDurationSeconds(l.getRoundDurationSeconds());
        createdGame1.setAdminId(l.getAdminId());
        createdGame1.setGameLocation(l.getGameLocation());
        createdGame1.setNumberRounds(rounds.size());
        createdGame1.setLobbyPassword(l.getPassword());


        assertThrows(ResponseStatusException.class, () -> gameService.startGame(l));
    }
    @Test
    public void testStartGame_Fail2() {
        List<String> q = new ArrayList<>();
        q.add("abc");
        q.add("def");
        q.add("ghi");

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

        Lobby l = new Lobby();
        l.setAdminId(7L);
        l.setParticipants(participants1);
        l.setId(1L);
        l.setRoundDurationSeconds(30);
        l.setJoinedParticipants(3);

        Game createdGame1 = new Game();
        List<Round> rounds = new ArrayList<>(q.size());
        for (String quest : q) {
            Round round = new Round();
            round.setId(Game.rounds_count++);
            round.setQuest(quest);
            round.setRoundTime(l.getRoundDurationSeconds());
            round.setRemainingSeconds(l.getRoundDurationSeconds());
            round.setGeoCodingData(l.getGameLocationCoordinates());
            rounds.add(round);
        }
        createdGame1.setRounds(rounds);
        createdGame1.setRoundDurationSeconds(l.getRoundDurationSeconds());
        createdGame1.setAdminId(l.getAdminId());
        createdGame1.setGameLocation(l.getGameLocation());
        createdGame1.setNumberRounds(rounds.size());
        createdGame1.setLobbyPassword(l.getPassword());


        assertThrows(ResponseStatusException.class, () -> gameService.startGame(l));
    }




}

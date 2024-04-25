package ch.uzh.ifi.hase.soprafs24.rest.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;
import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Quest;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {

    @Test
    public void test_convert_Lobby_To_LobbyGetDTO() {
        Lobby lobby = new Lobby();
        lobby.setId(3L);
        lobby.setMaxParticipants(5);
        lobby.setJoinedParticipants(3);
        lobby.setName("Beispiel");

        LobbyGetDTO lobbyGetDTO = DTOMapper.INSTANCE.convertLobbyToLobbyGetDTO(lobby);
        assertEquals(lobby.getId(), lobbyGetDTO.getId());
        assertEquals(lobby.getName(), lobbyGetDTO.getName());
        assertEquals(lobby.getJoinedParticipants(), lobbyGetDTO.getJoinedParticipants());
        assertEquals(lobby.getMaxParticipants(), lobbyGetDTO.getMaxParticipants());


    }

    @Test
    public void test_convert_Lobby_To_LobbyGetInformationDTO() {
        Lobby lobby = new Lobby();
        GeoCodingData geoCodingData = new GeoCodingData();
        geoCodingData.setLocation("Munich");
        lobby.setName("Beispiel");
        lobby.setGameLocation("Munich");
        lobby.setRoundDurationSeconds(33);
        lobby.setAdminUsername("Admin");
        lobby.setGameLocationCoordinates(geoCodingData);
        lobby.setQuests(Arrays.asList("Quest1", "Quest2", "Quest3"));

        LobbyGetInformationDTO lobbyGetInformationDTO = DTOMapper.INSTANCE.convertLobbyToLobbyGetInformationDTO(lobby);
        assertEquals(lobby.getName(), lobbyGetInformationDTO.getName());
        assertEquals(lobby.getGameLocation(), lobbyGetInformationDTO.getGameLocation());
        assertEquals(lobby.getRoundDurationSeconds(), lobbyGetInformationDTO.getRoundDurationSeconds());
        assertEquals(lobby.getAdminUsername(), lobbyGetInformationDTO.getAdminUsername());
        assertEquals(lobby.getGameLocationCoordinates(), lobbyGetInformationDTO.getGameLocationCoordinates());
        assertEquals(lobby.getQuests(), lobbyGetInformationDTO.getQuests());}

    @Test
    public void test_convert_Participant_To_ParticipantGetDTO() {
        Participant participant = new Participant();
        participant.setId(3L);
        participant.setAdmin(Boolean.TRUE);
        participant.setUsername("Beispiel");
        participant.setScore(33);
        participant.setStreak(2);
        participant.setLeftGame(Boolean.TRUE);

        ParticipantGetDTO participantGetDTO = DTOMapper.INSTANCE.convertParticipantToParticipantGetDTO(participant);
        assertEquals(participant.getId(), participantGetDTO.getId());
        assertEquals(participant.getAdmin(), participantGetDTO.getAdmin());
        assertEquals(participant.getUsername(), participantGetDTO.getUsername());
        assertEquals(participant.getScore(), participantGetDTO.getScore());
        assertEquals(participant.getStreak(), participantGetDTO.getStreak());
        assertEquals(participant.getLeftGame(), participantGetDTO.getLeftGame());

    }

    @Test
    public void test_convert_Round_To_GameRoundGetDTO() {
        Game game = new Game();
        game.setId(3L);
        game.setRoundDurationSeconds(33);
        game.setGameLocation("Munich");
        game.setCurrentRound(3);
        game.setNumberRounds(5);
        game.setAdminId(1L);

        Round round = new Round();
        RoundStatus roundStatus = RoundStatus.PLAYING;
        round.setQuest("Park");
        round.setRemainingSeconds(33);
        round.setRoundStatus(roundStatus);
        round.setRoundTime(33);
        round.setGeoCodingData(new GeoCodingData());

        RoundGetDTO roundGetDTO = DTOMapper.INSTANCE.convertRoundToGameRoundGetDTO(round, game);
        assertEquals(round.getQuest(), roundGetDTO.getQuest());
        assertEquals(game.getCurrentRound() + 1, roundGetDTO.getCurrentRound());
        assertEquals(game.getNumberRounds(), roundGetDTO.getNumberRounds());
        assertEquals(round.getRemainingSeconds(), roundGetDTO.getRemainingSeconds());
        assertEquals(round.getRoundStatus(), roundGetDTO.getRoundStatus());
        assertEquals(round.getRoundTime(), roundGetDTO.getRoundTime());
        assertEquals(round.getGeoCodingData(), roundGetDTO.getGeoCodingData());

    }

    @Test
    public void test_convert_Game_To_GameGetDTO() {
        Game game = new Game();
        game.setId(3L);
        game.setRoundDurationSeconds(33);
        game.setGameLocation("Munich");
        game.setCurrentRound(3);
        game.setNumberRounds(5);
        game.setAdminId(1L);

        GameGetDTO gameGetDTO = DTOMapper.INSTANCE.convertGameToGameGetDTO(game);
        assertEquals(game.getId(), gameGetDTO.getId());
        assertEquals(game.getRoundDurationSeconds(), gameGetDTO.getRoundDurationSeconds());
        assertEquals(game.getGameLocation(), gameGetDTO.getGameLocation());
        assertEquals(game.getCurrentRound(), gameGetDTO.getCurrentRound());
        assertEquals(game.getNumberRounds(), gameGetDTO.getNumberRounds());
        assertEquals(game.getAdminId(), gameGetDTO.getAdminId());

    }

    @Test
    public void test_convert_Submission_To_SubmissionGetDTO() {
        Submission submission = new Submission();
        SubmissionData submissionData = new SubmissionData();
        submissionData.setLat("47.00000");
        submissionData.setLng("8.00000");
        submission.setId(3L);
        submission.setSubmissionTimeSeconds(33);
        submission.setSubmittedLocation(submissionData);
        submission.setNumberVotes(3);
        submission.setNumberBanVotes(2);
        submission.setAwardedPoints(5);
        submission.setNoSubmission(Boolean.TRUE);

        SubmissionGetDTO submissionGetDTO = DTOMapper.INSTANCE.convertSubmissionToSubmissionGetDTO(submission);
        assertEquals(submission.getId(), submissionGetDTO.getId());
        assertEquals(submission.getSubmissionTimeSeconds(), submissionGetDTO.getSubmissionTimeSeconds());
        assertEquals(submission.getSubmittedLocation(), submissionGetDTO.getSubmittedLocation());
        assertEquals(submission.getNumberVotes(), submissionGetDTO.getNumberVotes());
        assertEquals(submission.getNumberBanVotes(), submissionGetDTO.getNumberBanVotes());
        assertEquals(submission.getAwardedPoints(), submissionGetDTO.getAwardedPoints());
        assertEquals(submission.getNoSubmission(), submissionGetDTO.getNoSubmission());

    }

    @Test
    public void test_convert_Participant_To_LeaderboardGetDTO() {
        Participant participant = new Participant();
        participant.setId(3L);
        participant.setUsername("Beispiel");
        participant.setScore(33);
        participant.setStreak(2);

        LeaderboardGetDTO leaderboardGetDTO = DTOMapper.INSTANCE.convertParticipantToLeaderboardGetDTO(participant);
        assertEquals(participant.getId(), leaderboardGetDTO.getId());
        assertEquals(participant.getUsername(), leaderboardGetDTO.getUsername());
        assertEquals(participant.getScore(), leaderboardGetDTO.getScore());
        assertEquals(participant.getStreak(), leaderboardGetDTO.getStreak());


    }

    @Test
    public void test_convert_Summary_To_SummaryGetDTO() {
        Summary summary = new Summary();
        summary.setId(3L);
        summary.setCityName("Munich");
        summary.setRoundsPlayed(3);
        Quest quest = new Quest();
        quest.setName("Park");
        summary.setQuests(Arrays.asList(quest));
        SummaryGetDTO summaryGetDTO = DTOMapper.INSTANCE.convertSummaryToSummaryGetDTO(summary);
        assertEquals(summary.getId(), summaryGetDTO.getId());
        assertEquals(summary.getCityName(), summaryGetDTO.getCityName());
        assertEquals(summary.getRoundsPlayed(), summaryGetDTO.getRoundsPlayed());
        assertEquals(summary.getQuests(), summaryGetDTO.getQuests());
    }

}


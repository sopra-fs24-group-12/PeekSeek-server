package ch.uzh.ifi.hase.soprafs24.rest.mapper;


import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}


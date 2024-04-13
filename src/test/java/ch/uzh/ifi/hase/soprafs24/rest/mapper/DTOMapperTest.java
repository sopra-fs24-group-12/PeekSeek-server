package ch.uzh.ifi.hase.soprafs24.rest.mapper;


import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.ParticipantGetDTO;
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
}


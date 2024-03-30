package ch.uzh.ifi.hase.soprafs24.rest.mapper;


import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameRoundGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.ParticipantGetDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "maxParticipants", target = "maxParticipants")
    @Mapping(source = "joinedParticipants", target = "joinedParticipants")
    LobbyGetDTO convertLobbyToLobbyGetDTO(Lobby lobby);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "isAdmin", target = "admin")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "streak", target = "streak")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "leftGame", target = "leftGame")
    ParticipantGetDTO convertParticipantToParticipantGetDTO(Participant participant);

    @Mapping(source = "round.quest", target = "quest")
    @Mapping(source = "game.currentRound", target = "currentRound")
    @Mapping(source = "game.numberRounds", target = "numberRounds")
    GameRoundGetDTO convertRoundToGameRoundGetDTO(Round round, Game game);


}

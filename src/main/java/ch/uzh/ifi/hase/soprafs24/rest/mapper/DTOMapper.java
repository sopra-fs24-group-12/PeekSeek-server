package ch.uzh.ifi.hase.soprafs24.rest.mapper;


import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.entity.Submission;
import ch.uzh.ifi.hase.soprafs24.entity.summary.Summary;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.RoundGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.ParticipantGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.SubmissionGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
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
    @Mapping(source = "passwordProtected", target = "passwordProtected")
    LobbyGetDTO convertLobbyToLobbyGetDTO(Lobby lobby);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "usernames", target = "participants")
    @Mapping(source = "quests", target = "quests")
    @Mapping(source = "gameLocation", target = "gameLocation")
    @Mapping(source = "gameLocationCoordinates", target = "gameLocationCoordinates")
    @Mapping(source = "roundDurationSeconds", target = "roundDurationSeconds")
    @Mapping(source = "adminUsername", target = "adminUsername")
    LobbyGetInformationDTO convertLobbyToLobbyGetInformationDTO(Lobby lobby);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "streak", target = "streak")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "leftGame", target = "leftGame")
    ParticipantGetDTO convertParticipantToParticipantGetDTO(Participant participant);

    @Mapping(source = "round.quest", target = "quest")
    @Mapping(source = "game.currentRound", target = "currentRound")
    @Mapping(source = "game.numberRounds", target = "numberRounds")
    @Mapping(source = "round.remainingSeconds", target = "remainingSeconds")
    @Mapping(source = "round.roundStatus", target = "roundStatus")
    @Mapping(source = "round.roundTime", target = "roundTime")
    @Mapping(source = "round.geoCodingData", target = "geoCodingData")
    RoundGetDTO convertRoundToGameRoundGetDTO(Round round, Game game);

    @Mapping(source = "id", target = "id")
    //@Mapping(source = "participants", target = "participants")
    @Mapping(source = "roundDurationSeconds", target = "roundDurationSeconds")
    @Mapping(source = "gameLocation", target = "gameLocation")
    @Mapping(source = "currentRound", target = "currentRound")
    @Mapping(source = "numberRounds", target = "numberRounds")
    @Mapping(source = "adminId", target = "adminId")
    GameGetDTO convertGameToGameGetDTO(Game game);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "submissionTimeSeconds", target = "submissionTimeSeconds")
    @Mapping(source = "submittedLocation", target = "submittedLocation")
    @Mapping(source = "numberVotes", target = "numberVotes")
    @Mapping(source = "numberBanVotes", target = "numberBanVotes")
    @Mapping(source = "awardedPoints", target = "awardedPoints")
    @Mapping(source = "noSubmission", target = "noSubmission")
    @Mapping(source = "username", target = "username")
    SubmissionGetDTO convertSubmissionToSubmissionGetDTO(Submission submission);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "streak", target = "streak")
    //@Mapping(source = "position", target = "position")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "pointsThisRound", target = "pointsThisRound")
    @Mapping(source = "leftGame", target = "leftGame")
    LeaderboardGetDTO convertParticipantToLeaderboardGetDTO(Participant participant);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "roundsPlayed", target = "roundsPlayed")
    @Mapping(source = "quests", target = "quests")
    @Mapping(source = "lat", target = "lat")
    @Mapping(source = "lng", target = "lng")
    SummaryGetDTO convertSummaryToSummaryGetDTO(Summary summary);

}


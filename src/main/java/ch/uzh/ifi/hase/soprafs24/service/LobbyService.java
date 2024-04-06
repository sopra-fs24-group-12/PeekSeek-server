package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.entity.Round;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs24.repository.ParticipantRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.LobbyPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final ParticipantRepository participantRepository;
    private final GameRepository gameRepository;
    private final GameService gameService;

    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository,
                        @Qualifier("participantRepository") ParticipantRepository participantRepository,
                        @Qualifier("gameRepository") GameRepository gameRepository,
                        GameService gameService) {
        this.lobbyRepository = lobbyRepository;
        this.participantRepository = participantRepository;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    public Lobby createLobby(String username, String name, String password) {
        checkIfLobbyNameExists(name);

        Lobby createdLobby = new Lobby();
        createdLobby.setName(name);
        createdLobby.setPassword(password);

        Participant admin = new Participant();
        admin.setAdmin(true);
        admin.setUsername(username);
        admin.setToken(UUID.randomUUID().toString());
        admin = participantRepository.save(admin);

        createdLobby.setAdminId(admin.getId());
        createdLobby.addParticipant(admin);
        createdLobby.setJoinedParticipants(1);
        createdLobby = lobbyRepository.save(createdLobby);

        admin.setLobby(createdLobby.getId());
        admin = participantRepository.save(admin);

        return createdLobby;
    }

    public List<Lobby> getAllLobbies() {
        return this.lobbyRepository.findAll();
    }

    public List<Participant> getAllParticipants(Long lobbyId, String token) {
        Lobby lobby = lobbyRepository.findById(lobbyId).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "A lobby with this ID does not exist"));
        authorizeLobbyParticipant(lobbyId, token);
        return lobby.getParticipants();
    }

    public Lobby getSpecificLobby(Long id) {
        return lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "A lobby with this ID does not exist"));
    }

    public String joinLobby(Long id, String username, String password) {
        Lobby lobby = lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "A lobby with this ID does not exist"));
        checkIfUsernameInLobby(username, id);
        if (lobby.getJoinedParticipants() >= lobby.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The lobby is full");
        }
        if (!lobby.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        Participant participant = new Participant();
        participant.setUsername(username);
        participant.setToken(UUID.randomUUID().toString());

        lobby.addParticipant(participant);

        lobby = lobbyRepository.save(lobby);
        lobbyRepository.flush();

        return participant.getToken();
    }

    public String leaveLobby(Long id, String token) {
        Lobby lobby = lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "A lobby with this ID does not exist"));
        Participant participant = participantRepository.findByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A participant with this token does not exist");
        }
        if (!participant.getLobby().equals(lobby.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not in the lobby you are trying to leave");
        }
        if (participant.getId().equals(lobby.getAdminId())) {
            // TODO: handle case where admin leaves the lobby
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The admin cannot leave the lobby");
        }
        String username = participant.getUsername();
        participantRepository.delete(participant);
        lobby.setJoinedParticipants(lobby.getJoinedParticipants() - 1);

        return username;
    }

    public Lobby updateLobbySettings(Long id, LobbyPutDTO lobbyPutDTO, String token) {
        Lobby lobby = lobbyRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "A lobby with this ID does not exist"));
        authorizeLobbyAdmin(lobby.getAdminId(), token);
        if (lobbyPutDTO.getGameLocation() != null) {
            lobby.setGameLocation(lobbyPutDTO.getGameLocation());
        }
        if (lobbyPutDTO.getQuests() != null) {
            lobby.setQuests(lobbyPutDTO.getQuests());
        }
        if (lobbyPutDTO.getRoundDurationSeconds() != null) {
            lobby.setRoundDurationSeconds(lobbyPutDTO.getRoundDurationSeconds());
        }

        lobby = lobbyRepository.save(lobby);
        lobbyRepository.flush();

        return lobby;
    }

    public Long startGame(Long lobbyId, String token) {
        Lobby lobby = lobbyRepository.findById(lobbyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "A lobby with this ID does not exist"));

        authorizeLobbyAdmin(lobby.getAdminId(), token);

        if (lobby.getJoinedParticipants() < 3) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You need at least 3 participants to start the game");
        }

        List<String> quests = lobby.getQuests();
        if (quests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You need to have at least one quest to start the game");
        }

        Game createdGame = new Game();
        gameRepository.save(createdGame);

        List<Round> rounds = new ArrayList<>(quests.size());
        for (String quest : quests) {
            Round round = new Round();
            round.setQuest(quest);
            round.setRoundTime(lobby.getRoundDurationSeconds());
            round.setRemainingSeconds(lobby.getRoundDurationSeconds());
            round.setGame(createdGame.getId());
            rounds.add(round);
        }

        createdGame.setRoundDurationSeconds(lobby.getRoundDurationSeconds());
        createdGame.setAdminId(lobby.getAdminId());
        createdGame.setGameLocation(lobby.getGameLocation());
        createdGame.setRounds(rounds);
        createdGame.setNumberRounds(lobby.getQuests().size());

        List<Participant> participants = new ArrayList<>(lobby.getParticipants());
        for (Participant participant : participants) {
            participant.setGame(createdGame.getId());
            participant.setLobby(null);
        }
        createdGame.setParticipants(participants);
        createdGame.getRounds().get(0).setRoundStatus(RoundStatus.PLAYING);
        lobby.getParticipants().clear();
        lobby.recycleLobby();

        lobby = lobbyRepository.save(lobby);
        createdGame = gameRepository.save(createdGame);
        gameRepository.flush();

        gameService.startTimer(createdGame.getRounds().get(0), createdGame.getId());

        return createdGame.getId();
    }

    private void checkIfLobbyNameExists(String name) {
        Lobby lobby = lobbyRepository.findLobbyByName(name);
        if (lobby != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A lobby with this name already exists");
        }
    }

    public void checkIfUsernameInLobby(String username, Long lobbyId) {
        Participant participant = participantRepository.findByUsernameAndLobby(username, lobbyId);
        if (participant != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already in lobby");
        }
    }

    public void authorizeLobbyAdmin(Long adminId, String token) {
        Participant admin = participantRepository.findByToken(token);
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        } else if (!admin.getId().equals(adminId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        }
    }

    public void authorizeLobbyParticipant(Long lobbyId, String token) {
        Participant participant = participantRepository.findByToken(token);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad authorization token");
        } else if (!participant.getLobby().equals(lobbyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not in this lobby");
        }
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

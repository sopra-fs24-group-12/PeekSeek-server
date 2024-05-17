package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyRepository {
    private final Map<Long, Lobby> lobbyRepositoryById;
    private final List<String> lobbyNames;

    private static final LobbyRepository INSTANCE = new LobbyRepository();

    private LobbyRepository() {
        lobbyRepositoryById = new HashMap<>();
        lobbyNames = new ArrayList<>();
    }


    public static void addLobby(Lobby lobby) {
        INSTANCE.lobbyRepositoryById.put(lobby.getId(), lobby);
        INSTANCE.lobbyNames.add(lobby.getName());
    }

    public static Lobby getLobbyById(Long lobbyId) {
        return INSTANCE.lobbyRepositoryById.get(lobbyId);
    }

    public static void deleteLobby(Long lobbyId) {
        Lobby l = INSTANCE.lobbyRepositoryById.get(lobbyId);
        if (l == null)
            return;
        INSTANCE.lobbyRepositoryById.remove(lobbyId);
    }

    public static Boolean lobbyNameFree(String lobbyName) {
        return !INSTANCE.lobbyNames.contains(lobbyName);
    }

    public static List<Lobby> findAll() {
        return new ArrayList<>(INSTANCE.lobbyRepositoryById.values());
    }
}
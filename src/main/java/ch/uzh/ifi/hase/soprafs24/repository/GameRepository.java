package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Game;

import java.util.HashMap;
import java.util.Map;

public class GameRepository {
    private final Map<Long, Game> gameRepositoryById;

    private static final GameRepository INSTANCE = new GameRepository();

    private GameRepository() {
        gameRepositoryById = new HashMap<>();
    }

    public static void addGame(Game game) {
        INSTANCE.gameRepositoryById.put(game.getId(), game);
    }

    public static Game getGameById(Long gameId) {
        return INSTANCE.gameRepositoryById.get(gameId);
    }

    public static void deleteGame(Long gameId) {
        INSTANCE.gameRepositoryById.remove(gameId);
    }
}
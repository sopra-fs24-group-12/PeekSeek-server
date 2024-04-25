package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {
    
    private Game game;

    @BeforeEach
    public void setUp() {
        // Initialize a game object before each test
        long gameId = 1L;
        game = new Game();
        game.setId(gameId);
    }

    @Test
    public void testAddGameAndGetGameById() {
        // Add the game to the repository
        GameRepository.addGame(game);
        
        // Now attempt to retrieve it by ID
        Game found = GameRepository.getGameById(game.getId());
        
        // Validate that the game was added and retrieved correctly
        assertNotNull(found, "The game should be found in the repository.");
        assertEquals(game.getId(), found.getId(), "The game ID should match the one that was retrieved.");
    }


    @Test
    public void testGetGameByIdNonExistent() {
        // Try to get a game that does not exist in the repository
        long nonExistentGameId = 2L; // Assuming 1L is used in setUp method
        Game found = GameRepository.getGameById(nonExistentGameId);
        
        // Validate that no game is found given the non-existent ID
        assertNull(found, "No game should be found for a non-existent ID.");
    }

    @Test
    public void testDeleteGame() {
        // First, add a game to ensure it is in the repository
        GameRepository.addGame(game);

        // Now delete the game from the repository
        GameRepository.deleteGame(game.getId());

        // Attempt to retrieve the game after deletion
        Game foundAfterDeletion = GameRepository.getGameById(game.getId());

        // Validate that the game was deleted from the repository
        assertNull(foundAfterDeletion, "The game should have been deleted from the repository.");
    }
}

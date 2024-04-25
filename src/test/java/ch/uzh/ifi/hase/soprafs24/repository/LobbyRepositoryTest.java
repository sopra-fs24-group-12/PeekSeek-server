package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbyRepositoryTest {
    
    private Lobby lobby;

    @BeforeEach
    void setUp() {
        // Initialize a Lobby object before each test
        lobby = new Lobby();
        lobby.setId(1L);
        lobby.setName("TestLobby");
    }

    @Test
    void testAddLobbyAndGetLobbyById() {
        // Add the lobby to the repository
        LobbyRepository.addLobby(lobby);

        // Now attempt to retrieve it by ID
        Lobby found = LobbyRepository.getLobbyById(lobby.getId());

        // Validate that the lobby was added and retrieved correctly
        assertNotNull(found, "The lobby should be found in the repository.");
        assertEquals(lobby.getId(), found.getId(), "The lobby IDs should match.");
        assertEquals(lobby.getName(), found.getName(), "The lobby names should match.");
    }
    
    @Test
    void testGetLobbyByIdNonExistent() {
        // Try to get a lobby that does not exist in the repository
        long nonExistentLobbyId = 99L; // Arbitrary number unlikely to clash with setup
        Lobby found = LobbyRepository.getLobbyById(nonExistentLobbyId);

        // Validate that no lobby is found given the non-existent ID
        assertNull(found, "No lobby should be found for a non-existent ID.");
    }

    @Test
    void testDeleteLobby() {
        // First, add a lobby to ensure it exists in our repository
        LobbyRepository.addLobby(lobby);

        // Now delete the lobby from the repository
        LobbyRepository.deleteLobby(lobby.getId());

        // Attempt to retrieve the lobby after deletion
        Lobby foundAfterDeletion = LobbyRepository.getLobbyById(lobby.getId());

        // Validate that the lobby was deleted
        assertNull(foundAfterDeletion, "The lobby should have been deleted from the repository.");
    }

    @Test
    void testLobbyNameFree() {
        // The name should initially be free
        assertTrue(LobbyRepository.lobbyNameFree("NewLobby"), "The lobby name should initially be free.");

        // After adding a lobby, its name should not be free
        LobbyRepository.addLobby(lobby);
        assertFalse(LobbyRepository.lobbyNameFree(lobby.getName()), "The lobby name should not be free after adding a lobby with that name.");
    }

    @Test
    void testFindAll() {
        // Initially, the repository should be empty
        List<Lobby> lobbies = LobbyRepository.findAll();
        assertTrue(lobbies.isEmpty(), "Initially, the repository should be empty.");

        // Add a lobby and check if it is present in the findAll result
        LobbyRepository.addLobby(lobby);
        lobbies = LobbyRepository.findAll();

        // Validate that findAll includes the newly added lobby
        assertFalse(lobbies.isEmpty(), "The repository should not be empty after adding a lobby.");
        assertTrue(lobbies.contains(lobby), "The repository should contain the added lobby.");
    }
}

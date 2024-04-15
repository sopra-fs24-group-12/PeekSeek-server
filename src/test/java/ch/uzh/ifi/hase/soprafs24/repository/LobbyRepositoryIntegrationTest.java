package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class LobbyRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    private LobbyRepository lobbyRepository;

    @Test
    public void getLobbyById_success() {

        Lobby lobby = new Lobby();
        lobby.setId(3L);
        lobby.setRoundDurationSeconds(30);
        lobby.setAdminId(4L);
        lobby.setGameLocation("Zürich");
        lobby.setJoinedParticipants(3);


        entityManager.persist(lobby);
        entityManager.flush();


        Lobby found = LobbyRepository.getLobbyById(lobby.getId());


        assertNotNull(found.getId());
        assertEquals(found.getId(), lobby.getId());
        assertEquals(found.getRoundDurationSeconds(), lobby.getRoundDurationSeconds());
        assertEquals(found.getGameLocation(), lobby.getGameLocation());
        assertEquals(found.getJoinedParticipants(), lobby.getJoinedParticipants());
        assertEquals(found.getAdminId(), lobby.getAdminId());

    }
}

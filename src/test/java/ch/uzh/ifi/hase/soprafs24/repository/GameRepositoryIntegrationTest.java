package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class GameRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    private GameRepository gameRepository;

    @Test
    public void getGameByID_success() {

        Game game = new Game();
        game.setId(3L);
        game.setRoundDurationSeconds(30);
        game.setGameStatus(GameStatus.RUNNING);
        game.setGameLocation("Zürich");

        entityManager.persist(game);
        entityManager.flush();

        Game found = GameRepository.getGameById(game.getId());

    
        assertNotNull(found.getId());
        assertEquals(found.getId(), game.getId());
        assertEquals(found.getRoundDurationSeconds(), game.getRoundDurationSeconds());
        assertEquals(found.getGameLocation(), game.getGameLocation());
        assertEquals(found.getGameStatus(), game.getGameStatus());
    }

}

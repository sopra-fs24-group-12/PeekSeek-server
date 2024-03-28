package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Lobby;
import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {

}

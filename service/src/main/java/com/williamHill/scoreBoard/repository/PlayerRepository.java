package com.williamHill.scoreBoard.repository;

import com.williamHill.scoreBoard.repository.entity.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

  Optional<Player> findByName(String name);

  Optional<Player> findByPlayerKey(String playerKey);
}

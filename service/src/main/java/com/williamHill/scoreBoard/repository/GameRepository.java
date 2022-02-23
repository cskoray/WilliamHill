package com.williamHill.scoreBoard.repository;

import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

  Optional<Game> findByHomeTeamAndAwayTeamAndGameDate(Team homeTeam, Team awayTeam,
      Timestamp gameDate);

  Optional<Game> findByGameKey(String gameKey);
}

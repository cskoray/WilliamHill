package com.williamHill.scoreBoard.repository;

import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Score;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {

  Optional<List<Score>> findByGame(Game game);
}

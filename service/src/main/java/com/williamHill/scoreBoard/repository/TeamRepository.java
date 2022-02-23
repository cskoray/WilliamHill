package com.williamHill.scoreBoard.repository;

import com.williamHill.scoreBoard.repository.entity.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

  Optional<Team> findByName(String name);

  Optional<Team> findByTeamKey(String teamKey);

  Optional<List<Team>> findByTeamKeyIn(List<String> teamKeys);
}

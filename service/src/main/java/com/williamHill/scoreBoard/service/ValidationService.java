package com.williamHill.scoreBoard.service;

import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_TEAM_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_TEAM_ERROR;
import static java.util.Arrays.asList;

import com.williamHill.scoreBoard.exception.ScoreBoardException;
import com.williamHill.scoreBoard.repository.GameRepository;
import com.williamHill.scoreBoard.repository.PlayerRepository;
import com.williamHill.scoreBoard.repository.TeamRepository;
import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Player;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidationService {

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private GameRepository gameRepository;

  public void validateTeamByName(String name) {

    Optional<Team> teamOpt = teamRepository.findByName(name);
    teamOpt.ifPresent((team) -> {
      throw new ScoreBoardException(DUPLICATE_TEAM_ERROR);
    });
  }

  public Team validateTeamByKey(String teamKey) {

    return teamRepository.findByTeamKey(teamKey).orElseThrow(() -> {
      throw new ScoreBoardException(INVALID_TEAM_ERROR);
    });
  }

  public void validatePlayerByName(String playerName) {

    Optional<Player> playerOpt = playerRepository.findByName(playerName);
    playerOpt.ifPresent((player) -> {
      throw new ScoreBoardException(DUPLICATE_PLAYER_ERROR);
    });
  }

  public void validateTeamByKeyIn(String homeTeamKey, String awayTeamKey) {

    Optional<List<Team>> teamsOpt = teamRepository
        .findByTeamKeyIn(asList(homeTeamKey, awayTeamKey));
    teamsOpt.ifPresent((teams) -> {
      if (teams.size() < 2) {
        throw new ScoreBoardException(INVALID_TEAM_ERROR);
      }
    });
  }

  public void validateGame(Team homeTeam, Team awayTeam, Timestamp gameDate) {

    Optional<Game> gameOpt = gameRepository
        .findByHomeTeamAndAwayTeamAndGameDate(homeTeam, awayTeam, gameDate);
    gameOpt.ifPresent((team) -> {
      throw new ScoreBoardException(DUPLICATE_GAME_ERROR);
    });
  }

  public Player validatePlayerByKey(String playerKey) {

    return playerRepository.findByPlayerKey(playerKey).orElseThrow(() -> {
      throw new ScoreBoardException(INVALID_PLAYER_ERROR);
    });
  }

  public Game validateGameByKey(String gameKey) {

    return gameRepository.findByGameKey(gameKey).orElseThrow(() -> {
      throw new ScoreBoardException(INVALID_GAME_ERROR);
    });
  }

  public Team validateGameTeam(Game game, String teamKey) {

    if (teamKey.equalsIgnoreCase(game.getHomeTeam().getTeamKey())) {
      game.setHomeScore(game.getHomeScore() + 1);
    } else if (teamKey.equalsIgnoreCase(game.getAwayTeam().getTeamKey())) {
      game.setAwayScore(game.getAwayScore() + 1);
    } else {
      throw new ScoreBoardException(INVALID_TEAM_ERROR);
    }
    return validateTeamByKey(teamKey);
  }
}

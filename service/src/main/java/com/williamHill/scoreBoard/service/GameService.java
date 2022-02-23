package com.williamHill.scoreBoard.service;

import static java.util.UUID.randomUUID;

import com.williamHill.scoreBoard.dto.request.AddGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddPlayerRequestDto;
import com.williamHill.scoreBoard.dto.request.AddScoreToGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddTeamRequestDto;
import com.williamHill.scoreBoard.dto.request.Message;
import com.williamHill.scoreBoard.dto.request.ScoreMap;
import com.williamHill.scoreBoard.repository.GameRepository;
import com.williamHill.scoreBoard.repository.PlayerRepository;
import com.williamHill.scoreBoard.repository.ScoreRepository;
import com.williamHill.scoreBoard.repository.TeamRepository;
import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Player;
import com.williamHill.scoreBoard.repository.entity.Score;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameService {

  @Autowired
  private ValidationService validationService;

  @Autowired
  private ScorePublishService scorePublishService;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private ScoreRepository scoreRepository;

  public void addTeam(AddTeamRequestDto addTeamRequestDto) {

    String teamName = addTeamRequestDto.getName();
    validationService.validateTeamByName(teamName);
    Team team = Team.builder()
        .teamKey(randomUUID().toString())
        .name(teamName)
        .build();
    teamRepository.save(team);
  }

  public void addPlayer(AddPlayerRequestDto addPlayerRequestDto) {

    String playerName = addPlayerRequestDto.getName();
    String teamKey = addPlayerRequestDto.getTeamKey();
    validationService.validatePlayerByName(playerName);
    Team team = validationService.validateTeamByKey(teamKey);
    Player player = Player.builder()
        .playerKey(randomUUID().toString())
        .name(playerName)
        .team(team)
        .build();
    playerRepository.save(player);
  }

  public void addGame(AddGameRequestDto addGameRequestDto) throws ParseException {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String homeTeamKey = addGameRequestDto.getHomeTeamKey();
    String awayTeamKey = addGameRequestDto.getAwayTeamKey();
    String gameDate = addGameRequestDto.getGameDate();
    Timestamp gameTs = new Timestamp(formatter.parse(gameDate).getTime());
    validationService.validateTeamByKeyIn(homeTeamKey, awayTeamKey);
    Team homeTeam = validationService.validateTeamByKey(homeTeamKey);
    Team awayTeam = validationService.validateTeamByKey(awayTeamKey);
    validationService.validateGame(homeTeam, awayTeam, gameTs);
    Game game = Game.builder()
        .gameKey(randomUUID().toString())
        .homeTeam(homeTeam)
        .awayTeam(awayTeam)
        .homeScore(0)
        .awayScore(0)
        .gameDate(gameTs)
        .build();
    gameRepository.save(game);
  }

  public void addScoreToGame(AddScoreToGameRequestDto addScoreToGameRequestDto) {

    String gameKey = addScoreToGameRequestDto.getGameKey();
    String teamKey = addScoreToGameRequestDto.getTeamKey();
    String playerKey = addScoreToGameRequestDto.getPlayerKey();
    int scoreMinute = addScoreToGameRequestDto.getScoreMinute();
    Game game = validationService.validateGameByKey(gameKey);
    Team team = validationService.validateGameTeam(game, teamKey);
    Player player = validationService.validatePlayerByKey(playerKey);

    Score score = Score.builder()
        .scoreKey(randomUUID().toString())
        .game(game)
        .player(player)
        .team(team)
        .scoreMinute(scoreMinute)
        .build();
    scoreRepository.save(score);
    gameRepository.save(game);

    Optional<List<Score>> gameScores = scoreRepository.findByGame(game);

    List<ScoreMap> homeScoreMap = new ArrayList<>();
    List<ScoreMap> awayScoreMap = new ArrayList<>();
    gameScores.ifPresent(scores -> scores.forEach(gameScore -> {
      Player scorer = gameScore.getPlayer();
      Team scorerTeam = scorer.getTeam();
      String scorerName = scorer.getName();
      int scorerMinute = gameScore.getScoreMinute();
      if (scorerTeam.getTeamKey().equalsIgnoreCase(game.getHomeTeam().getTeamKey())) {
        ScoreMap scoreMap = ScoreMap.builder()
            .playerName(scorerName)
            .scoreMinute(scorerMinute)
            .build();
        homeScoreMap.add(scoreMap);
      } else {
        ScoreMap scoreMap = ScoreMap.builder()
            .playerName(scorerName)
            .scoreMinute(scorerMinute)
            .build();
        awayScoreMap.add(scoreMap);
      }
    }));

    StringBuilder content = new StringBuilder(
        "Event: " + game.getHomeTeam().getName() + " vs " + game.getAwayTeam().getName())
        .append("<br>").append("Score: ").append(game.getHomeScore()).append(" - ")
        .append(game.getAwayScore())
        .append("<br>");
    for (ScoreMap sm : homeScoreMap) {
      content.append(" ").append(sm.getPlayerName()).append(" ").append(sm.getScoreMinute()).append("'").append("<br>");
    }
    for (ScoreMap sm : awayScoreMap) {
      content.append(" ").append(sm.getPlayerName()).append(" ").append(sm.getScoreMinute()).append("'").append("<br>");
    }
    content.append("<br>");
    Message message = Message.builder()
        .messageContent(content.toString())
        .build();
    scorePublishService.notifyFrontend(message.getMessageContent());
  }
}

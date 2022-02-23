package com.williamHill.scoreBoard.util;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDate.of;
import static java.time.Month.JANUARY;
import static java.util.UUID.randomUUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamHill.scoreBoard.dto.request.AddGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddPlayerRequestDto;
import com.williamHill.scoreBoard.dto.request.AddScoreToGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddTeamRequestDto;
import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Player;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.sql.Timestamp;

public class ObjectFactoryTest {

  public static final String ERROR_CODE = "$.errors[0].code";
  public static final String ERROR_DESCRIPTION = "$.errors[0].description";
  public static final String GAME = "/api/v1/game";
  public static final String ADD_TEAM = "/team";
  public static final String ADD_PLAYER = "/player";
  public static final String ADD_GAME = "/match";
  public static final String ADD_SCORE_TO_GAME = "/score";
  public final static String HOME_TEAM_NAME = "Arsenal";
  public final static String AWAY_TEAM_NAME = "Man United";
  public final static String PLAYER_NAME = "Diego";
  public final static String PLAYER_KEY = randomUUID().toString();
  public final static String HOME_TEAM_KEY = randomUUID().toString();
  public final static String AWAY_TEAM_KEY = randomUUID().toString();
  public final static String GAME_KEY = randomUUID().toString();
  public final static String GAME_DATE = "2022-01-25 21:00";
  public final static Timestamp GAME_DATE_TS = valueOf(of(2022, JANUARY, 25).atTime(21, 0));
  public final static int SCORE_MINUTE = 10;


  private static final ObjectMapper mapper = new ObjectMapper();

  public static AddTeamRequestDto BuildAddTeamRequestDto() {

    return AddTeamRequestDto.builder()
        .name(HOME_TEAM_NAME)
        .build();
  }

  public static AddPlayerRequestDto BuildAddPlayerRequestDto() {

    return AddPlayerRequestDto.builder()
        .name(PLAYER_NAME)
        .teamKey(HOME_TEAM_KEY)
        .build();
  }

  public static AddGameRequestDto BuildAddGameRequestDto() {

    return AddGameRequestDto.builder()
        .homeTeamKey(HOME_TEAM_KEY)
        .awayTeamKey(AWAY_TEAM_KEY)
        .gameDate(GAME_DATE)
        .build();
  }

  public static AddScoreToGameRequestDto BuildAddScoreToGameRequestDto() {

    return AddScoreToGameRequestDto.builder()
        .gameKey(GAME_KEY)
        .teamKey(HOME_TEAM_KEY)
        .playerKey(PLAYER_KEY)
        .scoreMinute(SCORE_MINUTE)
        .build();
  }

  public static Team BuildHomeTeam() {

    return Team.builder()
        .teamKey(HOME_TEAM_KEY)
        .name(HOME_TEAM_NAME)
        .build();
  }

  public static Team BuildAwayTeam() {

    return Team.builder()
        .teamKey(AWAY_TEAM_KEY)
        .name(AWAY_TEAM_NAME)
        .build();
  }

  public static Game BuildGame() {

    return Game.builder()
        .gameKey(GAME_KEY)
        .awayScore(0)
        .homeScore(0)
        .homeTeam(BuildHomeTeam())
        .awayTeam(BuildAwayTeam())
        .gameDate(GAME_DATE_TS)
        .build();
  }

  public static Player BuildPlayer() {

    return Player.builder()
        .playerKey(PLAYER_KEY)
        .name(PLAYER_NAME)
        .build();
  }

  public static String ObjectToJson(Object jsonObject) {
    String json;
    if (jsonObject == null) {
      json = "Null Object";
    } else {
      try {
        json = mapper.writeValueAsString(jsonObject);
      } catch (Exception e) {
        json = "Object could not be converted to Json Format";
      }
    }
    return json;
  }
}

package com.williamHill.scoreBoard.service;

import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_TEAM_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_GAME_ERROR;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.AWAY_TEAM_KEY;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.AWAY_TEAM_NAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddGameRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddPlayerRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddScoreToGameRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddTeamRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAwayTeam;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildGame;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildHomeTeam;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildPlayer;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.GAME_KEY;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.HOME_TEAM_KEY;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.HOME_TEAM_NAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.PLAYER_KEY;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.PLAYER_NAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.SCORE_MINUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import com.williamHill.scoreBoard.dto.request.AddGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddPlayerRequestDto;
import com.williamHill.scoreBoard.dto.request.AddScoreToGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddTeamRequestDto;
import com.williamHill.scoreBoard.exception.ScoreBoardException;
import com.williamHill.scoreBoard.repository.GameRepository;
import com.williamHill.scoreBoard.repository.PlayerRepository;
import com.williamHill.scoreBoard.repository.ScoreRepository;
import com.williamHill.scoreBoard.repository.TeamRepository;
import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Player;
import com.williamHill.scoreBoard.repository.entity.Score;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
@ComponentScan({"com.williamHill.*"})
class GameServiceTest {

  @Mock
  private ValidationService validationService;

  @Mock
  private ScorePublishService scorePublishService;

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private PlayerRepository playerRepository;

  @Mock
  private GameRepository gameRepository;

  @Mock
  private ScoreRepository scoreRepository;

  @InjectMocks
  private GameService unit;

  @Captor
  ArgumentCaptor<Team> teamCaptor;

  @Captor
  ArgumentCaptor<Player> playerCaptor;

  @Captor
  ArgumentCaptor<Game> gameCaptor;

  @Captor
  ArgumentCaptor<Score> scoreCaptor;

  @Test
  public void shouldAddNewTeam() {

    AddTeamRequestDto addTeamRequestDto = BuildAddTeamRequestDto();
    unit.addTeam(addTeamRequestDto);
    verify(teamRepository).save(teamCaptor.capture());
    verify(teamRepository, times(1)).save(any());
    Team team = teamCaptor.getValue();
    assertEquals(HOME_TEAM_NAME, team.getName());
  }

  @Test
  public void shouldThrowException_AddNewTeam() {

    doThrow(new ScoreBoardException(DUPLICATE_TEAM_ERROR)).when(validationService)
        .validateTeamByName(HOME_TEAM_NAME);
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.addTeam(BuildAddTeamRequestDto()));
    assertTrue(thrown.getMessage().contains(DUPLICATE_TEAM_ERROR.getMessage()));
  }

  @Test
  public void shouldAddNewPlayer() {

    Team team = BuildHomeTeam();
    AddPlayerRequestDto addPlayerRequestDto = BuildAddPlayerRequestDto();
    when(validationService.validateTeamByKey(anyString())).thenReturn(team);
    unit.addPlayer(addPlayerRequestDto);
    verify(playerRepository).save(playerCaptor.capture());
    verify(playerRepository, times(1)).save(any());
    Player player = playerCaptor.getValue();
    assertEquals(PLAYER_NAME, player.getName());
    assertEquals(HOME_TEAM_KEY, player.getTeam().getTeamKey());
    assertEquals(HOME_TEAM_NAME, player.getTeam().getName());
  }

  @Test
  public void shouldThrowException_AddNewPlayer() {

    doThrow(new ScoreBoardException(DUPLICATE_PLAYER_ERROR)).when(validationService)
        .validatePlayerByName(PLAYER_NAME);
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.addPlayer(BuildAddPlayerRequestDto()));
    assertTrue(thrown.getMessage().contains(DUPLICATE_PLAYER_ERROR.getMessage()));
  }

  @Test
  public void shouldAddNewGame() throws ParseException {

    Team homeTeam = BuildHomeTeam();
    Team awayTeam = BuildAwayTeam();
    when(validationService.validateTeamByKey(HOME_TEAM_KEY)).thenReturn(homeTeam);
    when(validationService.validateTeamByKey(AWAY_TEAM_KEY)).thenReturn(awayTeam);
    AddGameRequestDto addGameRequestDto = BuildAddGameRequestDto();
    unit.addGame(addGameRequestDto);
    verify(gameRepository).save(gameCaptor.capture());
    verify(gameRepository, times(1)).save(any());
    Game game = gameCaptor.getValue();
    assertEquals(HOME_TEAM_NAME, game.getHomeTeam().getName());
    assertEquals(AWAY_TEAM_NAME, game.getAwayTeam().getName());
    assertEquals(0, game.getHomeScore());
    assertEquals(0, game.getAwayScore());
  }

  @Test
  public void shouldThrowException_AddNewGame() {

    doThrow(new ScoreBoardException(DUPLICATE_GAME_ERROR)).when(validationService)
        .validateTeamByKey(HOME_TEAM_KEY);
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.addGame(BuildAddGameRequestDto()));
    assertTrue(thrown.getMessage().contains(DUPLICATE_GAME_ERROR.getMessage()));
  }

  @Test
  public void shouldAddNewScoreToGame() {

    Team homeTeam = BuildHomeTeam();
    Game game = BuildGame();
    Player player = BuildPlayer();
    when(validationService.validateGameByKey(GAME_KEY)).thenReturn(game);
    when(validationService.validateGameTeam(game, HOME_TEAM_KEY)).thenReturn(homeTeam);
    when(validationService.validatePlayerByKey(PLAYER_KEY)).thenReturn(player);
    AddScoreToGameRequestDto addScoreToGameRequestDto = BuildAddScoreToGameRequestDto();
    unit.addScoreToGame(addScoreToGameRequestDto);
    verify(scoreRepository).save(scoreCaptor.capture());
    verify(scoreRepository, times(1)).save(any());
    Score score = scoreCaptor.getValue();
    assertEquals(HOME_TEAM_KEY, score.getTeam().getTeamKey());
    assertEquals(PLAYER_KEY, score.getPlayer().getPlayerKey());
    assertEquals(SCORE_MINUTE, score.getScoreMinute());
  }

  @Test
  public void shouldThrowException_AddNewScoreToGame() {

    doThrow(new ScoreBoardException(INVALID_GAME_ERROR)).when(validationService)
        .validateGameByKey(GAME_KEY);
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.addScoreToGame(BuildAddScoreToGameRequestDto()));
    assertTrue(thrown.getMessage().contains(INVALID_GAME_ERROR.getMessage()));
  }
}
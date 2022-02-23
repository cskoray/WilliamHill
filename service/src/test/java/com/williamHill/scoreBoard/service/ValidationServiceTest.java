package com.williamHill.scoreBoard.service;

import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_TEAM_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_TEAM_ERROR;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.AWAY_TEAM_KEY;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.HOME_TEAM_KEY;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import com.williamHill.scoreBoard.exception.ScoreBoardException;
import com.williamHill.scoreBoard.repository.GameRepository;
import com.williamHill.scoreBoard.repository.PlayerRepository;
import com.williamHill.scoreBoard.repository.TeamRepository;
import com.williamHill.scoreBoard.repository.entity.Game;
import com.williamHill.scoreBoard.repository.entity.Player;
import com.williamHill.scoreBoard.repository.entity.Team;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
@ComponentScan({"com.williamHill.*"})
class ValidationServiceTest {

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private PlayerRepository playerRepository;

  @Mock
  private GameRepository gameRepository;

  @InjectMocks
  private ValidationService unit;

  @Test
  public void shouldThrowException_validateTeamByName() {

    when(teamRepository.findByName(any())).thenReturn(of(Team.builder().build()));
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validateTeamByName(anyString()));
    assertTrue(thrown.getMessage().contains(DUPLICATE_TEAM_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validateTeamByKey() {

    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validateTeamByKey(anyString()));
    assertTrue(thrown.getMessage().contains(INVALID_TEAM_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validatePlayerByName() {

    when(playerRepository.findByName(any())).thenReturn(of(Player.builder().build()));
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validatePlayerByName(anyString()));
    assertTrue(thrown.getMessage().contains(DUPLICATE_PLAYER_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validateTeamByKeyIn() {

    when(teamRepository.findByTeamKeyIn(asList(HOME_TEAM_KEY, AWAY_TEAM_KEY)))
        .thenReturn(of(asList(Team.builder().build())));
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validateTeamByKeyIn(HOME_TEAM_KEY, AWAY_TEAM_KEY));
    assertTrue(thrown.getMessage().contains(INVALID_TEAM_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validateGame() {

    when(gameRepository.findByHomeTeamAndAwayTeamAndGameDate(any(), any(), any()))
        .thenReturn(of(Game.builder().build()));
    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validateGame(Team.builder().build(), Team.builder().build(),
            Timestamp.valueOf(now())));
    assertTrue(thrown.getMessage().contains(DUPLICATE_GAME_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validatePlayerByKey() {

    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validatePlayerByKey(anyString()));
    assertTrue(thrown.getMessage().contains(INVALID_PLAYER_ERROR.getMessage()));
  }

  @Test
  public void shouldThrowException_validateGameByKey() {

    ScoreBoardException thrown = assertThrows(ScoreBoardException.class,
        () -> unit.validateGameByKey(anyString()));
    assertTrue(thrown.getMessage().contains(INVALID_GAME_ERROR.getMessage()));
  }
}
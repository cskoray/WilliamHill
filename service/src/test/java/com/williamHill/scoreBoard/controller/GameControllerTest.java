package com.williamHill.scoreBoard.controller;

import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_GAME_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_PLAYER_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.DUPLICATE_TEAM_ERROR;
import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_GAME_ERROR;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ADD_GAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ADD_PLAYER;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ADD_SCORE_TO_GAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ADD_TEAM;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddGameRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddPlayerRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddScoreToGameRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.BuildAddTeamRequestDto;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ERROR_CODE;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ERROR_DESCRIPTION;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.GAME;
import static com.williamHill.scoreBoard.util.ObjectFactoryTest.ObjectToJson;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.williamHill.scoreBoard.exception.GlobalExceptionHandler;
import com.williamHill.scoreBoard.exception.ScoreBoardException;
import com.williamHill.scoreBoard.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@ComponentScan({"com.williamHill.*"})
class GameControllerTest {

  @Mock
  private GameService gameService;

  @InjectMocks
  private GameController unit;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(unit)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  public void shouldAddTeam_AddNewTeam() throws Exception {

    doNothing()
        .when(gameService).addTeam(BuildAddTeamRequestDto());
    this.mockMvc
        .perform(
            post(GAME + ADD_TEAM)
                .content(ObjectToJson(BuildAddTeamRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionDuplicateTeam_AddNewTeam() throws Exception {

    doThrow(new ScoreBoardException(DUPLICATE_TEAM_ERROR))
        .when(gameService)
        .addTeam(BuildAddTeamRequestDto());

    this.mockMvc
        .perform(
            post(GAME + ADD_TEAM)
                .content(ObjectToJson(BuildAddTeamRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(DUPLICATE_TEAM_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(DUPLICATE_TEAM_ERROR.getMessage()));
  }

  @Test
  public void shouldAddPlayer_AddNewPlayer() throws Exception {

    doNothing()
        .when(gameService).addPlayer(BuildAddPlayerRequestDto());
    this.mockMvc
        .perform(
            post(GAME + ADD_PLAYER)
                .content(ObjectToJson(BuildAddPlayerRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionDuplicatePlayer_AddNewPlayer() throws Exception {

    doThrow(new ScoreBoardException(DUPLICATE_PLAYER_ERROR))
        .when(gameService)
        .addPlayer(BuildAddPlayerRequestDto());

    this.mockMvc
        .perform(
            post(GAME + ADD_PLAYER)
                .content(ObjectToJson(BuildAddPlayerRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(DUPLICATE_PLAYER_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(DUPLICATE_PLAYER_ERROR.getMessage()));
  }

  @Test
  public void shouldAddGame_AddNewGame() throws Exception {

    this.mockMvc
        .perform(
            post(GAME + ADD_GAME)
                .content(ObjectToJson(BuildAddGameRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionDuplicateGame_AddNewGame() throws Exception {

    doThrow(new ScoreBoardException(DUPLICATE_GAME_ERROR))
        .when(gameService)
        .addGame(BuildAddGameRequestDto());

    this.mockMvc
        .perform(
            post(GAME + ADD_GAME)
                .content(ObjectToJson(BuildAddGameRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(DUPLICATE_GAME_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(DUPLICATE_GAME_ERROR.getMessage()));
  }

  @Test
  public void shouldAddScoreToGame_AddNewScoreToGame() throws Exception {

    this.mockMvc
        .perform(
            patch(GAME + ADD_SCORE_TO_GAME)
                .content(ObjectToJson(BuildAddScoreToGameRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionInvalidGame_AddNewGame() throws Exception {

    doThrow(new ScoreBoardException(INVALID_GAME_ERROR))
        .when(gameService)
        .addGame(BuildAddGameRequestDto());

    this.mockMvc
        .perform(
            post(GAME + ADD_GAME)
                .content(ObjectToJson(BuildAddGameRequestDto()))
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(ERROR_CODE).value(INVALID_GAME_ERROR.getCode()))
        .andExpect(jsonPath(ERROR_DESCRIPTION).value(INVALID_GAME_ERROR.getMessage()));
  }
}
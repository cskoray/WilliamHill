package com.williamHill.scoreBoard.controller;

import static com.williamHill.scoreBoard.constants.RestApiUrls.ADD_GAME;
import static com.williamHill.scoreBoard.constants.RestApiUrls.ADD_PLAYER;
import static com.williamHill.scoreBoard.constants.RestApiUrls.ADD_SCORE_TO_GAME;
import static com.williamHill.scoreBoard.constants.RestApiUrls.ADD_TEAM;
import static com.williamHill.scoreBoard.constants.RestApiUrls.GAME;
import static org.springframework.http.ResponseEntity.ok;

import com.williamHill.scoreBoard.dto.request.AddGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddPlayerRequestDto;
import com.williamHill.scoreBoard.dto.request.AddScoreToGameRequestDto;
import com.williamHill.scoreBoard.dto.request.AddTeamRequestDto;
import com.williamHill.scoreBoard.service.GameService;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(GAME)
@CrossOrigin
public class GameController {

  @Autowired
  private GameService gameService;

  @ApiOperation(value = "add new team", nickname = "addTeam", response = ResponseEntity.class)
  @PostMapping(ADD_TEAM)
  public ResponseEntity<String> addTeam(
      @Valid @RequestBody AddTeamRequestDto addTeamRequestDto) {

    gameService.addTeam(addTeamRequestDto);
    return ok().build();
  }

  @ApiOperation(value = "add new player", nickname = "addPlayer", response = ResponseEntity.class)
  @PostMapping(ADD_PLAYER)
  public ResponseEntity<String> addPlayer(
      @Valid @RequestBody AddPlayerRequestDto addPlayerRequestDto) {

    gameService.addPlayer(addPlayerRequestDto);
    return ok().build();
  }

  @ApiOperation(value = "add new game", nickname = "addGame", response = ResponseEntity.class)
  @PostMapping(ADD_GAME)
  public ResponseEntity<String> addGame(
      @Valid @RequestBody AddGameRequestDto addGameRequestDto) throws ParseException {

    gameService.addGame(addGameRequestDto);
    return ok().build();
  }

  @ApiOperation(value = "add score to game", nickname = "addScoreToGame", response = ResponseEntity.class)
  @PatchMapping(ADD_SCORE_TO_GAME)
  @MessageMapping("/scoreBoard")
  @SendTo("/topic/scores")
  public ResponseEntity<String> addScoreToGame(
      @Valid @RequestBody AddScoreToGameRequestDto addScoreToGameRequestDto) throws ParseException {

    gameService.addScoreToGame(addScoreToGameRequestDto);
    return ok().build();
  }
}

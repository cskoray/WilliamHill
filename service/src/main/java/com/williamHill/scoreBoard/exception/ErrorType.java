package com.williamHill.scoreBoard.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

  DUPLICATE_TEAM_ERROR("10000", "Team already exists", BAD_REQUEST),
  INVALID_PARAMETER_ERROR("10001", "Invalid field(s). ", BAD_REQUEST),
  INVALID_TEAM_ERROR("10002", "No such team ", BAD_REQUEST),
  DUPLICATE_PLAYER_ERROR("10003", "Player already exists", BAD_REQUEST),
  DUPLICATE_GAME_ERROR("10004", "Game already exists", BAD_REQUEST),
  INVALID_PLAYER_ERROR("10005", "No such player ", BAD_REQUEST),
  INVALID_GAME_ERROR("10006", "No such game ", BAD_REQUEST);

  private String code;
  private String message;
  private HttpStatus httpStatus;
}

package com.williamHill.scoreBoard.exception;

import lombok.Getter;

@Getter
public class ScoreBoardException extends RuntimeException {

  private ErrorType errorType;
  private String field;
  private String code;

  public ScoreBoardException(ErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public ScoreBoardException(ErrorType errorType, String field) {
    super(errorType.getMessage());
    this.code = errorType.getCode();
    this.errorType = errorType;
    this.field = field;
  }

}

package com.williamHill.scoreBoard.exception;

import static com.williamHill.scoreBoard.exception.ErrorType.INVALID_PARAMETER_ERROR;
import static java.util.Collections.singletonList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(ScoreBoardException.class)
  @ResponseBody
  public ResponseEntity<ErrorList> handleException(ScoreBoardException exception) {

    Error error = createError(exception);
    return new ResponseEntity<>(createErrorList(singletonList(error)),
        exception.getErrorType().getHttpStatus());
  }

  private Error createError(ScoreBoardException exception) {
    return createError(exception.getErrorType(), exception.getField());
  }

  private Error createError(ErrorType errorType, String field) {

    return Error.builder()
        .code(errorType.getCode())
        .description(errorType.getMessage())
        .field(field)
        .build();
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorList> handleValidationException(ValidationException exception) {
    return createSingleErrorListResponse(INVALID_PARAMETER_ERROR, exception, "");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<ErrorList> handleMessagingException(
      IllegalArgumentException exception) {
    return createSingleErrorListResponse(INVALID_PARAMETER_ERROR, exception, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorList> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    ErrorType errorType = INVALID_PARAMETER_ERROR;

    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    List<Error> errorList = fieldErrors.stream()
        .map(fieldError -> createError(errorType, fieldError.getField()))
        .collect(Collectors.toList());

    return new ResponseEntity<>(createErrorList(errorList), errorType.getHttpStatus());
  }

  private ErrorList createErrorList(List<Error> errorList) {
    return ErrorList.builder()
        .errors(errorList)
        .build();
  }

  private ResponseEntity<ErrorList> createSingleErrorListResponse(ErrorType errorType,
      Exception exception, String field) {
    Error error = createError(errorType, field);
    return new ResponseEntity<>(createErrorList(singletonList(error)), errorType.getHttpStatus());
  }
}

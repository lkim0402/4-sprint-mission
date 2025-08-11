package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * The template for your JSON error message to send back to the client
 */
public class ErrorResponse {

  private final Instant timestamp = Instant.now();
  private final String code;
  private final String message;
  private final Map<String, Object> details;
  private final String exceptionType; // 발생한 예외의 클래스 이름
  private final int status; // HTTP 상태코드

  // custom ErrorResponse
  public ErrorResponse(DiscodeitException e) {
    this.code = e.getErrorCode().getCode();
    this.message = e.getErrorCode().getMessage();
    this.details = e.getDetails();
    this.exceptionType = e.getClass().getSimpleName();
    this.status = e.getErrorCode().getStatus().value();
  }

  // for non-custom ErrorResponses
  public ErrorResponse(
      String code,
      String message,
      Map<String, Object> details,
      String exceptionType,
      int status
  ) {
    this.code = code;
    this.message = message;
    this.details = details;
    this.exceptionType = exceptionType;
    this.status = status;
  }

}

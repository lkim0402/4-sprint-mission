package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class AuthException extends DiscodeitException {

  public AuthException(Instant timestamp, ErrorCode errorCode,
      Map<String, Object> details) {
    super(timestamp, errorCode, details);
  }


}

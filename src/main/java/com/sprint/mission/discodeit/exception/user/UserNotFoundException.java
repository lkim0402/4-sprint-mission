package com.sprint.mission.discodeit.exception.user;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class UserNotFoundException extends UserException {

  public UserNotFoundException(
      UUID userId) {
    super(
        Instant.now(),
        ErrorCode.USER_NOT_FOUND,
        Map.of("userId", userId));
  }
}

package com.sprint.mission.discodeit.exception.userStatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserStatusException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class UserStatusNotFoundException extends UserStatusException {

  public UserStatusNotFoundException(
      UUID userStatusId) {
    super(
        Instant.now(),
        ErrorCode.USERSTATUS_NOT_FOUND,
        Map.of("userStatusId", userStatusId));
  }
}

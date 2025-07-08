package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserStatusDto {

  public record UserStatusRequestDto(
      UUID userId
  ) {

  }

  public record UserStatusResponseDto(
      UUID userStatusId,
      UUID userid,
      Instant lastActiveTime,
      UserStatus.UserState userState
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    UserStatusResponseDto {" + "\n" +
          "    userStatusId   = " + this.userStatusId + ",\n" +
          "    userid         = " + this.userid + ",\n" +
          "    lastActiveTime = " + this.lastActiveTime + ",\n" +
          "    userState      = " + this.userState + "\n" +
          "  }";
    }
  }

  public record UserStatusResponseDtos(
      List<UserStatusResponseDto> userStatusResponseDtos
  ) {

  }

}

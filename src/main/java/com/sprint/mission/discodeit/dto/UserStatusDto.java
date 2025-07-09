package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserStatusDto {

  public record UserStatusRequestDto(
      @Schema(description = "The UUID of the user", example = "c1244d8c-77db-4c61-823f-0a83cc91bb46", format = "uuid")
      UUID userId
  ) {

  }

  public record UserStatusResponseDto(
      @Schema(description = "The UUID of the userStatus", example = "c1244d8c-77db-4c61-823f-0a83cc91bb46", format = "uuid")
      UUID userStatusId,
      @Schema(description = "The UUID of the user", example = "c1244d8c-77db-4c61-823f-0a83cc91bb46", format = "uuid")
      UUID userid,
      @Schema(
          description = "The last time the user was active, in UTC",
          format = "date-time",
          example = "2025-07-08T08:30:00Z"
      )
      Instant lastActiveTime,
      @Schema(description = "The UserStatus", example = "ONLINE")
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

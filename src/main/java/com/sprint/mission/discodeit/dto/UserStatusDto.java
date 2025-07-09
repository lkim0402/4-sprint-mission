package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserStatusDto {

  public record UserStatusRequest(
      @Schema(description = "The UUID of the user", example = "c1244d8c-77db-4c61-823f-0a83cc91bb46", format = "uuid")
      UUID userId
  ) {

  }

  public record UserStatusResponse(
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

  }

  public record UserStatusResponses(
      List<UserStatusResponse> userStatusResponses
  ) {

  }

  // ============================== PATCH - 유저 상태 업데이트 ==============================
  @Schema(description = "변경할 User 온라인 상태 정보")
  public record UserStatusUpdateRequest(
      Instant newLastActiveAt
  ) {

  }

  public record UserStatusUpdateResponse(
      Instant createdAt,
      UUID id,
      Instant lastActiveAt,
      Boolean online,
      Instant updatedAt,
      UUID userId
  ) {

  }

}

package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReadStatusDto {

  @Schema(description = "Message 읽음 상태 생성 정보")
  public record ReadStatusRequest(
      UUID userId,
      UUID channelId,
      Instant lastReadAt
  ) {

  }

  public record ReadStatusResponse(
      UUID id,
      UUID userId,
      UUID channelId,
      Instant lastReadAt,
      Instant createdAt,
      Instant updatedAt
  ) {

  }

  public record ReadStatusResponseDtos(
      List<ReadStatusResponse> readStatusRequestDtos

  ) {

  }

  // ============================== PATCH - ReadStatus 수정 ==============================
  @Schema(description = "수정할 읽음 상태 정보")
  public record ReadStatusUpdateRequest(
      Instant newLastReadAt
  ) {

  }

  public record ReadStatusUpdateResponse(
      UUID id,
      Instant lastReadAt,
      UUID channelId,
      Instant createdAt,
      Instant updatedAt,
      UUID userId
  ) {

  }

}

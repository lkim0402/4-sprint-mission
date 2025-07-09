package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MessageDto {

  @Schema(description = "")
  public record MessageCreateRequest(
      String content,
      UUID channelId,
      UUID authorId
//      List<MultipartFile> files
  ) {

  }

  public record MessageResponse(
      UUID id,
      String content,
      UUID channelId,
      UUID authorId,
      List<UUID> attachmentIds,
      Instant createdAt,
      Instant updatedAt
  ) {

  }

  public record MessageResponseDtos(
      List<MessageResponse> messageResponses
  ) {

  }

  @Schema(description = "수정할 Message 내용")
  public record MessageUpdateRequestDto(
      String newContent
  ) {

  }

}

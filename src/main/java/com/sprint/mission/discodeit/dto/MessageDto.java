package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageDto {

  @Schema(description = "")
  public record MessageCreateRequest(
      String content,
      UUID channelId,
      UUID authorId
//      List<MultipartFile> files
  ) {

  }

  public record MessageResponseDto(
      UUID id,
      String content,
      UUID channelId,
      UUID authorId,
      List<UUID> attachmentIds,
      Instant createdAt,
      Instant updatedAt
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    MessageResponseDto {" + "\n" +
          "    id        = " + this.id + ",\n" +
          "    content   = " + this.content + ",\n" +
          "    channelId = " + this.channelId + ",\n" +
          "    authorId  = " + this.authorId + ",\n" +
          "  }";
    }
  }

  public record MessageResponseDtos(
      List<MessageResponseDto> messageResponseDtos
  ) {

    @Override
    public String toString() {
      if (messageResponseDtos == null || messageResponseDtos.isEmpty()) {
        return "Messages: []";
      }

      return "\n--- List of Messages ---" +
          messageResponseDtos.stream()
              .map(MessageResponseDto::toString)
              .collect(Collectors.joining(","));
    }
  }

  @Schema(description = "수정할 Message 내용")
  public record MessageUpdateRequestDto(
      String newContent
  ) {

  }

}

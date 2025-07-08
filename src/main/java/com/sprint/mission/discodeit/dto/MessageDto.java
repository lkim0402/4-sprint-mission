package com.sprint.mission.discodeit.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

public class MessageDto {

  public record MessageRequestDto(
      String content,
      UUID channelId,
      UUID authorId,
      List<MultipartFile> files
  ) {

  }

  public record MessageResponseDto(
      UUID id,
      String content,
      UUID channelId,
      UUID authorId
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

  public record MessageUpdateRequestDto(
      String content
  ) {

  }

  public record MessageUpdateResponseDto(
      String content,
      UUID channelId,
      UUID authorId,
      UUID messageId
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    MessageUpdateResponseDto {" + "\n" +
          "    messageId   = " + this.messageId + ",\n" +
          "    content     = " + this.content + ",\n" +
          "    channelId   = " + this.channelId + ",\n" +
          "    authorId    = " + this.authorId + ",\n" +
          "  }";
    }
  }
}

package com.sprint.mission.discodeit.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

public class BinaryContentDto {

  public record BinaryContentRequestDto(
      UUID userId,
      UUID messageId,
      MultipartFile file
  ) {

  }

  public record BinaryContentResponseDto(
      UUID id,
      UUID userId,
      UUID messageId
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    BinaryContentResponseDto {" + "\n" +
          "    id        = " + this.id + ",\n" +
          "    userId    = " + this.userId + ",\n" +
          "    messageId = " + this.messageId + ",\n" +
          "  }";
    }
  }

  public record BinaryContentResponseDtos(
      List<BinaryContentResponseDto> binaryContentResponseDtos
  ) {

    @Override
    public String toString() {
      if (binaryContentResponseDtos == null || binaryContentResponseDtos.isEmpty()) {
        return "Binary Contents: []";
      }
      return "\n--- List of Binary Content ---" +
          binaryContentResponseDtos.stream()
              .map(BinaryContentResponseDto::toString) //
              .collect(Collectors.joining(","));
    }
  }

}

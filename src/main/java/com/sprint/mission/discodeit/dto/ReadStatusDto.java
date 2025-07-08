package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadStatusDto {

  public record ReadStatusRequestDto(
      UUID userId,
      UUID channelId
  ) {

  }

  public record ReadStatusResponseDto(
      UUID id,
      UUID userId,
      UUID channelId,
      Instant lastReadAt
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    ReadStatusResponseDto {" + "\n" +
          "    id         = " + this.id + ",\n" +
          "    userId     = " + this.userId + ",\n" +
          "    channelId  = " + this.channelId + ",\n" +
          "    lastReadAt = " + this.lastReadAt + "\n" +
          "  }";
    }
  }

  public record ReadStatusResponseDtos(
      List<ReadStatusResponseDto> readStatusRequestDtos

  ) {

    @Override
    public String toString() {
      if (readStatusRequestDtos == null || readStatusRequestDtos.isEmpty()) {
        return "ReadStatus list: []";
      }
      return "\n--- List of ReadStatus ---" +
          readStatusRequestDtos.stream()
              .map(ReadStatusResponseDto::toString) //
              .collect(Collectors.joining(","));
    }
  }
}

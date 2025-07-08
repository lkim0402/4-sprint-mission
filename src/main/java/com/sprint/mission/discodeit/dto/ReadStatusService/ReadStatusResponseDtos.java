package com.sprint.mission.discodeit.dto.ReadStatusService;

import java.util.List;
import java.util.stream.Collectors;

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

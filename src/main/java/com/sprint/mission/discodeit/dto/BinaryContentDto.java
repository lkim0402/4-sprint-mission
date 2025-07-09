package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BinaryContentDto {

  public record BinaryContentRequest(
      String fileName,
      String contentType,
      byte[] bytes
  ) {

  }

  @Schema(name = "BinaryContent")
  public record BinaryContentResponse(
      UUID id,
      Instant createdAt,
      String fileName,
      Long size,
      String contentType,
      @Schema(format = "byte")
      String bytes // Changed to String for base64 encoding
  ) {

  }

  public record ResponseDtos(
      List<BinaryContentResponse> binaryContentResponses
  ) {

  }

}

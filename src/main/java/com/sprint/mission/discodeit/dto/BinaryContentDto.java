package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BinaryContentDto {

  // NOT USED for API spec
//  public record RequestDto(
//      UUID userId,
//      UUID messageId,
//      MultipartFile file
//  ) {
//
//  }

//  public record ResponseDto(
//      UUID id,
//      UUID userId,
//      UUID messageId
//  ) {
//
//    @Override
//    public String toString() {
//      return "\n" +
//          "    ResponseDto {" + "\n" +
//          "    id        = " + this.id + ",\n" +
//          "    userId    = " + this.userId + ",\n" +
//          "    messageId = " + this.messageId + ",\n" +
//          "  }";
//    }
//  }

//  public record ResponseDtos(
//      List<ResponseDto> responseDtos
//  ) {
//
//    @Override
//    public String toString() {
//      if (responseDtos == null || responseDtos.isEmpty()) {
//        return "Binary Contents: []";
//      }
//      return "\n--- List of Binary Content ---" +
//          responseDtos.stream()
//              .map(ResponseDto::toString) //
//              .collect(Collectors.joining(","));
//    }
//  }

  // ========================== FOR API SPECS ==========================

  public record BinaryContentRequestDto(
      String fileName,
      String contentType,
      byte[] bytes
  ) {

  }
  
  @Schema(name = "BinaryContent")
  public record BinaryContentResponseDto(
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

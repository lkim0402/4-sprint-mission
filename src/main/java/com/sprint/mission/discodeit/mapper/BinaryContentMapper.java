package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.Arrays;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BinaryContentMapper {

//  // Request
//  public BinaryContent toBinaryContent(UUID userId, UUID messageId, MultipartFile file) {
//
//    if (file == null) {
//      return null;
//    }
//    try {
//      return new BinaryContent(
//          userId,
//          messageId,
//          file.getBytes(),
//          file.getOriginalFilename(),
//          file.getContentType()
//      );
//    } catch (IOException e) {

  /// /      throw new RuntimeException("Error reading file bytes"); /      throw new
  /// IOException("Error reading file bytes");
//      throw new FileAccessException("Error reading file bytes.", HttpStatus.NOT_ACCEPTABLE.value());
//    }
//  }
//
//  // Response
//  public ResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
//    return new ResponseDto(
//        binaryContent.getId(),
//        binaryContent.getUserId(),
//        binaryContent.getMessageId()
//    );
//  }
//
//  // Response
//  public ResponseDtos toBinaryContentResponseDtos(List<BinaryContent> binaryContents) {
//    return new ResponseDtos(
//        binaryContents
//            .stream()
//            .map(this::toBinaryContentResponseDto)
//            .toList()
//    );
//  }
  public BinaryContentResponse toResponseDto(BinaryContent binaryContent) {
    return new BinaryContentResponse(
        binaryContent.getId(),
        binaryContent.getCreatedAt(),
        binaryContent.getFileName(),
        binaryContent.getSize(),
        binaryContent.getContentType(),
        Arrays.toString(binaryContent.getBytes())
    );
  }

  public ResponseDtos toResponseDtos(List<BinaryContent> binaryContents) {
    return new ResponseDtos(
        binaryContents
            .stream()
            .map(this::toResponseDto)
            .toList()
    );
  }
}

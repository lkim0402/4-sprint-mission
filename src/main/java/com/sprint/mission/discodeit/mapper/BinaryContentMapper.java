package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.FileAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class BinaryContentMapper {

  // Request
  public BinaryContent toBinaryContent(UUID userId, UUID messageId, MultipartFile file) {

    if (file == null) {
      return null;
    }
    try {
      return new BinaryContent(
          userId,
          messageId,
          file.getBytes(),
          file.getOriginalFilename(),
          file.getContentType()
      );
    } catch (IOException e) {
//      throw new RuntimeException("Error reading file bytes");
//      throw new IOException("Error reading file bytes");
      throw new FileAccessException("Error reading file bytes.", HttpStatus.NOT_ACCEPTABLE.value());
    }
  }

  // Response
  public BinaryContentResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
    return new BinaryContentResponseDto(
        binaryContent.getId(),
        binaryContent.getUserId(),
        binaryContent.getMessageId()
    );
  }

  // Response
  public BinaryContentResponseDtos toBinaryContentResponseDtos(List<BinaryContent> binaryContents) {
    return new BinaryContentResponseDtos(
        binaryContents
            .stream()
            .map(this::toBinaryContentResponseDto)
            .toList()
    );
  }
}

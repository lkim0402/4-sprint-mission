package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.Arrays;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BinaryContentMapper {

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

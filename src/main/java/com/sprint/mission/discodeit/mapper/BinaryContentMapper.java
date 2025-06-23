package com.sprint.mission.discodeit.mapper;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BinaryContentMapper {

    // Request
    public BinaryContent toBinaryContent(BinaryContentRequestDto binaryContentRequestDto) {
        return new BinaryContent(
                binaryContentRequestDto.userId(),
                binaryContentRequestDto.messageId(),
                binaryContentRequestDto.bytes(),
                binaryContentRequestDto.fileName(),
                binaryContentRequestDto.fileType()
        );
    }

    // Response
    public BinaryContentResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
        return new BinaryContentResponseDto(
                binaryContent.getBytes(),
                binaryContent.getFileName(),
                binaryContent.getFileType()
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

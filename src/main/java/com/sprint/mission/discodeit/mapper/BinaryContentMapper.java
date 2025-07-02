package com.sprint.mission.discodeit.mapper;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class BinaryContentMapper {

    // Request
    public BinaryContent toBinaryContent(UUID userId, UUID messageId, MultipartFile file) {
        try {
            return new BinaryContent(
                    userId,
                    messageId,
                    file == null ? null : file.getBytes(),
                    file == null ? null :file.getName(),
                    file == null ? null :file.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to read bytes from multipart file");
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

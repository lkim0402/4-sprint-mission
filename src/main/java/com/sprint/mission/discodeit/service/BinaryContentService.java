package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContentResponseDto create(BinaryContentRequestDto binaryContentRequestDto);
    BinaryContentResponseDto find(UUID userId);
    BinaryContentResponseDtos findAllByIdIn(List<UUID> binaryContentIds);
    void delete(UUID binaryContentId);
    void deleteAll();
}

package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent create(BinaryContentRequestDto binaryContentRequestDto);
    BinaryContentResponseDto find(UUID userId);
    BinaryContentResponseDtos findAllByIdIn(List<UUID> binaryContentIds);
    void delete(UUID binaryContentId);
}

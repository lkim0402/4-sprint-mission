package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {

  BinaryContentResponseDto create(BinaryContentRequestDto binaryContentRequestDto);

  //    BinaryContentResponseDto find(UUID binaryContentId);
  BinaryContent find(UUID binaryContentId); // 심화

  BinaryContentResponseDtos findAllByIdIn(List<UUID> binaryContentIds);

  void delete(UUID binaryContentId);

  void deleteAll();
}

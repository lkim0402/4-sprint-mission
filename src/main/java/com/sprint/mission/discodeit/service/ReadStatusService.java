package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.dto.ReadStatusService.*;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(ReadStatusRequestDto readStatusRequestDto);
    ReadStatusResponseDto find(UUID id);
    ReadStatusResponseDtos findAllByUserId(UUID id);
    void update(UUID readStatusId);
    void delete(UUID id);
    void deleteAll();
}

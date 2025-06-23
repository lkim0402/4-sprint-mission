package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDtos;
import com.sprint.mission.discodeit.dto.ReadStatusService.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(ReadStatusRequestDto readStatusRequestDto);
    ReadStatusResponseDto find(UUID id);
    ReadStatusResponseDtos findAllByUserId(UUID id);
//    ReadStatus update(UUID readStatusId, UpdateReadStatusDto updateReadStatusDto);
    ReadStatus update(UUID readStatusId);
    void delete(UUID id);
}

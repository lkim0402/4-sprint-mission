package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDtos;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public interface UserStatusService {
    UserStatusResponseDto create(UserStatusRequestDto userStatusRequestDto);
    UserStatusResponseDto find(UUID userStatusId);
    UserStatusResponseDto findByUserId(UUID userId);
    UserStatusResponseDtos findAll();
    void update(UUID id);
    void delete(UUID id);
    void deleteAll();
}

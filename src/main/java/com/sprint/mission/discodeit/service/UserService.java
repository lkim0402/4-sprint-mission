package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto find(UUID userId);
//    UserResponseDtos findAll();
    UserDtos findAll(); // 심화 요구사항
    UpdateUserResponseDto update(UUID userId, UpdateUserRequestDto updateUserRequestDto);
    void delete(UUID id);
    void deleteAll();
}

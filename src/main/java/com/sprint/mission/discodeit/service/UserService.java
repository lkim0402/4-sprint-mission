package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserService.UpdateUserRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UserRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserService.UserResponseDtos;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(UserRequestDto userRequestDto);
    UserResponseDto find(UUID userId);
    UserResponseDtos findAll();
    User update(UpdateUserRequestDto updateUserRequestDto);
    void delete(User user);
}

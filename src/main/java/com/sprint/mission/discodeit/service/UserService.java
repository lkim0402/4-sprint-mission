package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDto.*;
import java.util.List;
import java.util.UUID;

public interface UserService {

  UserGetDto create(UserCreateRequestDto userCreateRequestDto);

  UserCreateResponseDto find(UUID userId);

  List<UserGetDto> findAll(); // 심화 요구사항

  UserUpdateResponseDto update(UUID userId, UserUpdateRequestDto userUpdateRequestDto);

  void delete(UUID id);

  void deleteAll();
}

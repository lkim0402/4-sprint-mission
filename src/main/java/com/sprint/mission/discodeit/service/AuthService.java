package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.RoleUpdateRequest;

public interface AuthService {

//  UserDto login(LoginRequest loginRequest);

  UserDto updateRole(RoleUpdateRequest roleUpdateRequest);

  boolean isUserOnline(String username);
}

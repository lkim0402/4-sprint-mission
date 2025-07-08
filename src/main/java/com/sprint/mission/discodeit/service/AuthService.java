package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.AuthService.LoginRequest;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;

public interface AuthService {
    UserLoginResponseDto login(LoginRequest loginRequest);
}

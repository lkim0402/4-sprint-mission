package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;

public interface AuthService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);
}

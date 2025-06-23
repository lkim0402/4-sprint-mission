package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthMapper {

    final private UserStatusMapper userStatusMapper;

    // Request
    public UserLoginRequestDto toUserLoginRequestDto(String username, String password) {
        return new UserLoginRequestDto(
                username,
                password
        );
    }

    // Response
    public UserLoginResponseDto toUserLoginResponseDto(User user, UserStatus userStatus) {

        return new UserLoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                userStatusMapper.toUserStatusResponseDto(userStatus)
        );
    }
}

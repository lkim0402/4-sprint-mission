package com.sprint.mission.discodeit.dto.AuthService;

import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;

import java.util.UUID;

// login response from service
public record UserLoginResponseDto(
        UUID userId,
        String username,
        String email,
        UserStatusResponseDto userStatusResponseDto
) {
    @Override
    public String toString() {
        return "\n" +
                "    UserLoginResponseDto {" + "\n" +
                "    userId                = " + this.userId + ",\n" +
                "    username              = " + this.username + ",\n" +
                "    email                 = " + this.email + ",\n" +
                "    userStatusResponseDto = " + this.userStatusResponseDto + "\n" +
                "  }";
    }
}

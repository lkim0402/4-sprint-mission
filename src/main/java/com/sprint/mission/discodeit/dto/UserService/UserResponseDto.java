package com.sprint.mission.discodeit.dto.UserService;

import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;

import java.util.UUID;

/**
 * Represents the model sent from or to the API client
 * Does NOT include password
 * */

public record UserResponseDto (
    UUID id,
    String username,
    String email,
    UserStatusResponseDto userStatusResponseDto
) {
    @Override
    public String toString() {
        return "\n" +
                "    UserResponseDto {" + "\n" +
                "    id                    = " + this.id + ",\n" +
                "    username              = " + this.username + ",\n" +
                "    email                 = " + this.email + ",\n" +
                "    userStatusResponseDto = " + this.userStatusResponseDto + "\n" +
                "  }";
    }
}

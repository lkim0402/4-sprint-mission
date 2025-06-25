package com.sprint.mission.discodeit.dto.UserService;

import java.util.UUID;

public record UpdateUserResponseDto(
    UUID userId,
    String username,
    String email,
    UUID profileId
) {
    @Override
    public String toString() {
        return "\n" +
                "    UpdateUserResponseDto {" + "\n" +
                "    userId                = " + this.userId + ",\n" +
                "    username              = " + this.username + ",\n" +
                "    email                 = " + this.email + ",\n" +
                "    profileId             = " + this.profileId + ",\n" +
                "  }";
    }
}

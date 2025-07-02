package com.sprint.mission.discodeit.dto.UserService;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
    UUID id,
    Instant createdAt,
    Instant updatedAt,
    String username,
    String email,
    UUID profileId,
    Boolean online
) {
    @Override
    public String toString() {
        return "\n" +
                "    UserDto    {" + "\n" +
                "    userId     = " + this.id + ",\n" +
                "    createdAt  = " + this.createdAt + ",\n" +
                "    updatedAt  = " + this.updatedAt + ",\n" +
                "    username   = " + this.username + ",\n" +
                "    email      = " + this.email + ",\n" +
                "    profileId  = " + this.profileId + ",\n" +
                "    online?    = " + this.profileId + ",\n" +
                "  }";
    }
}

package com.sprint.mission.discodeit.dto.UserStatusService;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserStatusResponseDto(
    UUID userid,
    Instant lastActiveTime,
    UserStatus.UserState userState
) {
    @Override
    public String toString() {
        return "\n" +
                "    UserStatusResponseDto {" + "\n" +
                "    userid         = " + this.userid + ",\n" +
                "    lastActiveTime = " + this.lastActiveTime + ",\n" +
                "    userState      = " + this.userState + "\n" +
                "  }";
    }
}

package com.sprint.mission.discodeit.dto.UserStatusService;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserStatusRequestDto(
//    UUID userid,
//    Instant lastActiveTime,
//    UserStatus.UserState userState
        UUID userId
) {}

package com.sprint.mission.discodeit.dto.UserStatusService;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.UUID;

public record UpdateUserStatusDto(
        UUID userId
) {}

package com.sprint.mission.discodeit.dto.UserService;

import java.util.UUID;

public record UpdateUserResponseDto(
    UUID userId,
    String username,
    String email,
    UUID profileId
) {}

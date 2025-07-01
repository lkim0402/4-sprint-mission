package com.sprint.mission.discodeit.dto.UserService;

import java.util.UUID;

public record UpdateUserRequestDto(
//    UUID userId,
    String username,
    String password,
    String email,
    UUID profileId
){}

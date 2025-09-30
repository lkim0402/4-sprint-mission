package com.sprint.mission.discodeit.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.entity.Role;
import java.util.UUID;

public record UserDto(
    UUID userId,
    String username,
    String email,
    BinaryContentDto profile,
    Boolean online,
    Role role
) {

}

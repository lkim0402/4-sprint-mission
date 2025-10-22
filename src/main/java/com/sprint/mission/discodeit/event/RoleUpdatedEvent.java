package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.Role;
import java.util.UUID;

public record RoleUpdatedEvent(
    Role role,
    UUID userId
) {

}

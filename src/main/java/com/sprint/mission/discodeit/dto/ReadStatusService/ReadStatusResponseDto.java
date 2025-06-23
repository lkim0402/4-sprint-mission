package com.sprint.mission.discodeit.dto.ReadStatusService;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusResponseDto(
    UUID id,
    UUID userId,
    UUID channelId,
    Instant lastReadAt
) {}

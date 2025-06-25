package com.sprint.mission.discodeit.dto.ReadStatusService;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusResponseDto(
    UUID id,
    UUID userId,
    UUID channelId,
    Instant lastReadAt
) {
    @Override
    public String toString() {
        return "\n" +
                "    ReadStatusResponseDto {" + "\n" +
                "    id         = " + this.id + ",\n" +
                "    userId     = " + this.userId + ",\n" +
                "    channelId  = " + this.channelId + ",\n" +
                "    lastReadAt = " + this.lastReadAt + "\n" +
                "  }";
    }
}

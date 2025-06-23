package com.sprint.mission.discodeit.dto.ChannelService;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.UUID;

public record UpdateChannelResponseDto(
        UUID channelId,
        ChannelType channelType,
        String name,
        String description
) {
}

package com.sprint.mission.discodeit.dto.ChannelService;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.util.List;
import java.util.UUID;

public record PrivateChannelRequestDto(
        ChannelType type,
        List<UUID> userIds
) {}

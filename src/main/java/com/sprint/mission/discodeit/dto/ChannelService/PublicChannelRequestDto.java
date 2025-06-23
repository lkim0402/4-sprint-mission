package com.sprint.mission.discodeit.dto.ChannelService;

import com.sprint.mission.discodeit.entity.ChannelType;

public record PublicChannelRequestDto(
        ChannelType type,
        String name,
        String description
) {}

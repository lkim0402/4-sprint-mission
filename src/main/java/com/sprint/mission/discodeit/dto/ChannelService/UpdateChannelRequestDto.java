package com.sprint.mission.discodeit.dto.ChannelService;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

// only public channels are allowed to edit
public record UpdateChannelRequestDto(
//        UUID channelId,
        ChannelType type,
        String name,
        String description
) {}

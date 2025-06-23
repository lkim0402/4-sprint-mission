package com.sprint.mission.discodeit.dto.ChannelService;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponseDto(
        UUID channelId,
        Instant latestMessageTime,
        ChannelType channelType,
        String name, // null for private
        String description, // null for private
        List<UUID> userIds // null for public
) {}

/**
 * [PUBLIC]
 *         UUID channelId,
 *         ChannelType channelType,
 *         String name,
 *         String description,
 *         Instant latestMessageTime,
 *
 * [PRIVATE]
 *         UUID channelId,
 *         ChannelType channelType,
 *         Instant latestMessageTime,
 *         List<UUID> userIds
 */
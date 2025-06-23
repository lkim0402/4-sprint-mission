package com.sprint.mission.discodeit.dto.MessageService;

import java.util.UUID;

public record MessageResponseDto(
        UUID messageId,
        String content,
        UUID channelId,
        UUID authorId
) {}

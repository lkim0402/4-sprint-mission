package com.sprint.mission.discodeit.dto.MessageService;

import java.util.UUID;

public record UpdateMessageResponseDto(
        String content,
        UUID channelId,
        UUID authorId,
        UUID messageId
) {}

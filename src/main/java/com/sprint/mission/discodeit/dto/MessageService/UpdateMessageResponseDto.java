package com.sprint.mission.discodeit.dto.MessageService;

import java.util.List;
import java.util.UUID;

public record UpdateMessageResponseDto(
        String content,
        UUID channelId,
        UUID authorId,
        UUID messageId
) {
    @Override
    public String toString() {
        return "\n" +
                "    UpdateMessageResponseDto {" + "\n" +
                "    messageId   = " + this.messageId + ",\n" +
                "    content     = " + this.content + ",\n" +
                "    channelId   = " + this.channelId + ",\n" +
                "    authorId    = " + this.authorId + ",\n" +
                "  }";
    }
}

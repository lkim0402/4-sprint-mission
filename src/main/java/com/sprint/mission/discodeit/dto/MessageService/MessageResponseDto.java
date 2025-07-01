package com.sprint.mission.discodeit.dto.MessageService;

import java.util.UUID;

public record MessageResponseDto(
        UUID id,
        String content,
        UUID channelId,
        UUID authorId
) {
    @Override
    public String toString() {
        return "\n" +
                "    MessageResponseDto {" + "\n" +
                "    id        = " + this.id + ",\n" +
                "    content   = " + this.content + ",\n" +
                "    channelId = " + this.channelId + ",\n" +
                "    authorId  = " + this.authorId + ",\n" +
                "  }";
    }

}

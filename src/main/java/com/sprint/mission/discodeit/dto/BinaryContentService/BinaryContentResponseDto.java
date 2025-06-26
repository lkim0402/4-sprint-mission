package com.sprint.mission.discodeit.dto.BinaryContentService;

import java.util.UUID;

public record BinaryContentResponseDto(
    UUID id,
    UUID userId,
    UUID messageId,
    String fileName,
    String fileType
) {
    @Override
    public String toString() {
        return "\n" +
                "    BinaryContentResponseDto {" + "\n" +
                "    id        = " + this.id + ",\n" +
                "    userId    = " + this.userId + ",\n" +
                "    messageId = " + this.messageId + ",\n" +
                "    fileName  = " + this.fileName + ",\n" +
                "    fileType  = " + this.fileType + "\n" +
                "  }";
    }
}

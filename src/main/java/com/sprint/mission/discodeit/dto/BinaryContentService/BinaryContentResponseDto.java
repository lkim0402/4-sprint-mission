package com.sprint.mission.discodeit.dto.BinaryContentService;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public record BinaryContentResponseDto(
    UUID id,
    UUID userId,
    UUID messageId
) {
    @Override
    public String toString() {
        return "\n" +
                "    BinaryContentResponseDto {" + "\n" +
                "    id        = " + this.id + ",\n" +
                "    userId    = " + this.userId + ",\n" +
                "    messageId = " + this.messageId + ",\n" +
                "  }";
    }
}

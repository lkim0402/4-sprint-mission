package com.sprint.mission.discodeit.dto.BinaryContentService;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public record BinaryContentRequestDto(
        UUID userId,
        UUID messageId,
        MultipartFile file
) {}

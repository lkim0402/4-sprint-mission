package com.sprint.mission.discodeit.dto.MessageService;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record MessageRequestDto(
        String content,
        UUID channelId,
        UUID authorId,
        List<MultipartFile> files
) {}

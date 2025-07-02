package com.sprint.mission.discodeit.dto.MessageService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public record UpdateMessageRequestDto(
        String content
) {}

package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDto.*;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

  MessageResponseDto create(MessageCreateRequest messageCreateRequest,
      List<MultipartFile> attachments);

  MessageResponseDto find(UUID messageId);

  MessageResponseDtos findallByChannelId(UUID channelId);

  MessageResponseDto update(UUID messageId, MessageUpdateRequestDto messageUpdateRequestDto);

  void delete(UUID id);

  void deleteAll();
}

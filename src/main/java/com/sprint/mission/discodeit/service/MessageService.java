package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDto.*;
import java.util.UUID;

public interface MessageService {

  MessageResponseDto create(MessageRequestDto messageRequestDto);

  MessageResponseDto find(UUID messageId);

  MessageResponseDtos findallByChannelId(UUID channelId);

  MessageUpdateResponseDto update(UUID messageId, MessageUpdateRequestDto messageUpdateRequestDto);

  void delete(UUID id);

  void deleteAll();
}

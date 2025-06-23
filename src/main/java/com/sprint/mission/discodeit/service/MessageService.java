package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageService.*;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(MessageRequestDto messageRequestDto);
    MessageResponseDto find(UUID messageId);
    MessageResponseDtos findallByChannelId(UUID channelId);
    UpdateMessageResponseDto update(UUID messageId, UpdateMessageRequestDto updateMessageRequestDto);
    void delete(Message message);

}

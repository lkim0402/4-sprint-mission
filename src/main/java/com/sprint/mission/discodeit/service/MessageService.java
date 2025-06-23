package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageService.MessageRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDtos;
import com.sprint.mission.discodeit.dto.MessageService.UpdateMessageRequestDto;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
//    Message create(String content, UUID channelId, UUID authorId);
//    Message find(UUID messageId);
//    List<Message> findAll();
//    Message update(UUID messageId, String newContent);
//    void delete(UUID messageId);

    Message create(MessageRequestDto messageRequestDto);
    MessageResponseDto find(UUID messageId);
    MessageResponseDtos findallByChannelId(UUID channelId);
    Message update(UUID messageId, UpdateMessageRequestDto updateMessageRequestDto);
    void delete(Message message);

}

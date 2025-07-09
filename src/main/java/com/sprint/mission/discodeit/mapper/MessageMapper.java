package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDto.*;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MessageMapper {

  // Request
//  public Message toMessage(MessageRequestDto messageRequestDto) {
//    return new Message(
//        messageRequestDto.content(),
//        messageRequestDto.channelId(),
//        messageRequestDto.authorId()
//    );
//  }

  // Response
  public MessageResponseDto toMessageResponseDto(Message message) {
    return new MessageResponseDto(
        message.getId(),
        message.getContent(),
        message.getChannelId(),
        message.getAuthorId()
    );
  }

  // Response
  public MessageResponseDtos toMessageResponseDtos(List<Message> messages) {
    return new MessageResponseDtos(
        messages
            .stream()
            .map(this::toMessageResponseDto)
            .toList()
    );
  }

  public MessageUpdateResponseDto toUpdateMessageResponseDto(Message message) {
    return new MessageUpdateResponseDto(
        message.getContent(),
        message.getChannelId(),
        message.getAuthorId(),
        message.getId()
    );
  }
}

package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDto.*;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MessageMapper {

  // Response
  public MessageResponse toMessageResponseDto(Message message) {
    return new MessageResponse(
        message.getId(),
        message.getContent(),
        message.getChannelId(),
        message.getAuthorId(),
        message.getAttachmentIds(),
        message.getCreatedAt(),
        message.getUpdatedAt()
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


}

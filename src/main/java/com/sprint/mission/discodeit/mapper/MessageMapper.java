package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto.UserChannelResponse;
import com.sprint.mission.discodeit.dto.MessageDto.*;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MessageMapper {

  // Response
  public MessageResponseDto toMessageResponseDto(Message message) {
    return new MessageResponseDto(
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

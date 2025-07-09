package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto.*;
import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class ChannelMapper {

  public ChannelResponse toChannelResponse(Channel channel) {
    return new ChannelResponse(
        channel.getId(),
        channel.getName(),
        channel.getDescription(),
        channel.getCreatedAt(),
        channel.getUpdatedAt(),
        channel.getType()
    );
  }

  public ChannelResponses toChannelResponses(List<ChannelResponse> channelResponses) {
    return new ChannelResponses(
        channelResponses
    );
  }

  // Request - public channel creation
//  public Channel requestDtoToPublicChannel(PublicCreateChannelRequestDto channelRequestDto) {
//    return new Channel(
//        channelRequestDto.type(),
//        channelRequestDto.name(),
//        channelRequestDto.description()
//    );
//  }

//  // Request - private channel creation
//  public Channel requestDtoToPrivateChannel(PrivateCreateChannelRequestDto channelRequestDto) {
//    return new Channel(
//        channelRequestDto.(),
//        null,
//        null
//    );
//  }

  // used in controller
//  public PublicCreateChannelRequestDto publicChannelRequestDto(
//      ChannelRequestDto channelRequestDto) {
//    return new PublicCreateChannelRequestDto(
//        channelRequestDto.channelType(),
//        channelRequestDto.name(),
//        channelRequestDto.description()
//    );
//  }

  // Response
//  public ChannelResponseDto toChannelResponseDto(Channel channel, List<UUID> userIds,
//      Instant lastMessageTime) {
//    return switch (channel.getType()) {
//      case PRIVATE -> new ChannelResponseDto(
//          channel.getId(),
//          lastMessageTime,
//          channel.getType(),
//          null, // no name for PRIVATE channel
//          null, // no description for PRIVATE channel
//          userIds
//      );
//      case PUBLIC -> new ChannelResponseDto(
//          channel.getId(),
//          lastMessageTime,
//          channel.getType(),
//          channel.getName(),
//          channel.getDescription(),
//          null // no user list for PUBLIC channel
//      );
//    };
//  }
//
//  // Response
//  public ChannelResponseDtos toChannelResponseDtos(List<ChannelResponseDto> channelResponseDtos) {
//    return new ChannelResponseDtos(
//        channelResponseDtos
//    );
//  }
//
//
//  // Response
//  public ChannelUpdateResponseDto toUpdateChannelResponseDto(Channel channel) {
//    return new ChannelUpdateResponseDto(
//        channel.getId(),
//        channel.getType(),
//        channel.getName(),
//        channel.getDescription()
//    );
//  }
//
//  // ================== used in controller ==================
//  // update -> channel (for updating channel response)
//  public ChannelResponseDto toChannelResponseDto(ChannelUpdateResponseDto updateDto) {
//    return new ChannelResponseDto(
//        updateDto.channelId(),
//        null,  // lastMessageTime is not relevant for an update confirmation
//        updateDto.channelType(),
//        updateDto.name(),
//        updateDto.description(),
//        null  // userIds are not relevant for a public channel update response
//    );
//  }
}

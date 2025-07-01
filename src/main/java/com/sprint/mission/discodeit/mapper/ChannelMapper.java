package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelService.*;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDtos;
import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class ChannelMapper {

    // Request - public channel creation
    public Channel requestDtoToPublicChannel(PublicChannelRequestDto channelRequestDto) {
        return new Channel(
                channelRequestDto.type(),
                channelRequestDto.name(),
                channelRequestDto.description()
        );
    }

    // Request - private channel creation
    public Channel requestDtoToPrivateChannel(PrivateChannelRequestDto channelRequestDto) {
        return new Channel(
                channelRequestDto.type(),
                null,
                null
        );
    }

    // used in controller
    public PublicChannelRequestDto publicChannelRequestDto(ChannelRequestDto channelRequestDto) {
        return new PublicChannelRequestDto(
                channelRequestDto.channelType(),
                channelRequestDto.name(),
                channelRequestDto.description()
        );
    }

    // Response
    public ChannelResponseDto toChannelResponseDto(Channel channel, List<UUID> userIds, Instant lastMessageTime) {
        return switch (channel.getType()) {
            case PRIVATE -> new ChannelResponseDto(
                    channel.getId(),
                    lastMessageTime,
                    channel.getType(),
                    null, // no name for PRIVATE channel
                    null, // no description for PRIVATE channel
                    userIds
            );
            case PUBLIC -> new ChannelResponseDto(
                    channel.getId(),
                    lastMessageTime,
                    channel.getType(),
                    channel.getName(),
                    channel.getDescription(),
                    null // no user list for PUBLIC channel
            );
        };
    }

    // Response
    public ChannelResponseDtos toChannelResponseDtos(List<ChannelResponseDto> channelResponseDtos) {
        return new ChannelResponseDtos(
                channelResponseDtos
        );
    }

    // Request
//    public UpdateChannelRequestDto toUpdateChannelRequestDto(Channel channel) {
//        return new UpdateChannelRequestDto(
//                channel.getId(),
//                channel.getType(),
//                channel.getName(),
//                channel.getDescription()
//        );
//    }

    // Response
    public UpdateChannelResponseDto toUpdateChannelResponseDto(Channel channel) {
        return new UpdateChannelResponseDto(
            channel.getId(),
            channel.getType(),
            channel.getName(),
            channel.getDescription()
        );
    }
}

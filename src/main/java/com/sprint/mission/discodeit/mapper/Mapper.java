package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelService.PrivateChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PublicChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.UpdateChannelRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDtos;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDtos;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDtos;
import com.sprint.mission.discodeit.entity.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Mapper {

    // ======================== UserService ========================

    // Request
    // client -> service (user)
    public User toUser(UserRequestDto userDTO) {
        return new User(
                userDTO.username(),
                userDTO.email(),
                userDTO.password()
        );
    }

    // Response
    // client <- service (user)
    public UserResponseDto toUserResponseDto(User user, Optional<UserStatus> optionalUserStatus) {
        UserStatus userStatus = optionalUserStatus.orElse(null);

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                toUserStatusResponseDto(userStatus)
        );
    }

    // Response
    // client <- service (users)
    public UserResponseDtos toUserResponseDtos(List<UserResponseDto> userResponseDtos) {
        return new UserResponseDtos(
                userResponseDtos
        );
    }



    // ======================== UserStatusService + UserService ========================


    // Request
    // client -> service
    public UserStatus toUserStatus(UserStatusRequestDto userStatusRequestDto) {
        return new UserStatus(userStatusRequestDto.userId());
    }

    // Response
    // client <- service
    public UserStatusResponseDto toUserStatusResponseDto(UserStatus userStatus) {
        return new UserStatusResponseDto(
                userStatus.getUserId(),
                userStatus.getLastActiveTime(),
                userStatus.getStatus()
        );
    }

    public UserStatusResponseDtos toUserStatusResponseDtos(List<UserStatus> userStatusResponseDtos) {
        return new UserStatusResponseDtos(
                userStatusResponseDtos.stream()
                    .map(this::toUserStatusResponseDto)
                    .toList()
        );
    }

    // ======================== AuthService ========================
    // Request
    public UserLoginRequestDto toUserLoginRequestDto(String username, String password) {
        return new UserLoginRequestDto(
                username,
                password
        );
    }

    // Response
    public UserLoginResponseDto toUserLoginResponseDto(User user, UserStatus userStatus) {

        return new UserLoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                toUserStatusResponseDto(userStatus)
        );
    }

    // ======================== ChannelService ========================


    // Request - create public channel
//    public publicChannelRequestDto toPublicChannelRequestDto()

    // Request - public channel
    // client -> server
    public Channel toPublicChannel(PublicChannelRequestDto channelRequestDto) {
        return new Channel(
                channelRequestDto.type(),
                channelRequestDto.name(),
                channelRequestDto.description()
        );
    }

    // Request - private channel
    // client -> server
    public Channel toPrivateChannel(PrivateChannelRequestDto channelRequestDto) {
        return new Channel(
                channelRequestDto.type(),
                null,
                null
        );
    }

    // Response
    // client <- server
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

    public ChannelResponseDtos toChannelResponseDtos(List<ChannelResponseDto> channelResponseDtos) {
        return new ChannelResponseDtos(
                channelResponseDtos
        );
    }

    public UpdateChannelRequestDto toUpdateChannelRequestDto(Channel channel) {
        return new UpdateChannelRequestDto(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription()
        );
    }

    // ======================== MessageService ========================
    // Request
    // client -> service (message)
    public Message toMessage(MessageRequestDto messageRequestDto) {
        return new Message(
                messageRequestDto.content(),
                messageRequestDto.channelId(),
                messageRequestDto.authorId()
        );
    }

    public MessageResponseDto toMessageResponseDto(Message message) {
        return new MessageResponseDto(
                message.getId(),
                message.getContent(),
                message.getChannelId(),
                message.getAuthorId()
        );
    }

    public MessageResponseDtos toMessageResponseDtos(List<Message> messages) {
        return new MessageResponseDtos(
                messages
                .stream()
                .map(this::toMessageResponseDto)
                .toList()
        );
    }

    // ======================== ReadStatusService ========================
    // Request
    // client -> service (readstatus)
    public ReadStatus toReadStatus(ReadStatusRequestDto readStatusRequestDto) {
        return new ReadStatus(
                readStatusRequestDto.userId(),
                readStatusRequestDto.channelId()
        );
    }

    public ReadStatusResponseDto toReadStatusDto(ReadStatus readStatus) {
        return new ReadStatusResponseDto(
                readStatus.getId(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastReadAt()
        );
    }

    public ReadStatusResponseDtos toReadStatusResponseDtos(List<ReadStatus> readStatuses) {
        return new ReadStatusResponseDtos(
                readStatuses
                .stream()
                .map(this::toReadStatusDto)
                .toList()
        );
    }

    // ======================== BinaryContentService ========================

    // Request
    // used also in UserService
    // client -> service
    public BinaryContent toBinaryContent(BinaryContentRequestDto binaryContentRequestDto,
                                         UUID userId, UUID messageId) {
        return new BinaryContent(
                userId,
                messageId,
                binaryContentRequestDto.getBytes(),
                binaryContentRequestDto.getFileName(),
                binaryContentRequestDto.getFileType()
        );
    }

    public BinaryContentResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
        return new BinaryContentResponseDto(
                binaryContent.getBytes(),
                binaryContent.getFileName(),
                binaryContent.getFileType()
        );
    }

    public BinaryContentResponseDtos toBinaryContentResponseDtos(List<BinaryContent> binaryContents) {
        return new BinaryContentResponseDtos(
                binaryContents
                .stream()
                .map(this::toBinaryContentResponseDto)
                .toList()
        );
    }
}

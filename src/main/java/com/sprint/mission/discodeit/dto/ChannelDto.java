package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChannelDto {

  public record ChannelRequestDto(
      Instant latestMessageTime,
      ChannelType channelType,
      String name, // null for private
      String description, // null for private
      List<UUID> userIds // null for public
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    ChannelResponseDto {" + "\n" +
          "    channelType       = " + this.channelType + ",\n" +
          "    name              = " + this.name + ",\n" +
          "    description       = " + this.description + ",\n" +
          "    latestMessageTime = " + this.latestMessageTime + ",\n" +
          "    userIds           = " + this.userIds + "\n" +
          "  }";
    }
  }

  /**
   * [PUBLIC] UUID channelId, ChannelType channelType, String name, String description, Instant
   * latestMessageTime,
   * <p>
   * [PRIVATE] UUID channelId, ChannelType channelType, Instant latestMessageTime, List<UUID>
   * userIds
   */

  public record ChannelResponseDto(
      UUID id,
      Instant latestMessageTime,
      ChannelType channelType,
      String name, // null for private
      String description, // null for private
      List<UUID> userIds // null for public
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    ChannelResponseDto {" + "\n" +
          "    channelType       = " + this.channelType + ",\n" +
          "    id                = " + this.id + ",\n" +
          "    name              = " + this.name + ",\n" +
          "    description       = " + this.description + ",\n" +
          "    latestMessageTime = " + this.latestMessageTime + ",\n" +
          "    userIds           = " + this.userIds + "\n" +
          "  }";
    }
  }

  public record ChannelResponseDtos(
      List<ChannelResponseDto> channelResponseDtosList
  ) {

    @Override
    public String toString() {
      if (channelResponseDtosList == null || channelResponseDtosList.isEmpty()) {
        return "Channels: []";
      }

      return "\n--- List of Channels ---" +
          channelResponseDtosList.stream()
              .map(ChannelResponseDto::toString) //
              .collect(Collectors.joining(","));
    }
  }

  public record PrivateChannelRequestDto(
      ChannelType type,
      List<UUID> userIds
  ) {

  }

  public record PublicChannelRequestDto(
      ChannelType type,
      String name,
      String description
  ) {

  }

  // only public channels are allowed to edit
  public record ChannelUpdateRequestDto(
//        UUID channelId,
      ChannelType type,
      String name,
      String description
  ) {

  }

  public record ChannelUpdateResponseDto(
      UUID channelId,
      ChannelType channelType,
      String name,
      String description
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    ChannelUpdateResponseDto {" + "\n" +
          "    channelType       = " + this.channelType + ",\n" +
          "    channelId         = " + this.channelId + ",\n" +
          "    name              = " + this.name + ",\n" +
          "    description       = " + this.description + ",\n" +
          "  }";
    }
  }

}

package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChannelDto {

  // Used for API SPEC
  public record PublicChannelCreateRequest(
      String name,
      String description
  ) {

  }

  public record ChannelResponse(
      UUID id,
      String name,
      String description,
      Instant createdAt,
      Instant updatedAt,
      ChannelType type

  ) {

  }

  // different schema when requesting all channels from user
  public record UserChannelResponse(
      UUID id,
      String name,
      String description,
      ChannelType type,
      Instant lastMessageAt,
      List<UUID> participantIds
  ) {

  }

  public record PrivateChannelCreateRequest(
      List<UUID> participantIds
  ) {

  }

  @Schema(description = "수정할 Channel 정보")
  public record PublicChannelUpdateRequest(
      String newName,
      String newDescription
  ) {

  }

  public record ChannelResponses(
      List<ChannelResponse> channelResponses
  ) {

    @Override
    public String toString() {
      if (channelResponses == null || channelResponses.isEmpty()) {
        return "Channels: []";
      }

      return "\n--- List of Channels ---" +
          channelResponses.stream()
              .map(ChannelResponse::toString) //
              .collect(Collectors.joining(","));
    }
  }

  // =================================================================

//  public record ChannelRequestDto(
//      Instant latestMessageTime,
//      ChannelType channelType,
//      String name, // null for private
//      String description, // null for private
//      List<UUID> userIds // null for public
//  ) {
//
//    @Override
//    public String toString() {
//      return "\n" +
//          "    ChannelResponseDto {" + "\n" +
//          "    channelType       = " + this.channelType + ",\n" +
//          "    name              = " + this.name + ",\n" +
//          "    description       = " + this.description + ",\n" +
//          "    latestMessageTime = " + this.latestMessageTime + ",\n" +
//          "    userIds           = " + this.userIds + "\n" +
//          "  }";
//    }
//  }

  /**
   * [PUBLIC] UUID channelId, ChannelType channelType, String name, String description, Instant
   * latestMessageTime,
   * <p>
   * [PRIVATE] UUID channelId, ChannelType channelType, Instant latestMessageTime, List<UUID>
   * userIds
   */

//  public record ChannelResponseDto(
//      UUID id,
//      Instant latestMessageTime,
//      ChannelType channelType,
//      String name, // null for private
//      String description, // null for private
//      List<UUID> userIds // null for public
//  ) {
//
//    @Override
//    public String toString() {
//      return "\n" +
//          "    ChannelResponseDto {" + "\n" +
//          "    channelType       = " + this.channelType + ",\n" +
//          "    id                = " + this.id + ",\n" +
//          "    name              = " + this.name + ",\n" +
//          "    description       = " + this.description + ",\n" +
//          "    latestMessageTime = " + this.latestMessageTime + ",\n" +
//          "    userIds           = " + this.userIds + "\n" +
//          "  }";
//    }
//  }

//  public record PrivateCreateChannelRequestDto(
//      List<UUID> participantIds
//  ) {
//
//  }
//
//  @Schema(description = "")
//  public record PublicCreateChannelRequestDto(
//      ChannelType type,
//      String name,
//      String description
//  ) {
//
//  }

  // only public channels are allowed to edit
//  public record ChannelUpdateRequestDto(

  /// /        UUID channelId,
//      ChannelType type,
//      String name,
//      String description
//  ) {
//
//  }

//  public record ChannelUpdateResponseDto(
//      UUID channelId,
//      ChannelType channelType,
//      String name,
//      String description
//  ) {
//
//    @Override
//    public String toString() {
//      return "\n" +
//          "    ChannelUpdateResponseDto {" + "\n" +
//          "    channelType       = " + this.channelType + ",\n" +
//          "    channelId         = " + this.channelId + ",\n" +
//          "    name              = " + this.name + ",\n" +
//          "    description       = " + this.description + ",\n" +
//          "  }";
//    }
//  }

}

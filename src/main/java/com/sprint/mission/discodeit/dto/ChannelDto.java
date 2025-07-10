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

  // schema when requesting all channels from user
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

}

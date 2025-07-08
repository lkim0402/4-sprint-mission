package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelDto.*;

import java.util.UUID;

public interface ChannelService {

  ChannelResponseDto createPublic(PublicChannelRequestDto channelRequestDto);

  ChannelResponseDto createPrivate(PrivateChannelRequestDto channelRequestDto);

  ChannelResponseDto find(UUID channelId);

  ChannelResponseDtos findAllByUserId(UUID userId);

  ChannelResponseDtos findAllPublicChannels();

  ChannelUpdateResponseDto update(UUID channelId, ChannelUpdateRequestDto channelUpdateRequestDto);

  void delete(UUID id);

  void deleteAll();
}

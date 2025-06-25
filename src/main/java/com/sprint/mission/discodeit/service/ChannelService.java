package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelService.*;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDtos;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.UUID;

public interface ChannelService {
    Channel createPublic(PublicChannelRequestDto channelRequestDto);
    Channel createPrivate(PrivateChannelRequestDto channelRequestDto);
    ChannelResponseDto find(UUID channelId);
    ChannelResponseDtos findAllByUserId(UUID userId);
    ChannelResponseDtos findAllPublicChannels();
    UpdateChannelResponseDto update(UpdateChannelRequestDto updateChannelRequestDto);
    void delete(Channel channel);
    void deleteAll();
}

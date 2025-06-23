package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelService.PrivateChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PublicChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.UpdateChannelRequestDto;
import com.sprint.mission.discodeit.dto.UserService.ChannelResponseDtos;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import org.apache.catalina.webresources.Cache;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
//    Channel create(ChannelType type, String name, String description);
//    Channel find(UUID channelId);
//    List<Channel> findAll();
//    Channel update(UUID channelId, String newName, String newDescription);
//    void delete(UUID channelId);


    Channel createPublic(PublicChannelRequestDto channelRequestDto);
    Channel createPrivate(PrivateChannelRequestDto channelRequestDto);
    ChannelResponseDto find(UUID channelId);
    ChannelResponseDtos findAllByUserId(UUID userId);
    Channel update(UpdateChannelRequestDto updateChannelRequestDto);
    void delete(Channel channel);
}

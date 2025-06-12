package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;


import java.util.*;

public class JCFMapChannelService implements ChannelService {

    public final Map<UUID, Channel> data;

    public JCFMapChannelService() {
        data = new HashMap<>();
    }

    @Override
    public Channel createChannel(String channelName) {
        Channel channel = new Channel(channelName);
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public void joinChannel(Channel channel, User user) {
        if (user.getUserStatus() != UserStatus.WITHDRAWN) {
            channel.addUser(user);
        }

    }

    @Override
    public void leaveChannel(Channel channel, User user) {
        channel.deleteUser(user);
    }

    @Override
    public Channel findById(UUID id) {
        if(!data.containsKey(id)) {
            return null;
        }

        return data.get(id);
    }


    @Override
    public Channel updateChannel(UUID channelId, String channelName) {
        // update in list
        if (!data.containsKey(channelId)) {
            return null;
        }

        // update in map
        Channel c = data.get(channelId);
        c.setChannelName(channelName);
        data.put(channelId, c);

        return c;
    }


    @Override
    public void deleteById(UUID id) {
        data.remove(id);

    }

    @Override
    public ArrayList<Channel> getChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void clearChannels() {
        data.clear();
    }
}

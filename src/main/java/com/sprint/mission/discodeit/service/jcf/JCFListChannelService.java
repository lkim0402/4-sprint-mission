package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;


import java.util.*;

public class JCFListChannelService implements ChannelService {

    public final ArrayList<Channel> data; // contains channels

    public JCFListChannelService() {
        data = new ArrayList<>();
    }


    @Override
    public Channel createChannel(String channelName) {
        if (channelName == null || channelName.isBlank())
        {
            throw new IllegalArgumentException("Channel name cannot be empty!");
        }

        Channel channel = new Channel(channelName);
        data.add(channel);
        return channel;
    }

    @Override
    public void joinChannel(Channel channel, User user) {
        if (user.getUserStatus() != (UserStatus.WITHDRAWN)) {
            channel.addUser(user);
        }
    }

    @Override
    public void leaveChannel(Channel channel, User user) {
        channel.deleteUser(user);

    }


    @Override
    public Channel getChannel(UUID id) {
        for (Channel c : data) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }


    @Override
    public Channel updateChannel(UUID channelId, String channelName) {
        // update in data (channel list)
        for (Channel c : data) {
            if (c.getId().equals(channelId)) {
                c.setChannelName(channelName);
                return c;
            }
        }
        return null;
    }


    @Override
    public boolean deleteChannel(UUID id) {
        Channel c = getChannel(id);
        if (c == null) return false;

        data.remove(c);
        return true;
    }


    @Override
    public List<Channel> getChannels()
    {
        return data;
    }


    @Override
    public void clearChannels() {
        data.clear();
    }
}

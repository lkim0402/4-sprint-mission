package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data; // ChannelId: Channel
    private final Map<String, Channel> nameIndex; // Channel name : Channel

    public JCFChannelRepository() {
        this.data = new HashMap<>();
        this.nameIndex = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        this.data.put(channel.getId(), channel);
        this.nameIndex.put(channel.getName(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public boolean existsByName(String channelName) {
        return this.nameIndex.containsKey(channelName);
    }

    @Override
    public void deleteById(UUID channelId) {
        Channel removedChannel = this.data.remove(channelId);

        if (removedChannel != null) {
            this.nameIndex.remove(removedChannel.getName());
        }
    }
}

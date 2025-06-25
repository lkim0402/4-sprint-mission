package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFChannelRepository implements ChannelRepository {

    /**
     * nameIndex: Channel name을 key로 사용하여 O(1) 시간에 채널을 조회합니다.
     * Key: String ChannelName, Value: UUID channelId
     * 채널 이름으로 조회할때 O(n)에서 O(1) 시간 복잡도로 줄일 수 있어서 구현했습니다.
     */
    private final Map<UUID, Channel> data; // ChannelId: Channel
    private final Map<String, UUID> nameIndex; // Channel name : Channel Id

    public JCFChannelRepository() {
        this.data = new HashMap<>();
        this.nameIndex = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        this.data.put(channel.getId(), channel);
        this.nameIndex.put(channel.getName(), channel.getId());
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

    @Override
    public void deleteAll() {
        this.data.clear();
        this.nameIndex.clear();
    }
}

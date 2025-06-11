package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel createChannel(String channelName) {
        if (channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("Channel name cannot be empty!");
        }

        Channel channel = new Channel(channelName);

        addChannel(channel);
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
        return channelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
    }


    @Override
    public Channel updateChannel(UUID channelId, String channelName) {
        // data access
        List<Channel> channels = getChannels();

        for (Channel c : channels) {
            if (channelId.equals(c.getId())) {
                c.setChannelName(channelName);
                break;
            }
        }

        // data access
        channelRepository.saveAll(channels);
        return getChannel(channelId);
    }


    @Override
    public void deleteChannel(UUID id) {
        List<Channel> channels = getChannels();
        channels.removeIf(c -> c.getId().equals(id));
        channelRepository.saveAll(channels);
    }


    @Override
    public List<Channel> getChannels() {
        return channelRepository.findAll();
    }


    @Override
    public void clearChannels() {
        List<Channel> channels = new ArrayList<>();
        channelRepository.saveAll(channels);
    }

    /**
     * 새로운 채널을 목록에 추가합니다.
     * 이미 존재하는 채널은 중복 추가되지 않습니다.
     *
     * @param channel 추가할 채널 객체
     */
    private void addChannel(Channel channel) {
        isExistChannel(channel);

        List<Channel> channels = getChannels();
        channels.add(channel);
        channelRepository.saveAll(channels);
    }

    private void isExistChannel(Channel channel) {
        List<Channel> channels = getChannels();

        boolean alreadyExist = channels.stream()
                .anyMatch(c -> c.getId().equals(channel.getId()));

        if (alreadyExist) {
            throw new RuntimeException(channel.getId() + " already exists");
        }
    }
}

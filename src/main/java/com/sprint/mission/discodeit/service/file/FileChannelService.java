package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public FileChannelService(ChannelRepository channelRepository) {
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
    public Channel getChannel(UUID id) {

        return channelRepository.findChannel(id)
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
    }


    @Override
    public Channel updateChannel(UUID channelId, String channelName) {

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
    public void joinChannel(Channel channel, User user) {
        List<Channel> channels = getChannels();

        for (Channel c : channels) {
            if (c.getId().equals(channel.getId())) {
                c.addUser(user);
                channelRepository.saveAll(channels);
                return;
            }
        }

    }

    @Override
    public void leaveChannel(Channel channel, User user) {
        List<Channel> channels = getChannels();

        for (Channel c : channels) {
            if (c.getId().equals(channel.getId())) {
                c.deleteUser(user);
                channelRepository.saveAll(channels);
                return;
            }
        }
    }


    @Override
    public void clearChannels() {
        List<Channel> channels = new ArrayList<>();
        channelRepository.saveAll(channels);
    }


    @Override
    public List<Channel> getChannels() {
        return channelRepository.findAll();

    }

    // utility methods
    /**
     * 새로운 채널을 목록에 추가합니다.
     * 이미 존재하는 채널은 중복 추가되지 않습니다.
     *
     * @param channel 추가할 채널 객체
     */
    private void addChannel(Channel channel) {
        List<Channel> channels = getChannels(); // data access

        boolean alreadyExist = channels.stream()
                .anyMatch(c -> c.getId().equals(channel.getId()));
        if (!alreadyExist) {
            channels.add(channel);
            channelRepository.saveAll(channels); // data access
        }
    }

}
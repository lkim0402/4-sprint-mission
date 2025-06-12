package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;


import java.util.*;

public class JCFListChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public JCFListChannelService(ChannelRepository channelRepository) {
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
        isValidActiveUser(user);
        channel.addUser(user);
        channelRepository.save(channel);
    }

    @Override
    public void leaveChannel(Channel channel, User user) {
        isValidActiveUser(user);
        channel.deleteUser(user);
        channelRepository.save(channel);
    }


    @Override
    public Channel findById(UUID id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
    }


    @Override
    public Channel updateChannel(UUID id, String channelName) {
        validateChannelExists(findById(id));

        Channel c = findById(id);
        c.setChannelName(channelName);
        channelRepository.save(c);
        return c;
    }


    @Override
    public void deleteById(UUID id) {
        channelRepository.deleteById(id);
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

    // =========== utility methods ===========
    /**
     * 새로운 채널을 목록에 추가합니다.
     * 이미 존재하는 채널은 중복 추가되지 않고 정보가 덮어씌워집니다.
     *
     * @param channel 추가할 채널 객체
     */
    private void addChannel(Channel channel) {
        channelRepository.save(channel);
    }

    /**
     * 지정된 채널이 시스템에 존재하는지 검증합니다.
     *
     * @param channel 존재 여부를 확인할 채널 객체
     * @throws RuntimeException 채널이 존재하지 않는 경우 예외를 발생시킵니다
     */
    private void validateChannelExists(Channel channel) {
        List<Channel> channels = getChannels();

        boolean alreadyExist = channels.stream()
                .anyMatch(c -> c.getId().equals(channel.getId()));

        if (!alreadyExist) {
            throw new RuntimeException("Channel " + channel.getId() + " does not exist");
        }
    }

    /**
     * 사용자가 유효한 활성 사용자인지 검증합니다.
     *
     * @param user 검증할 사용자 객체
     * @throws NoSuchElementException 다음 경우에 예외를 발생시킵니다:
     *         - user가 null인 경우
     *         - user의 상태가 null인 경우
     *         - user의 상태가 WITHDRAWN(탈퇴)인 경우
     */
    private void isValidActiveUser(User user) {
        if (user == null || user.getUserStatus() == null || user.getUserStatus() == UserStatus.WITHDRAWN) {
            throw new NoSuchElementException("Not a valid user");
        }
    }
}

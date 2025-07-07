//package com.sprint.mission.discodeit.service.file;
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.entity.UserStatus;
//import com.sprint.mission.discodeit.repository.ChannelRepository;
//import com.sprint.mission.discodeit.service.ChannelService;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//public class FileChannelService implements ChannelService {
//
//    private final ChannelRepository channelRepository;
//
//    public FileChannelService(ChannelRepository channelRepository) {
//        this.channelRepository = channelRepository;
//    }
//
//    @Override
//    public Channel createChannel(String channelName) {
//        if (channelName == null || channelName.isBlank()) {
//            throw new IllegalArgumentException("Channel name cannot be empty!");
//        }
//
//        Channel channel = new Channel(channelName);
//
//        channelRepository.save(channel);
//        return channel;
//    }
//
//    @Override
//    public void joinChannel(Channel channel, User user) {
//        findVerifiedChannel(channel.getId());
//        isValidActiveUser(user);
//
//        channel.addUser(user);
//        channelRepository.save(channel);
//    }
//
//    @Override
//    public void leaveChannel(Channel channel, User user) {
//        findVerifiedChannel(channel.getId());
//        isValidActiveUser(user);
//
//        channel.deleteUser(user);
//        channelRepository.save(channel);
//    }
//
//    @Override
//    public Channel findVerifiedChannel(UUID id) {
//        return channelRepository.findVerifiedChannel(id)
//                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
//    }
//
//    @Override
//    public Channel updateChannel(UUID id, String channelName) {
//        Channel channel = findVerifiedChannel(id);
//
//        channel.setChannelName(channelName);
//        channelRepository.save(channel);
//        return channel;
//    }
//
//    @Override
//    public void deleteChannel(UUID id) {
//        channelRepository.deleteChannel(id);
//    }
//
//    @Override
//    public List<Channel> getChannels() {
//        return channelRepository.findAll();
//    }
//
//    @Override
//    public void clearChannels() {
//        List<Channel> channels = new ArrayList<>();
//        channelRepository.saveAll(channels);
//    }
//
//    // =========== utility methods ===========
//
//    /**
//     * 사용자가 유효한 활성 사용자인지 검증합니다.
//     *
//     * @param user 검증할 사용자 객체
//     * @throws NoSuchElementException 다음 경우에 예외를 발생시킵니다:
//     *         - user가 null인 경우
//     *         - user의 상태가 null인 경우
//     *         - user의 상태가 WITHDRAWN(탈퇴)인 경우
//     */
//
//    private void isValidActiveUser(User user) {
//        if (user == null || user.getUserStatus() == null || user.getUserStatus() == UserStatus.WITHDRAWN) {
//            throw new NoSuchElementException("Not a valid user");
//        }
//    }
//}
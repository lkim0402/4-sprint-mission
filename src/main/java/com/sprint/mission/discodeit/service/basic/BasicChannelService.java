package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.ChannelService.*;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDtos;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelMapper channelMapper;

    @Override
    public ChannelResponseDto createPublic(PublicChannelRequestDto channelRequestDto) {

        if (channelRepository.existsByName(channelRequestDto.name())) {
            throw new IllegalArgumentException("Channel name already exists");
        }

        Channel channel = channelMapper.requestDtoToPublicChannel(channelRequestDto);

        return channelMapper.toChannelResponseDto(
                channelRepository.save(channel),
                null,
                null
        );
    }

    @Override
    public ChannelResponseDto createPrivate(PrivateChannelRequestDto channelRequestDto) {
        List<UUID> userIds = channelRequestDto.userIds();
        Channel channel = channelMapper.requestDtoToPrivateChannel(channelRequestDto);

        for  (UUID uuid : userIds) {
            ReadStatus readStatus = new ReadStatus(uuid, channel.getId());
            readStatusRepository.save(readStatus);
        }

        return channelMapper.toChannelResponseDto(
                channelRepository.save(channel),
                userIds,
                null
        );
    }

    @Override
    public ChannelResponseDto find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                        .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));

        // get all the ReadStatus from one channel and get the user ids from it
        List<UUID> userIds = null;
        if (channel.getType() == ChannelType.PRIVATE) {
            userIds = readStatusRepository.findByChannelId(channel.getId())
                    .stream()
                    .map(ReadStatus::getUserId)
                    .toList();
        }

        // find latest message in channel
        Instant latestMessageTime = messageRepository.findByChannelId(channel.getId())
                .stream()
                .map(BaseEntity::getCreatedAt)
                .max(Comparator.naturalOrder())
                .orElse(null);

        return channelMapper.toChannelResponseDto(channel, userIds, latestMessageTime);
    }

    @Override
    public ChannelResponseDtos findAllByUserId(UUID userId) {

        List<Channel> allChannels = channelRepository.findAll();

        List<ChannelResponseDto> channelResponseDtos = allChannels
                .stream()
                .filter(c -> {
                    // if current channel is private, then get only the private channels that includes userId
                    if (c.getType() == ChannelType.PRIVATE) {
                        Optional<ReadStatus> readStatus = readStatusRepository.findByChannelAndUserId(c.getId(), userId);
                        return readStatus.isPresent();

                    } else return c.getType() == ChannelType.PUBLIC;
                    }
                )
                .map(c -> {

                    // getting users in private channel
                    List<UUID> userIds = null;
                    if (c.getType() == ChannelType.PRIVATE) {
                        userIds = readStatusRepository.findByChannelId(c.getId())
                                .stream()
                                .map(ReadStatus::getUserId)
                                .toList();
                    }

                    // getting latest message in channel
                    Instant lastMessage = messageRepository.findByChannelId(c.getId())
                            .stream()
                            .map(BaseEntity::getCreatedAt)
                            .max(Instant::compareTo)
                            .orElse(null);

                    return channelMapper.toChannelResponseDto(c, userIds, lastMessage);

                })
                .toList();

        return channelMapper.toChannelResponseDtos(channelResponseDtos);
    }

    @Override
    public ChannelResponseDtos findAllPublicChannels() {
        return channelMapper.toChannelResponseDtos(channelRepository.findAll()
                .stream()
                .filter(c -> c.getType() == ChannelType.PUBLIC)
                .map(c -> channelMapper.toChannelResponseDto(c, null, messageRepository.findByChannelId(c.getId())
                        .stream()
                        .map(BaseEntity::getCreatedAt)
                        .max(Instant::compareTo)
                        .orElse(null)))
                .toList()
        );
    }
    @Override
    public UpdateChannelResponseDto update(UpdateChannelRequestDto updateChannelRequestDto) {

        if (updateChannelRequestDto.type() == ChannelType.PRIVATE) {
            throw new IllegalArgumentException("Private channel cannot be updated");
        }

        Channel channel = channelRepository.findById(updateChannelRequestDto.channelId())
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + updateChannelRequestDto.channelId() + " not found"));

        if (channelRepository.existsByName(updateChannelRequestDto.name())) {
            throw new IllegalArgumentException("Channel with name " + updateChannelRequestDto.name() + " already exists!");
        }

        channel.update(
                updateChannelRequestDto.name(),
                updateChannelRequestDto.description()
        );
        return channelMapper.toUpdateChannelResponseDto(
                channelRepository.save(channel)
        );
    }

    @Override
    public void delete(UUID id) {
        if (!channelRepository.existsById(id)) {
            throw new NoSuchElementException("Channel with id " + id + " not found");
        }
        channelRepository.deleteById(id);

        // deleting all messages
        List<Message> messageList = messageRepository.findByChannelId(id);
        for (Message message : messageList) {
            messageRepository.deleteById(message.getId());
        }

        // deleting all read statuses
        List<ReadStatus> readStatusList = readStatusRepository.findByChannelId(id);
        for (ReadStatus readStatus : readStatusList) {
            readStatusRepository.deleteById(readStatus.getId());
        }
    }

    @Override
    public void deleteAll() {
        channelRepository.deleteAll();
    }
}

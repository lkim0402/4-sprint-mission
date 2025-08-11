package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.ReadStatusDto;
import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.readStatus.ReadStatusAlreadyExists;
import com.sprint.mission.discodeit.exception.readStatus.ReadStatusNotFound;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final ReadStatusMapper readStatusMapper;

  @Transactional
  @Override
  public ReadStatusDto create(ReadStatusCreateRequest request) {
    UUID userId = request.userId();
    UUID channelId = request.channelId();

    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new UserNotFoundException(userId));
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new ChannelNotFoundException(channelId)
        );

    if (readStatusRepository.existsByUserIdAndChannelId(user.getId(), channel.getId())) {
      throw new ReadStatusAlreadyExists(userId, channelId);
    }

    Instant lastReadAt = request.lastReadAt();
    ReadStatus readStatus = new ReadStatus(user, channel, lastReadAt);
    readStatusRepository.save(readStatus);

    return readStatusMapper.toDto(readStatus);
  }

  @Override
  public ReadStatusDto find(UUID readStatusId) {
    return readStatusRepository.findById(readStatusId)
        .map(readStatusMapper::toDto)
        .orElseThrow(
            () -> new ReadStatusNotFound(readStatusId));
  }

  @Override
  public List<ReadStatusDto> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId).stream()
        .map(readStatusMapper::toDto)
        .toList();
  }

  @Transactional
  @Override
  public ReadStatusDto update(UUID readStatusId, ReadStatusUpdateRequest request) {
    Instant newLastReadAt = request.newLastReadAt();
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(
            () -> new ReadStatusNotFound(readStatusId));
    readStatus.update(newLastReadAt);
    return readStatusMapper.toDto(readStatus);
  }

  @Transactional
  @Override
  public void delete(UUID readStatusId) {
    if (!readStatusRepository.existsById(readStatusId)) {
      throw new ReadStatusNotFound(readStatusId);
    }
    readStatusRepository.deleteById(readStatusId);
  }
}

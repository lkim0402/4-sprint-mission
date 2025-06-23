package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDtos;
import com.sprint.mission.discodeit.dto.ReadStatusService.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.Mapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public ReadStatus create(ReadStatusRequestDto readStatusRequestDto) {

        // Throw error if channel or user does not exist
        if (!channelRepository.existsById(readStatusRequestDto.channelId())) {
            throw new IllegalArgumentException("Channel not found");
        }
        if (!userRepository.existsById(readStatusRequestDto.userId())) {
            throw new IllegalArgumentException("User not found");
        }

        // Throw error if readstatus already exists
        Optional<ReadStatus> readStatus = readStatusRepository.findByChannelAndUserId(
                readStatusRequestDto.channelId(), readStatusRequestDto.userId()
        );
        if (readStatus.isPresent()) {
            throw new IllegalArgumentException("Read status already exists");
        }

        ReadStatus newReadStatus = mapper.toReadStatus(readStatusRequestDto);
        return readStatusRepository.save(newReadStatus);
    }

    @Override
    public ReadStatusResponseDto find(UUID id) {
        return readStatusRepository.findById(id)
                .map(mapper::toReadStatusDto)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    @Override
    public ReadStatusResponseDtos findAllByUserId(UUID id) {
        return mapper.toReadStatusResponseDtos(readStatusRepository.findByUserId(id));
    }

    @Override
    // UpdateReadStatusDto updateReadStatusDto 사용 안함
    public ReadStatus update(UUID readStatusId) {

        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
            .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + readStatusId + " not found"));

        readStatus.updateLastReadAt();
        return readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID id) {
        if (!readStatusRepository.existsById(id)) {
            throw new NoSuchElementException("ReadStatus with id " + id + " not found");
        }
        readStatusRepository.deleteById(id);
    }
}

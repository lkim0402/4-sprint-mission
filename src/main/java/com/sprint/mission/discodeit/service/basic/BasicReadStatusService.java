package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.ReadStatusService.*;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ReadStatusMapper readStatusMapper;

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

        ReadStatus newReadStatus = readStatusMapper.toReadStatus(readStatusRequestDto);
        return readStatusRepository.save(newReadStatus);
    }

    @Override
    public ReadStatusResponseDto find(UUID id) {
        return readStatusRepository.findById(id)
                .map(readStatusMapper::toReadStatusDto)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    @Override
    public ReadStatusResponseDtos findAllByUserId(UUID id) {
        return readStatusMapper.toReadStatusResponseDtos(readStatusRepository.findByUserId(id));
    }

    /**
     * 지정된 ID의 ReadStatus를 찾아 마지막 읽은 시간을 현재 시간으로 갱신합니다.
     * 이 메소드는 클라이언트로부터 별도의 데이터를 입력받지 않고,
     * 오직 {@code readStatusId}를 통해 대상 엔티티를 식별하여 서버 시간 기준으로 업데이트를 수행합니다.
     * 따라서 요청 본문(Request Body) 필요 없으므로 별도의 요청 DTO를 사용하지 않습니다.
     */
    @Override
    public void update(UUID readStatusId) {

        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
            .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + readStatusId + " not found"));

        readStatus.updateLastReadAt();
        readStatusRepository.save(readStatus);
    }

    @Override
    public void delete(UUID id) {
        if (!readStatusRepository.existsById(id)) {
            throw new NoSuchElementException("ReadStatus with id " + id + " not found");
        }
        readStatusRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        readStatusRepository.deleteAll();
    }
}

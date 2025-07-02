package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDtos;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final UserStatusMapper userStatusMapper;

    @Override
    public UserStatusResponseDto create(UserStatusRequestDto userStatusRequestDto) {
        // Throw error if user not found
        if (!userRepository.existsById(userStatusRequestDto.userId())) {
            throw new IllegalArgumentException("User not found");
        }

        // Throw error if userstatus already exists
        userStatusRepository.findByUserId(userStatusRequestDto.userId())
                .ifPresent(s -> {
                    throw new IllegalArgumentException("User status already exists for user with ID: " + s.getUserId());
                });

        UserStatus newUserStatus = userStatusMapper.toUserStatus(userStatusRequestDto);
        return userStatusMapper.toUserStatusResponseDto(userStatusRepository.save(newUserStatus));
    }

    @Override
    public UserStatusResponseDto find(UUID userStatusId) {
        return userStatusRepository.findById(userStatusId)
                .map(userStatusMapper::toUserStatusResponseDto)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));
    }

    @Override
    public UserStatusResponseDto findByUserId(UUID userId) {
        return userStatusRepository.findByUserId(userId)
                .map(userStatusMapper::toUserStatusResponseDto)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with user id " + userId + " not found"));
    }

    @Override
    public UserStatusResponseDtos findAll() {
        return userStatusMapper.toUserStatusResponseDtos(userStatusRepository.findAll());
    }

    /**
     * 사용자의 마지막 활동 시간을 현재 시간으로 갱신만 하고 별도의 응답 데이터를 반환하지 않습니다 (void).
     */
    @Override
    // updating timestamp
    public void update(UUID userId) {
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(
                        "userStatus with id " + userId + " not found"));

        userStatus.updateLastActiveTime();
        userStatusRepository.save(userStatus);
    }

    @Override
    public void delete(UUID id) {
        if (!userStatusRepository.existsById(id)) {
            throw new NoSuchElementException("UserStatus with id " + id + " not found");
        }
        userStatusRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userStatusRepository.deleteAll();
    }
}

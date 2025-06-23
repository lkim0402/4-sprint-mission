package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDtos;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.Mapper;
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
    private final Mapper mapper;

    @Override
    public UserStatus create(UserStatusRequestDto userStatusRequestDto) {
        // Throw error if user not found
        if (!userRepository.existsById(userStatusRequestDto.userId())) {
            throw new IllegalArgumentException("User not found");
        }

        // Throw error if userstatus already exists
        userStatusRepository.findByUserId(userStatusRequestDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User status already exists"));

        UserStatus newUserStatus = mapper.toUserStatus(userStatusRequestDto);
        return userStatusRepository.save(newUserStatus);
    }

    @Override
    public UserStatusResponseDto find(UUID id) {
        return userStatusRepository.findById(id)
                .map(mapper::toUserStatusResponseDto)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + id + " not found"));
    }

    @Override
    public UserStatusResponseDtos findAll() {
        return mapper.toUserStatusResponseDtos(userStatusRepository.findAll());
    }

    @Override
    // updating timestamp
    public UserStatus update(UpdateUserStatusDto updateUserStatusDto) {
        UserStatus userStatus = userStatusRepository.findById(updateUserStatusDto.userId())
                .orElseThrow(() -> new NoSuchElementException(
                        "userStatus with id " + updateUserStatusDto.userId() + " not found"));

        userStatus.updateLastActiveTime();
        return userStatusRepository.save(userStatus);

    }

    @Override
    public void delete(UUID id) {
        if (!userStatusRepository.existsById(id)) {
            throw new NoSuchElementException("UserStatus with id " + id + " not found");
        }
        userStatusRepository.deleteById(id);
    }
}

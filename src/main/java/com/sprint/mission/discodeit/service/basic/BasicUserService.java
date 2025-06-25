package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserMapper userMapper;
    private final BinaryContentMapper binaryContentMapper;

    @Override
    public User create(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        if (existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new IllegalStateException("User with the same username or email already exists.");
        }
        // Save user
        User savedUser =  userRepository.save(user);

        //Save profile in binaryContentRepository
        BinaryContent profile = binaryContentMapper.toBinaryContent(userRequestDto.profilePicture());
        if (profile != null) {
            // setting user's profile id
            savedUser.setProfileId(profile.getId());
            // setting profile's user id
            profile.setUserId(savedUser.getId());
            binaryContentRepository.save(profile);
        }

        // Save userStatus in userStatusRepository
        UserStatus userStatus = new UserStatus(savedUser.getId());
        userStatusRepository.save(userStatus);

        return savedUser;
    }

    @Override
    public UserResponseDto find(UUID userId) {

        // find the userStatus
        Optional<UserStatus> userStatus = userStatusRepository.findByUserId(userId);

        return userRepository.findById(userId)
                .map(u -> userMapper.toUserResponseDto(u, userStatus))
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

    @Override
    public UserResponseDtos findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new UserResponseDtos(Collections.emptyList());
        }

        List<UserResponseDto> userList =  users.stream()
                .map(u -> {
                    Optional<UserStatus> userStatus = userStatusRepository.findByUserId(u.getId());
                    return userMapper.toUserResponseDto(u, userStatus);
                })
                .toList();

        return userMapper.toUserResponseDtos(userList);
    }

    @Override
    public UpdateUserResponseDto update(UpdateUserRequestDto updateUserRequestDto) {

        User existingUser = userRepository.findById(updateUserRequestDto.userId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + updateUserRequestDto.userId() + " not found"));

        existingUser.update(
                updateUserRequestDto.username(),
                updateUserRequestDto.email(),
                updateUserRequestDto.password(),
                updateUserRequestDto.profileId()
        );

        return userMapper.toUpdateUserResponseDto(userRepository.save(existingUser));
    }

    @Override
    public void delete(User user) {
        UUID id = user.getId();
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with id " + id+ " not found");
        }
        userRepository.deleteById(id);

        // deleting in userstatus
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("UserStatus for user id " + user.getId() + " does not exist!"));
        userStatusRepository.deleteById(userStatus.getId());

        // deleting in binarycontent
        List<BinaryContent> binaryContentList = binaryContentRepository.findByUserId(user.getId());
        for (BinaryContent binaryContent : binaryContentList){
            binaryContentRepository.deleteById(binaryContent.getId());
        }
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    private boolean existsByUsernameOrEmail(String username, String email) {
        List<User> users = userRepository.findAll();

        List<User> filtered = users.stream().
                filter(u -> Objects.equals(u.getUsername(), username) || Objects.equals(u.getEmail(), email))
                .toList();

        return !filtered.isEmpty();
    }

}

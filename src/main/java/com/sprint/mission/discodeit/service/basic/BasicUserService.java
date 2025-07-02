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
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        if (existsByUsernameOrEmail(userRequestDto.getUsername(), userRequestDto.getEmail())) {
            throw new IllegalStateException("User with the same username or email already exists.");
        }
        // Save user
        User savedUser =  userRepository.save(user);

        //Save profile in binaryContentRepository
        BinaryContent profile = binaryContentMapper.toBinaryContent(savedUser.getId(), null, userRequestDto.getProfilePicture());
        if (profile != null) {
            // setting user's profile id
            savedUser.setProfileId(profile.getId());
            userRepository.save(savedUser);

            // setting profile's user id
            profile.setUserId(savedUser.getId());
            binaryContentRepository.save(profile);
        }

        // Save userStatus in userStatusRepository
        UserStatus userStatus = new UserStatus(savedUser.getId());
        userStatusRepository.save(userStatus);

        return userMapper.toUserResponseDto(savedUser, userStatus);
    }

    @Override
    public UserResponseDto find(UUID userId) {

        // find the userStatus
        Optional<UserStatus> userStatus = userStatusRepository.findByUserId(userId);

        return userRepository.findById(userId)
                .map(u -> userMapper.toUserResponseDto(u, userStatus))
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

//    @Override
//    public UserResponseDtos findAll() {
//        List<User> users = userRepository.findAll();
//        if (users.isEmpty()) {
//            return new UserResponseDtos(Collections.emptyList());
//        }
//
//        List<UserResponseDto> userList =  users.stream()
//                .map(u -> {
//                    Optional<UserStatus> userStatus = userStatusRepository.findByUserId(u.getId());
//                    return userMapper.toUserResponseDto(u, userStatus);
//                })
//                .toList();
//
//        return userMapper.toUserResponseDtos(userList);
//    }

    // 심화 요구사항에 맞춰서 findAll 변경
    @Override
    public UserDtos findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new UserDtos(Collections.emptyList());
        }

        List<UserDto> userList =  users.stream()
                .map(u -> {
                    UserStatus userStatus = userStatusRepository.findByUserId(u.getId())
                            .orElseThrow(() -> new NoSuchElementException("UserStatus does not exist for user id " + u.getId()));
                    return userMapper.toUserDto(u, userStatus);
                })
                .toList();

        return userMapper.toUserDtos(userList);
    }

    @Override
    public UpdateUserResponseDto update(UUID userId, UpdateUserRequestDto updateUserRequestDto) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        if (updateUserRequestDto.username() != null) {
            existingUser.setUsername(updateUserRequestDto.username());
        }
        if (updateUserRequestDto.email() != null) {
            existingUser.setEmail(updateUserRequestDto.email());
        }
        if (updateUserRequestDto.password() != null) {
            existingUser.setPassword(updateUserRequestDto.password());
        }
        if (updateUserRequestDto.profileId() != null) {
            existingUser.setProfileId(updateUserRequestDto.profileId());
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUpdateUserResponseDto(updatedUser);
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with id " + id+ " not found");
        }
        userRepository.deleteById(id);

        // deleting in userstatus
        UserStatus userStatus = userStatusRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchElementException("UserStatus for user id " + id + " does not exist!"));
        userStatusRepository.deleteById(userStatus.getId());

        // deleting in binarycontent
        List<BinaryContent> binaryContentList = binaryContentRepository.findByUserId(id);
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

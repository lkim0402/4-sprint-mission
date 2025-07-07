package com.sprint.mission.discodeit.mapper;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {

    private final UserStatusMapper userStatusMapper;

    // Request
    public User toUser(UserRequestDto userDTO) {
        return new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                null // set later when creating user
        );
    }

    // Response
    public UserResponseDto toUserResponseDto(User user, Optional<UserStatus> optionalUserStatus) {
        UserStatus userStatus = optionalUserStatus.orElse(null);

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                userStatus == null ? null : userStatusMapper.toUserStatusResponseDto(userStatus)
        );
    }


    // used in create
    public UserResponseDto toUserResponseDto(User user, UserStatus userStatus) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                userStatusMapper.toUserStatusResponseDto(userStatus)
        );
    }

    // Response
    public UserResponseDtos toUserResponseDtos(List<UserResponseDto> userResponseDtos) {
        return new UserResponseDtos(
                userResponseDtos
        );
    }

    public UpdateUserResponseDto toUpdateUserResponseDto(User user) {
        return new UpdateUserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId()
        );
    }

    // ======== 심화 =========

    public UserDto toUserDto(User user, UserStatus userStatus) {
        Boolean isOnline = userStatus.getStatus() == UserStatus.UserState.ONLINE;

        return new UserDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                isOnline
        );
    }

    public List<UserDto> toUserDtoList(UserDtos userDtos) {
        return userDtos.userDtoList();
    }

    public UserDtos toUserDtos(List<UserDto> userDtoList) {
        return new UserDtos(
                userDtoList
        );
    }
}

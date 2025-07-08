package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDto.*;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {

  private final UserStatusMapper userStatusMapper;

  // Request
  public User toUser(UserCreateRequestDto userDTO) {
    return new User(
        userDTO.getUsername(),
        userDTO.getEmail(),
        userDTO.getPassword(),
        null // set later when creating user
    );
  }

  // Response
  public UserCreateResponseDto toUserResponseDto(User user,
      Optional<UserStatus> optionalUserStatus) {
    UserStatus userStatus = optionalUserStatus.orElse(null);

    return new UserCreateResponseDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        userStatus == null ? null : userStatusMapper.toUserStatusResponseDto(userStatus)
    );
  }


  // used in create
  public UserCreateResponseDto toUserResponseDto(User user, UserStatus userStatus) {
    return new UserCreateResponseDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        userStatusMapper.toUserStatusResponseDto(userStatus)
    );
  }

  public UserUpdateResponseDto toUpdateUserResponseDto(User user) {
    return new UserUpdateResponseDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId()
    );
  }

  // ======== 심화 =========

  public UserGetDto toUserDto(User user, UserStatus userStatus) {
    Boolean isOnline = userStatus.getStatus() == UserStatus.UserState.ONLINE;

    return new UserGetDto(
        user.getId(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        isOnline
    );
  }
}

package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDto.*;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.UserStatus.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {

  private final UserStatusMapper userStatusMapper;

//  // Request
//  public User toUser(UserCreateRequestDto userDTO) {
//    return new User(
//        userDTO.username(),
//        userDTO.email(),
//        userDTO.password(),
//        null // set later when creating user
//    );
//  }

  // Response
  public UserResponse toUserResponseDto(User user,
      Optional<UserStatus> optionalUserStatus) {
    UserStatus userStatus = optionalUserStatus.orElse(null);

    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        userStatus == null ? null : userStatusMapper.toUserStatusResponseDto(userStatus)
    );
  }

  public AllUserGetDto toAllUserGetDto(User user, UserStatus userStatus) {
    return new AllUserGetDto(
        user.getId(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        userStatus.getStatus() == UserState.ONLINE
    );
  }

  // used in create
  public UserResponse toUserResponseDto(User user, UserStatus userStatus) {
    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        userStatusMapper.toUserStatusResponseDto(userStatus)
    );
  }

  public UserUpdateResponse toUpdateUserResponseDto(User user) {
    return new UserUpdateResponse(
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        user.getId(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getPassword()
    );
  }

  // ======== 심화 =========

  public UserGetDto toUserGetDto(User user, UserStatus userStatus) {
    Boolean isOnline = userStatus.getStatus() == UserStatus.UserState.ONLINE;

    return new UserGetDto(
        user.getId(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(), // API spec
        user.getProfileId()
//        isOnline // API spec
    );
  }
}

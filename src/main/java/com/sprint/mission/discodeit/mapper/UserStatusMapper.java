package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusDto.*;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserStatusMapper {

  // Request
  public UserStatus toUserStatus(UserStatusRequestDto userStatusRequestDto) {
    return new UserStatus(userStatusRequestDto.userId());
  }

  // Response
  public UserStatusResponseDto toUserStatusResponseDto(UserStatus userStatus) {
    return new UserStatusResponseDto(
        userStatus.getId(),
        userStatus.getUserId(),
        userStatus.getLastActiveTime(),
        userStatus.getStatus()
    );
  }

  // Response
  public UserStatusResponseDtos toUserStatusResponseDtos(List<UserStatus> userStatusResponseDtos) {
    return new UserStatusResponseDtos(
        userStatusResponseDtos.stream()
            .map(this::toUserStatusResponseDto)
            .toList()
    );
  }

}

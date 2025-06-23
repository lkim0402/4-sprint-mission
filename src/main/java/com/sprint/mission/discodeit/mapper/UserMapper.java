package com.sprint.mission.discodeit.mapper;
import com.sprint.mission.discodeit.dto.UserService.UpdateUserResponseDto;
import com.sprint.mission.discodeit.dto.UserService.UserRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserService.UserResponseDtos;
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
                userDTO.username(),
                userDTO.email(),
                userDTO.password()
        );
    }

    // Response
    public UserResponseDto toUserResponseDto(User user, Optional<UserStatus> optionalUserStatus) {
        UserStatus userStatus = optionalUserStatus.orElse(null);

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
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
}

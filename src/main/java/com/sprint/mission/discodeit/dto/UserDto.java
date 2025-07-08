package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class UserDto {

  @Getter
  @Setter
  @AllArgsConstructor
  public class UserCreateRequestDto {

    private String username;
    private String email;
    private String password;
    private MultipartFile profilePicture;
  }

  public record UserCreateResponseDto(
      UUID id,
      String username,
      String email,
      UUID profileId,
      UserStatusResponseDto userStatusResponseDto
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    UserResponseDto {" + "\n" +
          "    id                    = " + this.id + ",\n" +
          "    username              = " + this.username + ",\n" +
          "    email                 = " + this.email + ",\n" +
          "    profileId             = " + this.profileId + ",\n" +
          "    userStatusResponseDto = " + this.userStatusResponseDto + "\n" +
          "  }";
    }
  }

  public record UserCreateResponseDtos(
      List<UserCreateResponseDto> userCreateResponseDtoList
  ) {

    @Override
    public String toString() {
      if (userCreateResponseDtoList == null || userCreateResponseDtoList.isEmpty()) {
        return "Users: []";
      }

      // use a stream to call the toString() on each item in the list
      // then join them together with a "," and a newline
      return "\n--- List of Users ---" +
          userCreateResponseDtoList.stream()
              .map(UserCreateResponseDto::toString) //
              .collect(Collectors.joining(","));
    }
  }

  public record UserGetDto(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      String username,
      String email,
      UUID profileId,
      Boolean online
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    UserDto    {" + "\n" +
          "    userId     = " + this.id + ",\n" +
          "    createdAt  = " + this.createdAt + ",\n" +
          "    updatedAt  = " + this.updatedAt + ",\n" +
          "    username   = " + this.username + ",\n" +
          "    email      = " + this.email + ",\n" +
          "    profileId  = " + this.profileId + ",\n" +
          "    online?    = " + this.profileId + ",\n" +
          "  }";
    }
  }

  public record UserGetDtos(
      List<UserGetDto> userGetDtoList
  ) {

    @Override
    public String toString() {
      if (userGetDtoList == null || userGetDtoList.isEmpty()) {
        return "Users: []";
      }

      return "\n--- List of Users ---" +
          userGetDtoList.stream()
              .map(UserGetDto::toString) //
              .collect(Collectors.joining(","));
    }
  }

  public record UserUpdateRequestDto(
//    UUID userId,
      String username,
      String password,
      String email,
      UUID profileId
  ) {

  }

  public record UserUpdateResponseDto(
      UUID userId,
      String username,
      String email,
      UUID profileId
  ) {

    @Override
    public String toString() {
      return "\n" +
          "    UpdateUserResponseDto {" + "\n" +
          "    userId                = " + this.userId + ",\n" +
          "    username              = " + this.username + ",\n" +
          "    email                 = " + this.email + ",\n" +
          "    profileId             = " + this.profileId + ",\n" +
          "  }";
    }
  }

}

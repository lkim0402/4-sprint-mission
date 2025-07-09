package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.dto.UserStatusDto.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public class UserDto {

  // ============================== POST - 유저 등록 ==============================
  public record UserCreateRequest(
      String username,
      String email,
      String password
//      MultipartFile profilePicture
  ) {

  }

  public record UserResponse(
      UUID id,
      String username,
      String email,
      UUID profileId,
      UserStatusResponse userStatusResponse
  ) {

  }

  // telling Swagger that UserGetDto = User schema
  // used in POST response (creation)
  @Schema(name = "User")
  public record UserGetDto(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      String username,
      String email,
      String password, // API specifications
      UUID profileId
//      Boolean online // removing for API specifications
  ) {

  }

  // ============================== GET - 전체 유저 조회 ==============================
  public record AllUserGetDto(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      String username,
      String email,
      UUID profileId,
      Boolean online
  ) {

  }

  // ============================== PATCH - 유저 수정 ==============================
  @Schema(description = "수정할 User 정보")
  public record UserUpdateRequest(
//    UUID userId,
      String newUsername,
      String newPassword,
      String newEmail
  ) {

  }

  public record UserUpdateResponse(
      String username,
      String email,
      UUID profileId,
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      String password
  ) {

  }


}

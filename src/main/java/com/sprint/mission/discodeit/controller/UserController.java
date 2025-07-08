package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDto.*;
import com.sprint.mission.discodeit.dto.UserStatusDto.*;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User 관련 API")
public class UserController {

  private final UserService userService;
  private final UserStatusService userStatusService;

  @Operation(summary = "User 생성")
  @PostMapping
  public ResponseEntity<UserCreateResponseDto> createUser(
      @ModelAttribute UserCreateRequestDto userCreateRequestDto
  ) {
    UserCreateResponseDto user = userService.create(userCreateRequestDto);
    return ResponseEntity.ok().body(user);
  }

  @Operation(summary = "User 수정")
  @PatchMapping("/{user-id}")
  public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("user-id") UUID userId,
      @RequestBody UserUpdateRequestDto userUpdateRequestDto
  ) {
    UserUpdateResponseDto userUpdateResponseDto = userService.update(userId, userUpdateRequestDto);
    return ResponseEntity.ok(userUpdateResponseDto);
  }

  @Operation(summary = "User 삭제")
  @DeleteMapping("/{user-id}")
  public ResponseEntity<String> deleteMember(@PathVariable("user-id") UUID userId) {
    userService.delete(userId);
    return ResponseEntity.ok().body("Member deleted successfully");
  }

  @Operation(summary = "UserStatus 업데이트")
  @PatchMapping("/{user-id}/status")
  public ResponseEntity<UserStatusResponseDto> updateUserStatus(@PathVariable("user-id") UUID userId
  ) {
    UserStatusResponseDto userStatusResponseDto = userStatusService.updateByUserId(userId);
    return ResponseEntity.ok(userStatusResponseDto);
  }

  @Operation(summary = "모든 User 조회")
  @GetMapping("/findAll")
  public ResponseEntity<List<UserGetDto>> getUsers() {
    List<UserGetDto> userGetDtos = userService.findAll();
    return ResponseEntity.ok(userGetDtos);
  }
}

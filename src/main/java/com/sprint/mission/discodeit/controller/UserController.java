package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDto.*;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
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
  private final UserMapper userMapper;

  @PostMapping // 등록
  public ResponseEntity<UserCreateResponseDto> createUser(
      @ModelAttribute UserCreateRequestDto userCreateRequestDto
  ) {
    UserCreateResponseDto user = userService.create(userCreateRequestDto);
    return ResponseEntity.ok().body(user);
  }

  @PatchMapping("/{user-id}")// 수정
  public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("user-id") UUID userId,
      @RequestBody UserUpdateRequestDto userUpdateRequestDto
  ) {
    UserUpdateResponseDto userUpdateResponseDto = userService.update(userId, userUpdateRequestDto);
    return ResponseEntity.ok(userUpdateResponseDto);
  }

  @DeleteMapping("/{user-id}") // 유저 삭제
  public ResponseEntity<String> deleteMember(@PathVariable("user-id") UUID userId) {
    userService.delete(userId);
    return ResponseEntity.ok().body("Member deleted successfully");
  }


  @PatchMapping("/{user-id}/status") // 유저 상태 업데이트
  public ResponseEntity<UserStatusResponseDto> updateUserStatus(@PathVariable("user-id") UUID userId
  ) {
    UserStatusResponseDto userStatusResponseDto = userStatusService.updateByUserId(userId);
    return ResponseEntity.ok(userStatusResponseDto);
  }

  @GetMapping("/findAll") // 모든 사용자 조회
  public ResponseEntity<List<UserGetDto>> getUsers() {
    UserGetDtos userGetDtos = userService.findAll();
    return ResponseEntity.ok(userMapper.toUserDtoList(userGetDtos));
  }
}

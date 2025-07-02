package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    /**
     * [x] 사용자를 등록할 수 있다.
     * [x] 사용자 정보를 수정할 수 있다.
     * [x] 사용자를 삭제할 수 있다.
     * [x] 모든 사용자를 조회할 수 있다.
     * [x] 사용자의 온라인 상태를 업데이트할 수 있다.
     */

    @PostMapping // 등록
    public ResponseEntity<UserResponseDto> createUser(@ModelAttribute UserRequestDto userRequestDto
    ) {
        UserResponseDto user = userService.create(userRequestDto);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping ("/{user-id}")// 수정
    public ResponseEntity<UpdateUserResponseDto> updateUser(@PathVariable("user-id") UUID userId,
                                                            @RequestBody UpdateUserRequestDto updateUserRequestDto
        ) {
        UpdateUserResponseDto updateUserResponseDto = userService.update(userId, updateUserRequestDto);
        return ResponseEntity.ok(updateUserResponseDto);
    }

    @DeleteMapping("/{user-id}") // 유저 삭제
    public ResponseEntity<String> deleteMember(@PathVariable("user-id") UUID userId) {
        userService.delete(userId);
        return ResponseEntity.ok().body("Member deleted successfully");
    }

    @GetMapping // 모든 사용자 조회
    public ResponseEntity<UserResponseDtos> getMembers() {
        UserResponseDtos userResponseDtos = userService.findAll();
        return ResponseEntity.ok(userResponseDtos);
    }

    @PatchMapping("/{user-id}/status") // 유저 상태 업데이트
    public ResponseEntity<String>  updateUserStatus(@PathVariable("user-id") UUID userId
    ) {
        userStatusService.update(userId);
        return ResponseEntity.ok().body("UserStatus updated successfully");
    }
}

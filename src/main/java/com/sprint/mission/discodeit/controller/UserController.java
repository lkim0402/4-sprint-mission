package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PatchMapping("/{user-id}/status") // 유저 상태 업데이트
    public ResponseEntity<UserStatusResponseDto>  updateUserStatus(@PathVariable("user-id") UUID userId
    ) {
        UserStatusResponseDto userStatusResponseDto = userStatusService.updateByUserId(userId);
        return ResponseEntity.ok(userStatusResponseDto);
    }

    @GetMapping("/findAll") // 모든 사용자 조회
    public ResponseEntity<List<UserDto>> getUsers() {
        UserDtos userDtos = userService.findAll();
        return ResponseEntity.ok(userMapper.toUserDtoList(userDtos));
    }
}

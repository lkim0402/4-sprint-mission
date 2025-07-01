package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * [x] 사용자는 로그인할 수 있다.
     */

    @PostMapping // 로그인
    public ResponseEntity<UserLoginResponseDto> loginUser(
            @RequestBody UserLoginRequestDto userLoginRequestDto
    ) {
        UserLoginResponseDto userLoginResponseDto = authService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }
}

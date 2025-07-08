package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.AuthService.LoginRequest;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @RequestMapping(path = "login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody LoginRequest loginRequest
    ) {
        UserLoginResponseDto userLoginResponseDto = authService.login(loginRequest);
        return ResponseEntity.ok(userLoginResponseDto);
    }
}

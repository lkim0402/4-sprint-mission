package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.AuthDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 관련 API")
public class AuthController {

  private final AuthService authService;

  // ============================== POST - 유저 로그인 ==============================
  @Operation(summary = "로그인", operationId = "login")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그인 성공",
          content = @Content(mediaType = "*/*",
//              schema = @Schema(implementation = AuthDto.LoginResponse.class))
              schema = @Schema(implementation = UserDto.UserGetDto.class))

      ),
      @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "User with username {username} not found"))
      ),
      @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "Wrong password"))
      )
  })
  @PostMapping("/login")
  public ResponseEntity<UserDto.UserGetDto> loginUser(
      @RequestBody AuthDto.LoginRequest loginRequest
  ) {
//    AuthDto.LoginResponse loginResponse = authService.login(loginRequest);
    UserDto.UserGetDto loginResponse = authService.login(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }
}

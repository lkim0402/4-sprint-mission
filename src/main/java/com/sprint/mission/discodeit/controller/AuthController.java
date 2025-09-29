package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.AuthApi;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

  private final AuthService authService;

  @PostMapping(path = "login")
  public ResponseEntity<UserDto> login(@RequestBody @Valid LoginRequest loginRequest) {
    log.info("로그인 요청: username={}", loginRequest.username());
    UserDto user = authService.login(loginRequest);
    log.debug("로그인 응답: {}", user);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(user);
  }

  // where client first calls to get the initial CSRF token
  @GetMapping("csrf-token")
  public ResponseEntity<Void> getCsrToken(CsrfToken csrfToken) {
    // this method creates token in CookieCsrfTokenRepository
    // when the response is sent back, the HTTP response header includes Set-Cookie + created token
    // then client saves that cookie (that includes the token)
    String tokenValue = csrfToken.getToken();
    log.debug("CSRF 토큰 요청: {}", tokenValue);
    return ResponseEntity
        .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
        .build();
  }
  // In future requests, the token is sent back to the server in 2 places simultaneously
  // - Cookie (Automatic): The browser automatically includes the XSRF-TOKEN cookie with every request because it's stored for that domain
  // - Header (Manual): Frontend JS code reads the value from the XSRF-TOKEN cookie and *manually* adds it to a request header named X-XSRF-TOKEN
  //   - possible coz cookie was set with HttpOnly=false
  // The server simply checks if the token in cookie matches the token in the header
}

package com.sprint.mission.discodeit.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.auth.jwt.JwtTokenProvider;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

// 로그인 인증을 담당하는 Security Filter(JwtAuthenticationFilter)가 클라이언트의 로그인 인증 정보 수신
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  // 로그인 인증 정보(Username/Password)를 전달받아 UserDetailsService와 인터랙션 한 뒤 인증 여부를 판단
  private final AuthenticationManager authenticationManager;

  // 클라이언트가 인증에 성공할 경우, JWT를 생성 및 발급
  private final JwtTokenProvider jwtTokenProvider;

  // 1) 받은 인증정보를 AuthenticationManager에게 전달해 인증처리 위임
  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    ObjectMapper objectMapper = new ObjectMapper();
    LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
        LoginRequest.class);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginRequest.username(),
        loginRequest.password()
    );

    // DiscodeitUserDetailsService에게 사용자의 UserDetails 조회를 위임하고 사용자의 크레덴셜을 조회하고,
    // AuthenticationManager에게 사용자의 UserDetails를 전달 (loadUserByUsername())
    return authenticationManager.authenticate(authenticationToken);
  }

  // 2) 인증이 성공될때 호출됨
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {

    // AuthenticationManager 내부에서 인증에 성공하면,
    // 인증된 Authentication 객체가 생성되면서 principal 필드에 Member 객체가 할당됨
    User user = (User) authResult.getPrincipal();

    String accessToken = delegateAccessToken(user);
    String refreshToken = delegateRefreshToken(user);

    response.setHeader("Authorization", "Bearer " + accessToken);
    response.setHeader("Refresh", refreshToken);
  }

  // ====================== private methods ======================
  // access token 생성
  private String delegateAccessToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", user.getUsername());
    claims.put("email", user.getEmail());
    claims.put("role", user.getRole());
    String subject = user.getId().toString();

    return jwtTokenProvider.generateAccessToken(claims, subject);
  }

  // refresh token 생성
  private String delegateRefreshToken(User user) {
    String subject = user.getId().toString();

    return jwtTokenProvider.generateRefreshToken(subject);
  }
}

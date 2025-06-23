package com.sprint.mission.discodeit.dto.AuthService;

// login request from client
public record UserLoginRequestDto(
    String username,
    String password
) {}

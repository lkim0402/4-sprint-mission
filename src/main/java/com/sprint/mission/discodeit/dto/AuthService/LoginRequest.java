package com.sprint.mission.discodeit.dto.AuthService;

// login request from client
public record LoginRequest(
    String username,
    String password
) {}

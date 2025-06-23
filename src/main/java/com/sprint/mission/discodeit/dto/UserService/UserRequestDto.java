package com.sprint.mission.discodeit.dto.UserService;


import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;

public record UserRequestDto (
    String username,
    String email,
    String password,
    BinaryContentRequestDto profilePicture
) {}

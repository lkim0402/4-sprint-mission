package com.sprint.mission.discodeit.dto.BinaryContentService;

public record BinaryContentResponseDto(
    byte[] bytes,
    String fileName,
    String fileType
) {}

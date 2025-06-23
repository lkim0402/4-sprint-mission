package com.sprint.mission.discodeit.dto.BinaryContentService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BinaryContentRequestDto{
        private final byte[] bytes;
        private final String fileName;
        private final String fileType;
}

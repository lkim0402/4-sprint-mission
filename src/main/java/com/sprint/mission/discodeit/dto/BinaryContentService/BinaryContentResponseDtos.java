package com.sprint.mission.discodeit.dto.BinaryContentService;

import java.util.List;
import java.util.stream.Collectors;

public record BinaryContentResponseDtos(
        List<BinaryContentResponseDto> binaryContentResponseDtos
) {
    @Override
    public String toString() {
        if (binaryContentResponseDtos == null || binaryContentResponseDtos.isEmpty()) {
            return "Binary Contents: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of Binary Content ---" +
                binaryContentResponseDtos.stream()
                        .map(BinaryContentResponseDto::toString) //
                        .collect(Collectors.joining(","));
    }
}

package com.sprint.mission.discodeit.dto.BinaryContentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record BinaryContentRequestDtos(
        List<UUID> binaryContentUUIDList
) {
    @Override
    public String toString() {
        if (binaryContentUUIDList == null || binaryContentUUIDList.isEmpty()) {
            return "Binary Contents: []";
        }

        return binaryContentUUIDList.stream()
                        .map(UUID::toString) //
                        .collect(Collectors.joining(","));
    }
}

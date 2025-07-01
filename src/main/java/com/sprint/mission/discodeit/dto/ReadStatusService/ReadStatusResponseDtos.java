package com.sprint.mission.discodeit.dto.ReadStatusService;

import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.stream.Collectors;

public record ReadStatusResponseDtos(
        List<ReadStatusResponseDto> readStatusRequestDtos

) {
    @Override
    public String toString() {
        if (readStatusRequestDtos == null || readStatusRequestDtos.isEmpty()) {
            return "ReadStatus list: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of ReadStatus ---" +
                readStatusRequestDtos.stream()
                        .map(ReadStatusResponseDto::toString) //
                        .collect(Collectors.joining(","));
    }
}

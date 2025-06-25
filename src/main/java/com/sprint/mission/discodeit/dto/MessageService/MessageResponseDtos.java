package com.sprint.mission.discodeit.dto.MessageService;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public record MessageResponseDtos(
    List<MessageResponseDto> messageResponseDtos
) {
    @Override
    public String toString() {
        if (messageResponseDtos == null || messageResponseDtos.isEmpty()) {
            return "Messages: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of Messages ---" +
                messageResponseDtos.stream()
                        .map(MessageResponseDto::toString) //
                        .collect(Collectors.joining(","));
    }
}

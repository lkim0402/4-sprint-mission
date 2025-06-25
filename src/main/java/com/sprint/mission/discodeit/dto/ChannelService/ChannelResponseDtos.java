package com.sprint.mission.discodeit.dto.ChannelService;
import java.util.List;
import java.util.stream.Collectors;

public record ChannelResponseDtos(
        List<ChannelResponseDto> channelResponseDtosList
) {
    @Override
    public String toString() {
        if (channelResponseDtosList == null || channelResponseDtosList.isEmpty()) {
            return "Channels: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of Channels ---" +
                channelResponseDtosList.stream()
                        .map(ChannelResponseDto::toString) //
                        .collect(Collectors.joining(","));
    }

}

package com.sprint.mission.discodeit.dto.UserService;
import com.sprint.mission.discodeit.dto.ChannelService.ChannelResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public record UserResponseDtos(
    List<UserResponseDto> userResponseDtoList
) {
    @Override
    public String toString() {
        if (userResponseDtoList == null || userResponseDtoList.isEmpty()) {
            return "Users: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of Users ---" +
                userResponseDtoList.stream()
                        .map(UserResponseDto::toString) //
                        .collect(Collectors.joining(","));
    }
}

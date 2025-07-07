package com.sprint.mission.discodeit.dto.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserDtos(
    List<UserDto> userDtoList
) {
    @Override
    public String toString() {
        if (userDtoList == null || userDtoList.isEmpty()) {
            return "Users: []";
        }

        // use a stream to call the toString() on each item in the list
        // then join them together with a "," and a newline
        return "\n--- List of Users ---" +
                userDtoList.stream()
                        .map(UserDto::toString) //
                        .collect(Collectors.joining(","));
    }
}

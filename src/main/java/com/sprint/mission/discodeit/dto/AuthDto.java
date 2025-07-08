package com.sprint.mission.discodeit.dto;
import com.sprint.mission.discodeit.dto.UserStatusService.UserStatusResponseDto;
import java.util.UUID;

public class AuthDto {

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record LoginResponse(
            UUID userId,
            String username,
            String email,
            UserStatusResponseDto userStatusResponseDto
    ) {
        @Override
        public String toString() {
            return "\n" +
                    "    LoginResponse          {" + "\n" +
                    "    userId                = " + this.userId + ",\n" +
                    "    username              = " + this.username + ",\n" +
                    "    email                 = " + this.email + ",\n" +
                    "    userStatusResponseDto = " + this.userStatusResponseDto + "\n" +
                    "  }";
        }
    }
}

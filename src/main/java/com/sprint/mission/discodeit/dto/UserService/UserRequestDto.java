package com.sprint.mission.discodeit.dto.UserService;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private MultipartFile profilePicture;
}

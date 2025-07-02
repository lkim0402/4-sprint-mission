package com.sprint.mission.discodeit.dto.UserService;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 신규 사용자 생성 요청을 위한 데이터 DTO입니다.
 * 클라이언트가 신규 사용자를 등록하기 위해 필요한 모든 정보를 담고 있습니다.
 *
 * @param username 신규 사용자의 이름. (중복 불가).
 * @param email 사용자의 이메일 주소. (중복 불가).
 * @param password 사용자의 암호화되지 않은 원본 비밀번호.
 * @param profilePicture 프로필 사진 데이터를 담고 있는 선택적 DTO. null일 수 있습니다.
 */
public record UserRequestDto (
    String username,
    String email,
    String password,
    MultipartFile profilePicture
) {}

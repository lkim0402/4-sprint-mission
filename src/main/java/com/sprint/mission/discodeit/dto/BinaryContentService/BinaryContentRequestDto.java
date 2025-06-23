package com.sprint.mission.discodeit.dto.BinaryContentService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * 바이너리 콘텐츠(파일 등) 생성을 요청하기 위한 DTO입니다.
 * 파일 데이터와 함께 해당 파일이 어떤 사용자나 메시지에 속하는지에 대한
 * 선택적 컨텍스트 정보를 담습니다.
 *
 * @param userId 이 콘텐츠를 소유한 사용자의 ID. 프로필 사진 등 사용자와 직접 연결될 때 사용됩니다. (null일 수 있음)
 * @param messageId 이 콘텐츠가 첨부된 메시지의 ID. 메시지 첨부 파일일 경우 사용됩니다. (null일 수 있음)
 * @param bytes 파일의 원본 바이트 데이터 배열입니다.
 * @param fileName 파일의 이름 (예: "profile.jpg").
 * @param fileType 파일의 MIME 타입 (예: "image/jpeg", "application/pdf").
 */
public record BinaryContentRequestDto(
        UUID userId,
        UUID messageId,
        byte[] bytes,
        String fileName,
        String fileType
) {}

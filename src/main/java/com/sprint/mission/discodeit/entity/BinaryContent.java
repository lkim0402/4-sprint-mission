package com.sprint.mission.discodeit.entity;
import lombok.Getter;
import java.util.UUID;

    /**
     * 이미지, 파일 등 바이너리 데이터를 표현하는 도메인 모델입니다.
     * 사용자의 프로필 이미지, 메시지에 첨부된 파일을 저장하기 위해 활용합니다.
     * 수정 불가능한 도메인 모델로 간주합니다.
     * 따라서 updatedAt 필드는 정의하지 않습니다.
     * User, Message 도메인 모델과의 의존 관계 방향성을 잘 고려하여 id 참조 필드를 추가하세요.
     */
@Getter
public class BinaryContent extends ImmutableBaseEntity {

    private final UUID userId;
    private final UUID messageId;
    private final byte[] bytes;
    private final String fileName;
    private final String fileType;

    public BinaryContent(UUID userId, UUID messageId, byte[] bytes, String fileName, String fileType) {
        super();
        this.userId = userId;
        this.messageId = messageId;
        this.bytes = bytes;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}


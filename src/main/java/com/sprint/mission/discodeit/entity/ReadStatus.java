package com.sprint.mission.discodeit.entity;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReadStatus extends BaseEntity{
    /**
     * 사용자가 채널 별 마지막으로 메시지를 읽은 시간을 표현하는 도메인 모델입니다.
     * 사용자별 각 채널에 읽지 않은 메시지를 확인하기 위해 활용합니다.
     * ex) User Id:123, Channel: TestChannel, lastReadAt:(time)
     */

    private final UUID userId;
    private final UUID channelId;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId) {
        super();
        this.userId = userId;
        this.channelId = channelId;
        this.lastReadAt = Instant.now();
    }

    public void updateLastReadAt() {
        this.lastReadAt = Instant.now();
        this.updateTimeStamp();
    }
}

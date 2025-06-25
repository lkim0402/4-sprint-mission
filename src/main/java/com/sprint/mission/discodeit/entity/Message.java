package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Message extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private String content;
    private final UUID channelId;
    private final UUID authorId;

    public Message(String content, UUID channelId, UUID authorId) {
        super();
        this.content = content;
        this.channelId = channelId;
        this.authorId = authorId;
    }

    public void update(String newContent) {
        boolean anyValueUpdated = false;
        if (newContent != null && !newContent.equals(this.content)) {
            this.content = newContent;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updateTimeStamp();
        }
    }

    @Override
    public String toString() {
        return "\n" +
                "Message {" + "\n" +
                "  ID         = " + this.getId() + ",\n" +
                "  UserId     = " + this.authorId + ",\n" +
                "  ChannelId  = " + this.channelId + "\n" +
                "  Content    = '" + content + "',\n" +
                "}";
    }
}

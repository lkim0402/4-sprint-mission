package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Channel extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    final private ChannelType type;
    private String name;
    private String description;

    public Channel(ChannelType type, String name, String description) {
        super();
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void update(String newName, String newDescription) {
        boolean anyValueUpdated = false;
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
            anyValueUpdated = true;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updateTimeStamp();
        }
    }

    @Override
    public String toString() {
        return "\n" +
                "Channel {" + "\n" +
                "  Name = '" + this.name + "',\n" +
                "  ID   = " + this.getId() + ",\n" +
                "  type = " + this.type + "\n" +
                "}";
    }
}

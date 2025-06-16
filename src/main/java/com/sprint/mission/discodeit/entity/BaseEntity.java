package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class BaseEntity implements Serializable {

    private UUID id;
    private final long createdAt;
    private long updatedAt;

    @Serial
    private static final long serialVersionUID = 1L; // base entity's version

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void updateTimeStamp() {
        this.updatedAt = System.currentTimeMillis();
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    // used only when we want to update user
    public void setId(UUID id) {
        this.id = id;
    }
}

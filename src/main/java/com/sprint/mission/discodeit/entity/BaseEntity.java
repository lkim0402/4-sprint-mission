package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BaseEntity implements Serializable {

    private final UUID id;
    private final Instant createdAt;
    Instant updatedAt;

    @Serial
    private static final long serialVersionUID = 1L; // base entity's version

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    protected void updateTimeStamp() {
        this.updatedAt = Instant.now();
    }
}
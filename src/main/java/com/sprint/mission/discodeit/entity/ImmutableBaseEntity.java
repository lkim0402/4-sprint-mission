package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ImmutableBaseEntity implements Serializable {

    private final UUID id;
    private final Instant createdAt;
    private final Instant updatedAt;

    public ImmutableBaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
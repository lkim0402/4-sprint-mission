package com.sprint.mission.discodeit.event;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BinaryContentCreatedEvent extends ApplicationEvent {

  private final UUID id;
  private final byte[] bytes;

  public BinaryContentCreatedEvent(Object source, UUID id, byte[] bytes) {
    super(source);
    this.id = id;
    this.bytes = bytes;
  }
}

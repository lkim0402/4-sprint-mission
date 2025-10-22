package com.sprint.mission.discodeit.event.Listener;

import com.sprint.mission.discodeit.event.BinaryContentCreatedEvent;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class BinaryContentListener {

  private final BinaryContentStorage binaryContentStorage;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onApplicationEvent(BinaryContentCreatedEvent event) {
    binaryContentStorage.put(event.getId(), event.getBytes());
  }
}

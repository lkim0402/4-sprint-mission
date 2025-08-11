package com.sprint.mission.discodeit.exception.channel;

import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class ChannelNotFoundException extends ChannelException {

  public ChannelNotFoundException(
      UUID channelId) {
    super(
        Instant.now(),
        ErrorCode.CHANNEL_NOT_FOUND,
        Map.of("channelId", channelId));
  }
}

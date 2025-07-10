package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusDto.*;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReadStatusMapper {

  public ReadStatus toReadStatus(ReadStatusRequest readStatusRequest) {
    return new ReadStatus(
        readStatusRequest.userId(),
        readStatusRequest.channelId()
    );
  }

  public ReadStatusResponse toReadStatusResponse(ReadStatus readStatus) {
    return new ReadStatusResponse(
        readStatus.getId(),
        readStatus.getUserId(),
        readStatus.getChannelId(),
        readStatus.getLastReadAt(),
        readStatus.getCreatedAt(),
        readStatus.getUpdatedAt()
    );
  }

  public ReadStatusResponseDtos toReadStatusResponseDtos(List<ReadStatus> readStatuses) {
    return new ReadStatusResponseDtos(
        readStatuses
            .stream()
            .map(this::toReadStatusResponse)
            .toList()
    );
  }

}

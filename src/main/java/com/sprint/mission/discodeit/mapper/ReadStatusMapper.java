package com.sprint.mission.discodeit.mapper;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDtos;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReadStatusMapper {

    // Request
    public ReadStatus toReadStatus(ReadStatusRequestDto readStatusRequestDto) {
        return new ReadStatus(
            readStatusRequestDto.userId(),
            readStatusRequestDto.channelId()
        );
    }

    // Response
    public ReadStatusResponseDto toReadStatusDto(ReadStatus readStatus) {
        return new ReadStatusResponseDto(
            readStatus.getId(),
            readStatus.getUserId(),
            readStatus.getChannelId(),
            readStatus.getLastReadAt()
        );
    }

    // Response
    public ReadStatusResponseDtos toReadStatusResponseDtos(List<ReadStatus> readStatuses) {
        return new ReadStatusResponseDtos(
            readStatuses
                .stream()
                .map(this::toReadStatusDto)
                .toList()
        );
    }

}

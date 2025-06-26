package com.sprint.mission.discodeit.dto.ChannelService;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.UUID;

public record UpdateChannelResponseDto(
        UUID channelId,
        ChannelType channelType,
        String name,
        String description
) {
    @Override
    public String toString() {
        return "\n" +
                "    UpdateChannelResponseDto {" + "\n" +
                "    channelType       = " + this.channelType + ",\n" +
                "    channelId         = " + this.channelId + ",\n" +
                "    name              = " + this.name + ",\n" +
                "    description       = " + this.description + ",\n" +
                "  }";
    }
}

package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ReadStatus = membership
 *  A user is a "member" of a channel if there is a ReadStatus entry
 *  linking that userId to that channelId
 */
public interface ReadStatusRepository {
    ReadStatus save(ReadStatus readStatus);
    Optional<ReadStatus> findById(UUID id);
    List<ReadStatus> findByChannelId(UUID channelId); //
    List<ReadStatus> findByUserId(UUID userId);
    Optional<ReadStatus> findByChannelAndUserId(UUID channelId, UUID userId);
    List<ReadStatus> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
}

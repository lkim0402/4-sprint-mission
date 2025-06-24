package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileReadStatusRepository implements ReadStatusRepository {
    public ReadStatus save(ReadStatus readStatus) {

    }
    public Optional<ReadStatus> findById(UUID id) {

    }
    public List<ReadStatus> findByChannelId(UUID channelId) {

    }
    public List<ReadStatus> findByUserId(UUID userId) {

    }
    public Optional<ReadStatus> findByChannelAndUserId(UUID channelId, UUID userId) {

    }
    public List<ReadStatus> findAll() {

    }
    public boolean existsById(UUID id) {

    }
    public void deleteById(UUID id) {

    }
}

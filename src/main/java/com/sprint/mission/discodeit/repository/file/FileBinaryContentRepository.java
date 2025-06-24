package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository implements BinaryContentRepository {
    public BinaryContent save(BinaryContent binaryContent) {

    }
    public Optional<BinaryContent> findById(UUID id) {

    }
    public Optional<BinaryContent> findByUserId(UUID userId) {

    }
    public List<BinaryContent> findByMessageId(UUID messageId) {

    }
    public List<BinaryContent> findAll() {

    }
    public List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIds) {

    }
    public boolean existsById(UUID id) {

    }
    public void deleteById(UUID id) {

    }
}

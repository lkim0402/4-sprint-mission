package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserStatusRepository implements UserStatusRepository {
    public UserStatus save(UserStatus userStatus) {

    }

    public Optional<UserStatus> findById(UUID id) {

    }

    public Optional<UserStatus> findByUserId(UUID userId) {

    }

    public List<UserStatus> findAll() {

    }

    public boolean existsById(UUID id) {

    }

    public void deleteById(UUID id) {

    }
}

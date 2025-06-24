package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class JCFUserStatusRepository implements UserStatusRepository {
    private final Map<UUID, UserStatus> data; // UserStatusId : UserStatus
    private final Map<UUID, UserStatus> userIndex; // UserId : UserStatus

    public JCFUserStatusRepository() {
        this.data = new HashMap<>();
        this.userIndex = new HashMap<>();
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        this.data.put(userStatus.getId(), userStatus);
        this.userIndex.put(userStatus.getUserId(), userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return Optional.ofNullable(userIndex.get(userId));
    }

    @Override
    public List<UserStatus> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        UserStatus deletedUserStatus = this.data.remove(id);

        if (deletedUserStatus != null) {
            this.userIndex.remove(deletedUserStatus.getUserId());
        }
    }
}

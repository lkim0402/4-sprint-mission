package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;

@Primary
@Repository
public class JCFUserStatusRepository implements UserStatusRepository {
    /**
     * userIndex: userId를 키로 사용하여 O(1) 시간에 UserStatus Id를 조회합니다.
     * Key: UUID userId, Value: UUID UserStatusId
     * 유저 아이디로 조회할 때 O(n)에서 O(1) 시간 복잡도로 줄일 수 있어서 구현했습니다.
     */
    private final Map<UUID, UserStatus> data; // UserStatusId : UserStatus
    private final Map<UUID, UUID> userIndex; // UserId : UserStatus ID

    public JCFUserStatusRepository() {
        this.data = new HashMap<>();
        this.userIndex = new HashMap<>();
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        this.data.put(userStatus.getId(), userStatus);
        this.userIndex.put(userStatus.getUserId(), userStatus.getId());
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return Optional.ofNullable(userIndex.get(userId))
                .map(this.data::get);
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

    @Override
    public void deleteAll() {
        this.data.clear();
        this.userIndex.clear();
    }
}

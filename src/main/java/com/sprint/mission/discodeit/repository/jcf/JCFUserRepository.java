package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
public class JCFUserRepository implements UserRepository {

    /**
     * nameIndex: username을 key로 사용하여 O(1) 시간에 User Id를 조회합니다.
     * Key: String username, Value: UUID UserId
     * 유저네임으로 조회할 때 O(n)에서 O(1) 시간 복잡도로 줄일 수 있어서 구현했습니다.
     */
    private final Map<UUID, User> data; // User Id : User
    private final Map<String, UUID> nameIndex; // Username : User

    public JCFUserRepository() {
        this.data = new HashMap<>();
        this.nameIndex = new HashMap<>();
    }

    @Override
    public User save(User user) {
        this.data.put(user.getId(), user);
        this.nameIndex.put(user.getUsername(), user.getId());
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
            return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(nameIndex.get(username))
                .map(this.data::get);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {

        User user = this.data.remove(id);

        if (user != null) {
            this.nameIndex.remove(user.getUsername());
        }
    }

    @Override
    public void deleteAll() {
        this.data.clear();
        this.nameIndex.clear();
    }
}

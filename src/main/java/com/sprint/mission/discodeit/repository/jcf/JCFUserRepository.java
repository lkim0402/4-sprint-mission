package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> data; // User Id : User
    private final Map<String, User> nameIndex; // Username : User

    public JCFUserRepository() {
        this.data = new HashMap<>();
        this.nameIndex = new HashMap<>();
    }

    @Override
    public User save(User user) {
        this.data.put(user.getId(), user);
        this.nameIndex.put(user.getUsername(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
            return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(nameIndex.get(username));
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
}

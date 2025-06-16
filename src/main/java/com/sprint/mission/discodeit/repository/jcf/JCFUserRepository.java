package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFUserRepository implements UserRepository {
    List<User> data = new ArrayList<>();

    @Override
    public void saveAll(List<User> users) {
        data = users;
    }

    @Override
    public List<User> findAll() {
        return data.stream()
                .filter(u -> u.getUserStatus() == (UserStatus.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findVerifiedUser(UUID id) {
        System.out.println(findAll());
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(User user) {

        List<User> users = findAll();

        // replace if same ID, add if none
        boolean updated = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                updated = true;
                break;
            }
        }

        if (!updated) {
            users.add(user);
        }

        saveAll(users);
    }

    @Override
    public void deleteUser(UUID id) {
        data.removeIf(u -> u.getId().equals(id));
    }
}

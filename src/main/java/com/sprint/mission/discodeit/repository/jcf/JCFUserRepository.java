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
    public Optional<User> findUser(UUID id) {
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
}

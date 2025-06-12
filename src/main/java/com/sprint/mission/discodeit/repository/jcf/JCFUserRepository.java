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
    public Optional<User> findById(UUID id) {
        System.out.println(findAll());
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(UUID id, User user) {

        List<User> users = findAll();
        boolean updated = false;

        // replace if same ID, add if none
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getId().equals(id)) {
                Optional.ofNullable(user.getUserName())
                        .ifPresent(name -> u.setUserName(name));

                // setting email
                Optional.ofNullable(user.getEmail())
                        .ifPresent(email -> u.setEmail(email));

                // setting password
                Optional.ofNullable(user.getPassword())
                        .ifPresent(pw -> u.setPassword(pw));

                // setting status
                Optional.ofNullable(user.getUserStatus())
                        .ifPresent(status -> u.setUserStatus(status));

                //partialUser updatedAt
                u.updateTimeStamp();
                users.set(i, u);
                updated = true;
                break;
            }
        }

        if (!updated) {
            if (user.getUserName() != null &&
                    user.getEmail() != null &&
                    user.getPassword() != null &&
                    user.getUserStatus() != null) {
                users.add(user);
            } else {
                throw new IllegalArgumentException("Cannot add user: name, email, password, and status must all be provided");
            }
        }

        saveAll(users);

    }

    @Override
    public void deleteById(UUID id) {
        data.removeIf(u -> u.getId().equals(id));
    }
}

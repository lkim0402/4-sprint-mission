package com.sprint.mission.discodeit.service.jcf;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFListUserService implements UserService {

    private final UserRepository userRepository;

    public JCFListUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(String userName, String email, String pw) {
        User newUser = new User(userName, email, pw);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User findVerifiedUser(UUID id) {
        return userRepository.findVerifiedUser(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getUsersByStatus(UserStatus userStatus) {
        List<User> users = getUsers();

        return users.stream()
                .filter(u -> u.getUserStatus() == userStatus)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UUID id, User partialUser) {
        User user = findVerifiedUser(id);

        // replace if same ID, add if none
        Optional.ofNullable(partialUser.getUserName())
                .ifPresent(name -> user.setUserName(partialUser.getUserName()));

        // setting email
        Optional.ofNullable(partialUser.getEmail())
                .ifPresent(email -> user.setEmail(partialUser.getEmail()));

        // setting password
        Optional.ofNullable(partialUser.getPassword())
                .ifPresent(pw -> user.setPassword(partialUser.getPassword()));

        // setting status
        Optional.ofNullable(partialUser.getUserStatus())
                .ifPresent(status -> user.setUserStatus(partialUser.getUserStatus()));

        //partialUser updatedAt
        user.updateTimeStamp();

        userRepository.save(user);    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteUser(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void clearUsers() {
        List<User> users = new ArrayList<>();
        userRepository.saveAll(users);

    }
}

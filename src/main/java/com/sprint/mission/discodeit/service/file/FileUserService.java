package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUserService implements UserService {

    private final UserRepository userRepository;

    public FileUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(String userName, String email, String password) {
        User newUser = new User(userName, email, password);
        addUser(newUser);
        return newUser;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findUser(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByStatus(UserStatus userStatus) {
        List<User> users = getUsers();

        return users.stream()
                .filter(u -> u.getUserStatus() == userStatus)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(UUID id, User partialUser) {

        List<User> users = getUsers();

        for (User u : users) {
            if (u.getId().equals(id)) {
                Optional.ofNullable(partialUser.getUserName())
                        .ifPresent(name -> u.setUserName(name));

                // setting email
                Optional.ofNullable(partialUser.getEmail())
                        .ifPresent(email -> u.setEmail(email));

                // setting password
                Optional.ofNullable(partialUser.getPassword())
                        .ifPresent(pw -> u.setPassword(pw));

                // setting status
                Optional.ofNullable(partialUser.getUserStatus())
                        .ifPresent(status -> u.setUserStatus(status));

                //partialUser updatedAt
                u.updateTimeStamp();
                userRepository.saveAll(users);
                return u;
            }
        }

        return null;
    }

    @Override
    public void deleteUser(UUID id) {
        List<User> users = getUsers();

        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setUserStatus(UserStatus.WITHDRAWN);
                u.updateTimeStamp();
            }
        }

        userRepository.saveAll(users);
    }

    @Override
    public void clearUsers() {
        List<User> users = new ArrayList<>();
        userRepository.saveAll(users);
    }

    // utility methods
    /**
     * 새로운 유저를 목록에 추가합니다.
     * 이미 존재하는 유저는 중복 추가되지 않습니다.
     *
     * @param user 추가할 유저 객체
     */
    public void addUser(User user) {

        List<User> users = getUsers();

        boolean alreadyExist = users.stream()
                .anyMatch(u -> u.getId().equals(user.getId()));

        if (!alreadyExist) {
            users.add(user);
            userRepository.saveAll(users);
        }
    }


}

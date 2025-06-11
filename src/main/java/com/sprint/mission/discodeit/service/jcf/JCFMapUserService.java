package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFMapUserService implements UserService {
    public final Map<UUID, User> data;

    public JCFMapUserService() {
        data = new HashMap<>();
    }

    @Override
    public User createUser(String userName, String email, String pw) {
        User newUser = new User(userName, email, pw);
        data.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User getUser(UUID id) {

        List<User> users = new ArrayList<>(data.values());

        return users.stream()
                .filter(u ->
                        u.getUserStatus() == UserStatus.ACTIVE && u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsers() {

        return getUsersByStatus(UserStatus.ACTIVE);
    }

    @Override
    public List<User> getUsersByStatus(UserStatus userStatus) {
        List<User> users = new ArrayList<>(data.values());

        return users.stream()
                .filter(u -> u.getUserStatus() == userStatus)
                .collect(Collectors.toList());

    }

    // update user 합침
    @Override
    public User updateUser(UUID id, User userInfo) {

        User updatedUser = Optional.ofNullable(data.get(id))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다!"));

        if (updatedUser.getUserStatus() == (UserStatus.WITHDRAWN)) {
            throw new IllegalArgumentException("탈퇴한 유저입니다!");

        }

        // setting username
        Optional.ofNullable(userInfo.getUserName())
                .ifPresent(name -> updatedUser.setUserName(name));

        // setting email
        Optional.ofNullable(userInfo.getEmail())
                .ifPresent(email -> updatedUser.setEmail(email));

        // setting password
        Optional.ofNullable(userInfo.getPassword())
                .ifPresent(pw -> updatedUser.setPassword(pw));;

        // setting status
        Optional.ofNullable(userInfo.getUserStatus())
                .ifPresent(status -> updatedUser.setUserStatus(status));

        //setting updatedAt
        updatedUser.updateTimeStamp();

        return updatedUser;


    }


    @Override
    public void deleteUser(UUID id) {
        if (!data.containsKey(id)) {
            return;
        }

        User user =  data.get(id);
        user.setUserStatus(UserStatus.WITHDRAWN);
        user.updateTimeStamp();
        user.deleteChannels();
    }


    @Override
    public void clearUsers() {
        data.clear();
    }
}

package com.sprint.mission.discodeit.service.jcf;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFListUserService implements UserService {
    public final ArrayList<User> data;

    public JCFListUserService() {
        data = new ArrayList<>();
    }


    @Override
    public User createUser(String userName, String email, String pw) {
        User newUser = new User(userName, email, pw);
        data.add(newUser);
        return newUser;
    }

    @Override
    public User getUser(UUID id) {

        return data.stream()
                .filter(u ->
                        u.getUserStatus() == (UserStatus.ACTIVE) && u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsers() {

        return getUsersByStatus(UserStatus.ACTIVE);
    }

    @Override
    public List<User> getUsersByStatus(UserStatus userStatus) {
        return data.stream()
                .filter(u -> u.getUserStatus() == (userStatus))
                .collect(Collectors.toList());
    }

    // update user 합침
    @Override
    public User updateUser(UUID id, User userInfo) {
        User updatedUser = data.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
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
                .ifPresent(pw -> updatedUser.setPassword(pw));

        // setting status
        Optional.ofNullable(userInfo.getUserStatus())
                .ifPresent(status -> updatedUser.setUserStatus(status));

        //setting updatedAt
        updatedUser.setUpdatedAt();

        return updatedUser;
    }



    @Override
    public boolean deleteUser(UUID id) {
        User user = getUser(id);
        if (user == null) return false;

        user.setUserStatus(UserStatus.WITHDRAWN);
        user.setUpdatedAt();
        user.deleteChannels();
        return true;
    }


    @Override
    public void clearUsers()
    {
        data.clear();
    }
}

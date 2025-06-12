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
        addUser(newUser);
        return newUser;
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
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
        validateUserExists(findById(id));

        userRepository.save(id, partialUser);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
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

    // =========== utility methods ===========

    /**
     * 새로운 유저를 목록에 추가합니다.
     * 이미 존재하는 유저는 중복 추가되지 않고 정보는 덮어씌워집니다.
     *
     * @param user 추가할 유저 객체
     */
    private void addUser(User user) {
        userRepository.save(user.getId(), user);
    }

    /**
     * 지정된 유저가 시스템에 존재하는지 검증합니다.
     *
     * @param user 존재 여부를 확인할 유저 객체
     * @throws RuntimeException 유저가 존재하지 않는 경우 예외를 발생시킵니다
     */
    private void validateUserExists(User user) {
        List<User> users = getUsers();

        boolean alreadyExist = users.stream()
                .anyMatch(u -> u.getId().equals(user.getId()));

        if (!alreadyExist) {
            throw new RuntimeException("User " + user.getId() + " does not exist");
        }
    }
}

package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private static final String FILE_PATH = "data/users.ser";

    @Override
    public void saveAll(List<User> users) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading users: " + e.getMessage());
            return new ArrayList<>(); // fallback
        }
        return users;
    }

    @Override
    public Optional<User> findVerifiedUser(UUID id) {
        List<User> users = findAll();
        return users.stream()
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
        List<User> users = findAll();
        users.removeIf(u -> u.getId().equals(id));
        saveAll(users);
    }}

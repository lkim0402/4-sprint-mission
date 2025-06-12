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
    private static final String FILE_PATH = "data/channels.ser";

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
    public Optional<User> findById(UUID id) {
        List<User> users = findAll();
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(UUID id, User user) {

        List<User> data = findAll();
        boolean updated = false;

        // replace if same ID, add if none
        for (User u : data) {
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
                updated = true;
                break;
            }
        }

        if (!updated) {
            if (user.getUserName() != null &&
                    user.getEmail() != null &&
                    user.getPassword() != null &&
                    user.getUserStatus() != null) {
                user.setId(id);
                data.add(user);
            } else {
                throw new IllegalArgumentException("Cannot add user: name, email, password, and status must all be provided");
            }
        }

        saveAll(data);
    }

    @Override
    public void deleteById(UUID id) {
        List<User> users = findAll();
        users.removeIf(u -> u.getId().equals(id));
        saveAll(users);
    }}

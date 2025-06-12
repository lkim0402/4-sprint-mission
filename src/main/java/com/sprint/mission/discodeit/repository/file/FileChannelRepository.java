package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {

    private static final String FILE_PATH = "data/channels.ser";

    @Override
    public void saveAll(List<Channel> channels) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(channels);
        } catch (IOException e) {
            System.out.println("Error saving channels: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = null;
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            channels = (List<Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading channels: " + e.getMessage());
            return new ArrayList<>(); // fallback
        }
        return channels;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Channel channel) {
        List<Channel> channels = findAll();

        // replace if same ID, add if none
        boolean updated = false;

        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getId().equals(channel.getId())) {
                channels.set(i, channel);
                updated = true;
                break;
            }
        }

        if (!updated) {
            channels.add(channel);
        }

        saveAll(channels);
    }

    @Override
    public void deleteById(UUID id) {
        List<Channel> channels = findAll();
        channels.removeIf(c -> c.getId().equals(id));
        saveAll(channels);
    }
}

package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {

    private static final String FILE_PATH = "data/messages.ser";

    @Override
    public void saveAll(List<Message> messages) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(messages);
        } catch (IOException e) {
            System.out.println("Error saving channels: " + e.getMessage());
        }
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = null;

        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            messages = (List<Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading channels: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return messages;
    }

    @Override
    public Optional<Message> findMessage(UUID id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();

    }

}

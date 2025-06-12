package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    private List<Message> data = new ArrayList<>();

    @Override
    public void saveAll(List<Message> messages) {
        data = messages;
    }

    @Override
    public List<Message> findAll() {
        return data;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Message message) {

        // replace if same ID, add if none
        boolean updated = false;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(message.getId())) {
                data.set(i, message);
                updated = true;
                break;
            }
        }

        if (!updated) {
            data.add(message);
        }

        saveAll(data);

    }

    @Override
    public void deleteById(UUID id) {
        data.removeIf(m -> m.getId().equals(id));
    }
}

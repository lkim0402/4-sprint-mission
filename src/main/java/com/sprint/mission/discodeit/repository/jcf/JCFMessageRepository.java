package com.sprint.mission.discodeit.repository.jcf;
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
    public Optional<Message> findMessage(UUID id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }
}

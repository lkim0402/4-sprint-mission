package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> data; // Message Id : Message
    private final Map<UUID, List<Message>> channelIndex; // Channel Id : List<Message>

    public JCFMessageRepository() {
        this.data = new HashMap<>();
        this.channelIndex = new HashMap<>();
    }

    @Override
    public Message save(Message message) {
        this.data.put(message.getId(), message);

        this.channelIndex.computeIfAbsent(
                message.getChannelId(),
                k -> new ArrayList<>()
        ).add(message);

        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<Message> findByChannelId(UUID channelId) {
        return channelIndex.getOrDefault(channelId, Collections.emptyList());
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public void deleteById(UUID messageId) {

        Message deletedMessage = this.data.remove(messageId);

        if (deletedMessage != null) {
            List<Message> channelMessageList = channelIndex.get(deletedMessage.getChannelId());
            if (channelMessageList != null) {
                channelMessageList.remove(deletedMessage);
                if (channelMessageList.isEmpty()) {
                    channelIndex.remove(deletedMessage.getChannelId());
                }
            }
        }
    }
}

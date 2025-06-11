package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;


    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(User user, String message, Channel channel) {
        if (user.getUserStatus() == UserStatus.WITHDRAWN) {
            return null;
        }
        Message msg = new Message(user, message, channel);

        user.addMessage(msg);
        channel.addMessage(msg);

        addMessage(msg);
        return msg;

    }


    @Override
    public Message getMessage(UUID id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
    }


    @Override
    public Message updateMessage(UUID msgUUID, String message) {
        List<Message> messages = getMessages();

        for (Message msg : messages) {
            if (msgUUID.equals(msg.getId())) {
                msg.setMessage(message);
            }
        }

        messageRepository.saveAll(messages);
        return getMessage(msgUUID);

    }

    @Override
    public void deleteMessage(UUID id) {
        List<Message> messages = getMessages();
        messages.removeIf(m -> m.getId().equals(id));
        messageRepository.saveAll(messages);
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Override
    public void clearMessages() {
        List<Message> messages = new ArrayList<>();
        messageRepository.saveAll(messages);
    }

    // utility
    /**
     * 새로운 메세지를 목록에 추가합니다.
     * 이미 존재하는 메세지는 중복 추가되지 않습니다.
     *
     * @param msg 추가할 메세지 객체
     */
    private void addMessage(Message msg) {
        isExistMessage(msg);

        List<Message> msgs = getMessages();
        msgs.add(msg);
        messageRepository.saveAll(msgs);
    }

    private void isExistMessage(Message msg) {
        List<Message> msgs = getMessages();

        boolean alreadyExist = msgs.stream()
                .anyMatch(c -> c.getId().equals(msg.getId()));

        if (alreadyExist) {
            throw new RuntimeException(msg.getId() + " already exists");
        }
    }
}

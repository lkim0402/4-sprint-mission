package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMapMessageService implements MessageService {

    public final Map<UUID, Message> data;

    public JCFMapMessageService() {
        data = new HashMap<>();
    }

    @Override
    public Message createMessage(User user, String message, Channel channel) {
        if (user.getUserStatus() == (UserStatus.WITHDRAWN)) {
            return null;
        }

        Message msg = new Message(user, message, channel);

        user.addMessage(msg);
        channel.addMessage(msg);

        data.put(msg.getId(), msg);

        return msg;
    }

    @Override
    public Message getMessage(UUID id) {

        List<Message> msgList = new ArrayList<>(data.values());
        return msgList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Message updateMessage(UUID msgUUID, String message) {
        if (getMessage(msgUUID).getUser().getUserStatus() == (UserStatus.WITHDRAWN)) {
            return null;
        }

        if (!data.containsKey(msgUUID)) {
            return null;
        }

        // update in map (reference type)
        Message newMsg = data.get(msgUUID);
        newMsg.setMessage(message);
        data.put(msgUUID, newMsg);

        return newMsg;

    }

    @Override
    public boolean deleteMessage(UUID id) {
        Message m = getMessage(id);

        if (!data.containsKey(id) || m.getUser().getUserStatus() == (UserStatus.WITHDRAWN)) {
            return false;
        }

        data.remove(id);
        return true;
    }

    @Override
    public ArrayList<Message> getMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void clearMessages() {
        data.clear();
    }
}
package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.UUID;

public class JCFListMessageService implements MessageService {

    public final ArrayList<Message> data;
    public JCFListMessageService() {
        data = new ArrayList<>();
    }


    @Override
    public Message createMessage(User user, String message, Channel channel) {
        if (user.getUserStatus() == UserStatus.WITHDRAWN) {
            return null;
        }
        Message msg = new Message(user, message, channel);

        user.addMessage(msg);
        channel.addMessage(msg);

        data.add(msg);
        return msg;

    }

    @Override
    public Message getMessage(UUID id) {
        return data.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Message updateMessage(UUID msgUUID, String message) {
        if (getMessage(msgUUID).getUser().getUserStatus() == UserStatus.WITHDRAWN) {
            return null;
        }

        for (Message m : data) {
            if (m.getId().equals(msgUUID)) {
                m.setMessage(message);
                return m;
            }
        }

        return null;
    }


    @Override
    public boolean deleteMessage(UUID id) {
        Message m = getMessage(id);

        if (!data.contains(m) || m.getUser().getUserStatus() == UserStatus.WITHDRAWN ) {
            return false;
        }

        data.remove(m);
        return true;
    }


    @Override
    public ArrayList<Message> getMessages()
    {
        return data;
    }


    @Override
    public void clearMessages()
    {
        data.clear();
    }
}
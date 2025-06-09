package com.sprint.mission.discodeit.entity;

import java.util.*;

public class Channel extends BaseEntity {

    // new fields
    private String channelName;

    //  extra fields
    private List<User> users;
    private List<Message> messages;


    public Channel(String channelName) {
        super();
        this.channelName = channelName;

        // added
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    // ============ User ============ (linked with user)


    // utility methods
    public void addUser(User user) {

        if (!users.contains(user)) {
            users.add(user);

            // extra safe checking
            if (!user.getChannels().contains(this)) {
                    user.addChannel(this);
            }
        }
    }

    public void deleteUser(User user) {

        if (users.contains(user)) {
            users.remove(user);
            if (user.getChannels().contains(this)) {
                user.deleteChannel(this);
            }
        }
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    // ============ Message ============ (linked with User & Message)

    public void addMessage(Message message) {

        if (!messages.contains(message)) {
            messages.add(message);

            if (!message.getChannel().equals(this)) {
                message.setChannel(this);
            }
        }
    }

    public void deleteMessage(Message message) {

        if (messages.contains(message)) {
            messages.remove(message);

            User user = message.getUser();
            if (user.getMessagesList().contains(message)) {
                user.deleteMessage(message);
            }

        }
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<Message> newList)
    {
        messages = newList;
    }

    // ============ Channel name ============

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    @Override
    public String toString() {
        return "\n" +
                "Channel {" + "\n" +
                "  Channel Name = '" + channelName + "',\n" +
                "  ID           = " + this.getId() + "\n" +
                "}";
    }

}

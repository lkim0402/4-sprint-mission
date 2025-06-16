package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Message extends BaseEntity {


    // new fields
    private User user;
    private String message;
    private Channel channel;

    @Serial
    private static final long serialVersionUID = 1L; // message's version

    public Message(User user, String message, Channel channel) {
        super();

        // new fields
        this.user = user;
        this.channel = channel;
        this.message = message;
    }

    // ============ Channel ============

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    // ============ User (sender) ============

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ============ Message ============

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "\n" +
                "Message {" + "\n" +
                "  ID         = " + this.getId() + ",\n" +
                "  Username   = " + user.getUserName() + ",\n" +
                "  Message    = '" + message + "',\n" +
                "  Channel    = " + channel.getChannelName() + "\n" +
                "}";
    }

}

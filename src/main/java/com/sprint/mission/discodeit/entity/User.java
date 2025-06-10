package com.sprint.mission.discodeit.entity;

import java.util.*;
import java.util.regex.Pattern;

public class User extends BaseEntity {

    // new fields
    private String email;
    private String password;
    private String userName;

    // extra fields
    private UserStatus userStatus;
    private final List<Channel> channels;
    private final List<Message> messages;

    /**
     * member status (user statuc -> enum
     * 활동, 휴면 (메세지 작성 못함), 탈퇴한 회원
     * 전체 회훤 중에 -> 활동중인 회원만
     */

    public User(String userName, String email, String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;

        // added
        this.userStatus = UserStatus.ACTIVE;
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    // ============ Channel ============ (linked with channel)
    // User에게 channel을 추가하거나 삭제하면 (channelList 수정), channel의 userList로 수정됨

    public void addChannel(Channel channel) {

        if (!channels.contains(channel)) {
            channels.add(channel);

            // extra safechecking
            if (!channel.getUsers().contains(this)) {
                channel.deleteUser(this);
            }
        }
    }

    public void deleteChannel(Channel channel) {

        if (channels.contains(channel)) {
            channels.remove(channel);
            if (channel.getUsers().contains(this)) {
                channel.deleteUser(this);
            }
        }
    }

    public void deleteChannels() {
        for (Channel channel : new ArrayList<>(channels)) {
            channels.remove(channel);
            if (channel.getUsers().contains(this)) {
                channel.deleteUser(this);
            }
        }
    }

    public List<Channel> getChannels() {
        return channels;
    }

    // ============ Messages ============ (linked with Message & Channel)

    public void addMessage(Message message) {

        if (!messages.contains(message)) {
            messages.add(message);

            // updating the message's user (sender)
            if (!message.getUser().equals(this)) {
                message.setUser(this);
            }

            // updating the message's chanel's channellist
            Channel c = message.getChannel();
            if (!c.getMessages().contains(message)) {
                c.addMessage(message);
            }


        }
    }

    // if user deletes message, also deletes the message
    // in the message's channel's channellist
    public void deleteMessage(Message message) {

        if (messages.contains(message)) {
            messages.remove(message);

            // updating the message's chanel's channellist
            Channel c = message.getChannel();
            if (c.getMessages().contains(message)) {
                c.deleteMessage(message);
            }
        }
    }

    public List<Message> getMessagesList() {
        return new ArrayList<>(messages);
    }

    // ============ channels ============

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is null or blank");
        }

        // validating the format
        // https://stackoverflow.com/questions/66934141/how-can-i-check-email-address-in-java
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (pat.matcher(email).matches()) {
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "\n" +
                "User {" + "\n" +
                "  ID        = " + this.getId() + ",\n" +
                "  status    = " + this.userStatus  + ",\n" +
                "  username  = " + this.userName + ",\n" +
                "  email     = " + this.email + ",\n" +
                "  password  = " + this.password + "\n" +
                "  updatedAt = " + this.getUpdatedAt() + "\n" +
                "}";
    }

}

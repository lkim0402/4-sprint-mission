//package com.sprint.mission.discodeit.service.file;
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.Message;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.entity.UserStatus;
//import com.sprint.mission.discodeit.repository.ChannelRepository;
//import com.sprint.mission.discodeit.repository.MessageRepository;
//import com.sprint.mission.discodeit.repository.UserRepository;
//import com.sprint.mission.discodeit.service.MessageService;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class FileMessageService implements MessageService {
//
//    private final MessageRepository messageRepository;
//    private final ChannelRepository channelRepository;
//    private final UserRepository userRepository;
//
//    public FileMessageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository) {
//
//        this.messageRepository = messageRepository;
//        this.channelRepository = channelRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Message createMessage(User user, String message, Channel channel) {
//        if (user.getUserStatus() == UserStatus.WITHDRAWN) {
//            return null;
//        }
//        Message msg = new Message(user, message, channel);
//
//        user.addMessage(msg);
//        channel.addMessage(msg);
//
//        channelRepository.save(channel);
//        userRepository.save(user);
//
//        messageRepository.save(msg);
//        return msg;
//    }
//
//    @Override
//    public Message findVerifiedMessage(UUID id) {
//        return messageRepository.findVerifiedMessage(id)
//                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
//    }
//
//    @Override
//    public Message updateMessage(UUID id, String message) {
//        findVerifiedMessage(id);
//
//        Message msg = findVerifiedMessage(id);
//        msg.setMessage(message);
//        messageRepository.save(msg);
//        return msg;
//    }
//
//    @Override
//    public void deleteMessage(UUID id) {
//        messageRepository.deleteMessage(id);
//    }
//
//    @Override
//    public List<Message> getMessages() {
//        return messageRepository.findAll();
//    }
//
//    @Override
//    public void clearMessages() {
//        List<Message> messages = new ArrayList<>();
//        messageRepository.saveAll(messages);
//    }
//}

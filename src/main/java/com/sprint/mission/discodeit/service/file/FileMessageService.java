package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileMessageService implements MessageService {

    private final MessageRepository messageRepository;

    public FileMessageService(MessageRepository messageRepository) {
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
    public Message findById(UUID id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));
    }

    @Override
    public Message updateMessage(UUID id, String message) {
        validateUserExists(findById(id));

        Message m = findById(id);
        m.setMessage(message);
        messageRepository.save(m);
        return m;
    }

    @Override
    public void deleteById(UUID id) {
        messageRepository.deleteById(id);
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

    // =========== utility methods ===========
    /**
     * 새로운 메세지를 목록에 추가합니다.
     * 이미 존재하는 메세지는 중복 추가되지 않고 정보는 덮어씌워집니다.
     *
     * @param msg 추가할 메세지 객체
     */
    private void addMessage(Message msg) {
//        isExistMessage(msg);
        messageRepository.save(msg);
    }

    /**
     * 지정된 메세지가 시스템에 존재하는지 검증합니다.
     *
     * @param msg 존재 여부를 확인할 메세지 객체
     * @throws RuntimeException 메세지가 존재하지 않는 경우 예외를 발생시킵니다
     */
    private void validateUserExists(Message msg) {
        List<Message> msgs = getMessages();

        boolean alreadyExist = msgs.stream()
                .anyMatch(c -> c.getId().equals(msg.getId()));

        if (!alreadyExist) {
            throw new RuntimeException("Message " + msg.getId() + " does not exist");
        }
    }

}

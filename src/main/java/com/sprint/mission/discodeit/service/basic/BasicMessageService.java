package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.MessageService.*;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponseDto create(MessageRequestDto messageRequestDto) {

        Message newMessage = messageMapper.toMessage(messageRequestDto);
        UUID channelId = newMessage.getChannelId();
        UUID authorId = newMessage.getAuthorId();
        if (!channelRepository.existsById(newMessage.getChannelId())) {
            throw new NoSuchElementException("Channel not found with id " + channelId);
        }
        if (!userRepository.existsById(newMessage.getAuthorId())) {
            throw new NoSuchElementException("Author not found with id " + authorId);
        }

        Message savedMessage = messageRepository.save(newMessage);

        // saving the binary content
        List<MultipartFile> attachments = messageRequestDto.attachments();
        for (MultipartFile attachment : attachments) {
            BinaryContent binaryContent = binaryContentMapper.toBinaryContent(authorId, savedMessage.getId(), attachment);
            binaryContent.setUserId(authorId);
            binaryContent.setMessageId(savedMessage.getId());
            binaryContentRepository.save(binaryContent);
        }
        return messageMapper.toMessageResponseDto(savedMessage);
    }

    @Override
    public MessageResponseDto find(UUID messageId) {
        Message msg =  messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));

        return messageMapper.toMessageResponseDto(msg);
    }

    @Override
    public MessageResponseDtos findallByChannelId(UUID channelId) {

        List<Message> messageList = messageRepository.findByChannelId(channelId);
        return messageMapper.toMessageResponseDtos(
                messageList
        );
    }

    @Override
    public UpdateMessageResponseDto update(UUID messageId, UpdateMessageRequestDto updateMessageRequestDto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));

        message.update(updateMessageRequestDto.content());
        return messageMapper.toUpdateMessageResponseDto(messageRepository.save(message));
    }

    @Override
    public void delete(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }

        Message messageToDelete = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + id + " not found"));

        // deleting binary contents attached to the message
        List<BinaryContent> optionalBinaryContents = binaryContentRepository.findByUserId(messageToDelete.getAuthorId());

        if (!optionalBinaryContents.isEmpty()) {
            for (BinaryContent binaryContent : optionalBinaryContents) {
                binaryContentRepository.deleteById(binaryContent.getId());
            }
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }
}

package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageService.MessageRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageResponseDtos;
import com.sprint.mission.discodeit.dto.MessageService.UpdateMessageRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.Mapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final Mapper mapper;

    @Override
    public Message create(MessageRequestDto messageRequestDto) {

        Message newMessage = mapper.toMessage(messageRequestDto);
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
        List<BinaryContentRequestDto> attachments = messageRequestDto.attachments();
        for (BinaryContentRequestDto attachment : attachments) {
            BinaryContent binaryContent = mapper.toBinaryContent(attachment, authorId, savedMessage.getId());
            binaryContent.setMessageId(savedMessage.getId());
            binaryContentRepository.save(binaryContent);
        }
        return savedMessage;
    }

    @Override
    public MessageResponseDto find(UUID messageId) {
        Message msg =  messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));

        return mapper.toMessageResponseDto(msg);
    }

    @Override
    public MessageResponseDtos findallByChannelId(UUID channelId) {

        List<Message> messageList = messageRepository.findByChannelId(channelId);
        return mapper.toMessageResponseDtos(
                messageList
        );
    }

    @Override
    public Message update(UUID messageId, UpdateMessageRequestDto updateMessageRequestDto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(updateMessageRequestDto.content());
        return messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        UUID id = message.getId();
        if (!messageRepository.existsById(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }

        // deleting binary contents attached to the message
        Optional<List<BinaryContent>> optionalBinaryContents = binaryContentRepository.findByMessageId(message.getId());

        if (optionalBinaryContents.isPresent()) {
            List<BinaryContent> binaryContents = optionalBinaryContents.get();
            for (BinaryContent binaryContent : binaryContents) {
                binaryContentRepository.deleteById(binaryContent.getId());
            }
        }
        messageRepository.deleteById(id);
    }
}

package com.sprint.mission.discodeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private ChannelRepository channelRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private MessageMapper messageMapper;

  @Mock
  private BinaryContentStorage binaryContentStorage;

  @Mock
  private BinaryContentRepository binaryContentRepository;

  @Mock
  private PageResponseMapper pageResponseMapper;

  @InjectMocks
  private BasicMessageService messageService;

  @DisplayName("메세지 생성 테스트")
  @Test
  void createMessageTest() {
    // =============== given ===============
    UUID channelId = UUID.randomUUID();
    UUID authorId = UUID.randomUUID();
    MessageCreateRequest messageCreateRequest = new MessageCreateRequest(
        "Message Content!",
        channelId,
        authorId
    );

    // binaryContentCreateRequest
    byte[] fileBytes = "Hello World".getBytes();
    BinaryContentCreateRequest binaryRequest = new BinaryContentCreateRequest(
        "test.txt",
        "text/plain",
        fileBytes
    );
    List<BinaryContentCreateRequest> binaryRequests = List.of(binaryRequest);
    UUID binaryContentId = UUID.randomUUID();
    BinaryContent savedBinaryContent = new BinaryContent("test.txt", (long) fileBytes.length,
        "text/plain");
    ReflectionTestUtils.setField(savedBinaryContent, "id", binaryContentId);

    // expected MessageDto
    MessageDto expectedDto = new MessageDto(
        UUID.randomUUID(),
        Instant.now(),
        Instant.now(),
        "Message Content!",
        channelId,
        new UserDto(
            authorId,
            "username",
            "email",
            null,
            true
        ),
        null
    );
    Channel expectedChannel = new Channel(ChannelType.PUBLIC, "Test Public Channel",
        "This is a test public channel.");
    ReflectionTestUtils.setField(expectedChannel, "id", channelId);

    User expectedUser = new User("Bob", "bob@gmail.com", "1234", null);
    ReflectionTestUtils.setField(expectedUser, "id", authorId);

    Message mockSavedMessage = mock(Message.class);

    when(channelRepository.findById(channelId)).thenReturn(Optional.of(expectedChannel));
    when(userRepository.findById(authorId)).thenReturn(Optional.of(expectedUser));
    when(binaryContentRepository.save(any(BinaryContent.class))).thenReturn(savedBinaryContent);
    when(messageRepository.save(any(Message.class))).thenReturn(mockSavedMessage);
    when(messageMapper.toDto(any(Message.class))).thenReturn(expectedDto);

    // =============== when ===============
    MessageDto actualDto = messageService.create(messageCreateRequest, binaryRequests);

    // =============== then ===============
    assertEquals(expectedDto.content(), actualDto.content());
    assertEquals(expectedDto.channelId(), actualDto.channelId());
    assertEquals(expectedDto.author().id(), actualDto.author().id());
    verify(channelRepository).findById(channelId);
    verify(userRepository).findById(authorId);
    verify(binaryContentStorage).put(eq(binaryContentId), eq(fileBytes));
    verify(messageRepository).save(any(Message.class));
    verify(messageMapper).toDto(any(Message.class));
  }

}

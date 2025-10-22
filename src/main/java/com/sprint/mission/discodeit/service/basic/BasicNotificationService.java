package com.sprint.mission.discodeit.service.basic;


import com.sprint.mission.discodeit.dto.data.NotificationDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.NotificationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicNotificationService implements NotificationService {

  private final MessageRepository messageRepository;

  @Override
  public List<NotificationDto> getNotificationsForUser(String username) {
    return messageRepository.findUnreadMessageForUser(username)
        .stream()
        .map(this::toDto)
        .toList();
  }

  private NotificationDto toDto(Message message) {
    return new NotificationDto(
        message.getId(),
        message.getCreatedAt(),
        message.getAuthor().getId(),
        message.getAuthor().getUsername() + "(# " + message.getChannel() + ")",
        message.getContent()
    );

  }
}

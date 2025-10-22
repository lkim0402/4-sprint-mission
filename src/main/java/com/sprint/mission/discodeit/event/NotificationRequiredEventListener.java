package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.dto.data.NotificationDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationRequiredEventListener {

  private final ReadStatusRepository readStatusRepository;
  private final NotificationRepository notificationRepository;

  @TransactionalEventListener
  public void on(MessageCreatedEvent event) {
    Message message = event.message();
    Channel channel = message.getChannel();
    User user = message.getAuthor();

    List<ReadStatus> readStatusList = readStatusRepository.findByChannelIdAndNotificationEnabledTrue(
        channel.getId());

    List<Notification> notifications = readStatusList.stream()
        // 해당 메시지를 보낸 사람은 알림 대상에서 제외
        .filter(r -> !r.getUser().getId().equals(user.getId()))
        .map(r ->
            new Notification(
                r.getUser(),
                String.format("%s (#%s)", user.getUsername(), channel.getName()),
                message.getContent()
            )
        ).toList();

    notificationRepository.saveAll(notifications);
  }

  @TransactionalEventListener
  public void on(RoleUpdatedEvent event) {
    User user = event.user();
    Notification notification = new Notification(
        user,
        "권한이 변경되었습니다.",
        String.format("%s -> %s", user.getRole(), event.role())
    );
    notificationRepository.save(notification);
  }
}

package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.NotificationDto;
import com.sprint.mission.discodeit.service.NotificationService;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<NotificationDto>> sendNotification(@AuthenticationPrincipal
  UserPrincipal userPrincipal) {
    String username = userPrincipal.getName();

    List<NotificationDto> notificationDtos = notificationService.getNotificationsForUser(username);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(notificationDtos);
  }

  @DeleteMapping(path = "{notificationId}")
  public ResponseEntity<Void> deleteNotification(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @PathVariable("notificationId") UUID notificationId
  ) {

    // ReadStatus 엔티티의 lastReadAt 시간을 업데이트

    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();

  }

}

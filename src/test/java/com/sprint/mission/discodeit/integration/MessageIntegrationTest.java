package com.sprint.mission.discodeit.integration;

import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class MessageIntegrationTest {

  @Autowired
  MessageService messageService;
  @Autowired
  MessageRepository messageRepository;

  /**
   * POST - create
   */
  @DisplayName("메세지 생성과 저장 테스트")
  @Test
  @Transactional
  void CreatePublicMessageAndSavesMessage() {

  }

  /**
   * PATCH - update
   */
  @DisplayName("메세지 수정과 저장 테스트")
  @Test
  @Transactional
  void updateMessageAndSavesMessage() {

  }

  /**
   * DELETE
   */
  @DisplayName("메세지 삭제 테스트")
  @Test
  @Transactional
  void deleteMessage() {
  }

  /**
   * GET - findAllByChannelId
   *
   * @throws Exception
   */
  @DisplayName("채널 Id로 모든 메세지 조회 테스트")
  @Test
  void findAllMessages() {

  }
}

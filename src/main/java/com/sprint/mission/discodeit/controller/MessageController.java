package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.MessageDto.*;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Message", description = "Message 관련 API")
public class MessageController {

  private final MessageService messageService;

  @Operation(summary = "Message 생성")
  @PostMapping
  public ResponseEntity<MessageResponseDto> sendMessage(
      @ModelAttribute MessageRequestDto messageRequestDto
  ) {
    MessageResponseDto messageResponseDto = messageService.create(messageRequestDto);
    return ResponseEntity.ok().body(messageResponseDto);
  }

  @Operation(summary = "Message 수정")
  @PatchMapping("/{message-id}") // 수정
  public ResponseEntity<MessageUpdateResponseDto> updateMessage(
      @PathVariable("message-id") UUID messageId,
      @RequestBody MessageUpdateRequestDto messageUpdateRequestDto
  ) {
    MessageUpdateResponseDto messageUpdateResponseDto = messageService.update(messageId,
        messageUpdateRequestDto);
    return ResponseEntity.ok(messageUpdateResponseDto);
  }

  @Operation(summary = "Message 삭제")
  @DeleteMapping("/{message-id}") // 삭제
  public ResponseEntity<String> deleteMessage(@PathVariable("message-id") UUID messageId
  ) {
    messageService.delete(messageId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "특정 Channel의 모든 Message 조회")
  @GetMapping // 채널의 모든 메세지 조회
  public ResponseEntity<MessageResponseDtos> getMessagesByChannel(
      @RequestParam("channel-id") UUID channelId
  ) {
    MessageResponseDtos messageResponseDtos = messageService.findallByChannelId(channelId);
    return ResponseEntity.ok(messageResponseDtos);
  }

}

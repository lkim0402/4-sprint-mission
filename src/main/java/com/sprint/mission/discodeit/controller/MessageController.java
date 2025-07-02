package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.MessageService.*;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor

public class MessageController {

    private final MessageService messageService;

    /**
     * [x] 메시지를 보낼 수 있다.
     * [x] 메시지를 수정할 수 있다.
     * [x] 메시지를 삭제할 수 있다.
     * [x] 특정 채널의 메시지 목록을 조회할 수 있다.
     */

    @PostMapping // 메세지 보냄
    public ResponseEntity<MessageResponseDto> sendMessage(@ModelAttribute MessageRequestDto messageRequestDto
    ) {
        MessageResponseDto messageResponseDto = messageService.create(messageRequestDto);
        return ResponseEntity.ok().body(messageResponseDto);
    }

    @PatchMapping("/{message-id}") // 수정
    public ResponseEntity<UpdateMessageResponseDto> updateMessage(@PathVariable("message-id") UUID messageId,
                                                                  @RequestBody UpdateMessageRequestDto updateMessageRequestDto
    ) {
        UpdateMessageResponseDto updateMessageResponseDto = messageService.update(messageId, updateMessageRequestDto);
        return ResponseEntity.ok(updateMessageResponseDto);
    }

    @DeleteMapping("/{message-id}") // 삭제
    public ResponseEntity<String> deleteMessage(@PathVariable("message-id") UUID messageId
    ) {
        messageService.delete(messageId);
        return ResponseEntity.ok().body("Message successfully deleted");
    }

    @GetMapping // 채널의 모든 메세지 조회
    public ResponseEntity<MessageResponseDtos> getMessagesByChannel(@RequestParam("channel-id") UUID channelId
    ) {
        MessageResponseDtos messageResponseDtos = messageService.findallByChannelId(channelId);
        return ResponseEntity.ok(messageResponseDtos);
    }

}

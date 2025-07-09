package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.AuthDto;
import com.sprint.mission.discodeit.dto.MessageDto.*;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  // ============================== POST - Message 생성 ==============================
  @Operation(summary = "Message 생성", operationId = "create_2")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Message가 성공적으로 생성됨"),
      @ApiResponse(responseCode = "404", description = "Channel 또는 User를 찾을 수 없음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "Channel | Author with id {channelId | authorId} not found"))
      )
  })
  @PostMapping
  public ResponseEntity<MessageResponseDto> sendMessage(
      @ModelAttribute MessageRequestDto messageRequestDto
  ) {
    MessageResponseDto messageResponseDto = messageService.create(messageRequestDto);
    return ResponseEntity.ok().body(messageResponseDto);
  }

  // ============================== PATCH - Message 수정 ==============================
  @Operation(summary = "Message 내용 수정", operationId = "update_2")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Message가 성공적으로 수정됨"
      ),
      @ApiResponse(responseCode = "404", description = "Message를 찾을 수 없음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "Message with id {messageId} not found"))
      )
  })
  @PatchMapping("/{messageId}") // 수정
  public ResponseEntity<MessageUpdateResponseDto> updateMessage(
      @Parameter(description = "수정할 Message ID")
      @PathVariable(value = "messageId", required = true) UUID messageId,
      @RequestBody MessageUpdateRequestDto messageUpdateRequestDto
  ) {
    MessageUpdateResponseDto messageUpdateResponseDto = messageService.update(messageId,
        messageUpdateRequestDto);
    return ResponseEntity.ok(messageUpdateResponseDto);
  }

  // ============================== DELETE - Message 삭제 ==============================
  @Operation(summary = "Message 삭제", operationId = "delete_1")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Message가 성공적으로 삭제됨"
      ),
      @ApiResponse(responseCode = "404", description = "Message를 찾을 수 없음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "Message with id {messageId} not found"))
      )
  })
  @DeleteMapping("/{messageId}") // 삭제
  public ResponseEntity<Void> deleteMessage(
      @Parameter(description = "삭제할 Message ID") @PathVariable("messageId") UUID messageId
  ) {
    messageService.delete(messageId);
    return ResponseEntity.noContent().build();
  }

  // ============================== GET - Channel의 Message 목록 조회 ==============================
  @Operation(summary = "Channel의 Message 목록 조회", operationId = "findAllByChannelId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Message 목록 조회 성공",
          content = @Content(mediaType = "*/*",
              schema = @Schema(implementation = AuthDto.LoginResponse.class))
      )
  })
  @GetMapping
  public ResponseEntity<MessageResponseDtos> getMessagesByChannel(
      @Parameter(description = "조회할 Channel ID") @RequestParam("channelId") UUID channelId
  ) {
    MessageResponseDtos messageResponseDtos = messageService.findallByChannelId(channelId);
    return ResponseEntity.ok(messageResponseDtos);
  }

}

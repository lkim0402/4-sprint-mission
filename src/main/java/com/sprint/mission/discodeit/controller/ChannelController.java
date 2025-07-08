package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelDto.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
@Tag(name = "Channel", description = "Channel 관련 API")
public class ChannelController {

  private final ChannelService channelService;
  private final ChannelMapper channelMapper;

  @Operation(summary = "Public Channel 생성")
  @PostMapping("/public")
  public ResponseEntity<ChannelResponseDto> createPublicChannel(
      @RequestBody PublicChannelRequestDto publicChannelRequestDto
  ) {
    ChannelResponseDto channelResponseDto = channelService.createPublic(publicChannelRequestDto);
    return ResponseEntity.ok().body(channelResponseDto);
  }

  @Operation(summary = "Private Channel 생성")
  @PostMapping("/private")
  public ResponseEntity<ChannelResponseDto> createPrivateChannel(
      @RequestBody PrivateChannelRequestDto privateChannelRequestDto
  ) {
    ChannelResponseDto channelResponseDto = channelService.createPrivate(privateChannelRequestDto);
    return ResponseEntity.ok().body(channelResponseDto);
  }

  @Operation(summary = "Channel 수정")
  @PatchMapping("/{channel-id}")
  public ResponseEntity<ChannelResponseDto> updatePublicChannel(
      @PathVariable("channel-id") UUID channelId,
      @RequestBody ChannelUpdateRequestDto channelUpdateRequestDto
  ) {
    ChannelUpdateResponseDto channelUpdateResponseDto = channelService.update(channelId,
        channelUpdateRequestDto);
    return ResponseEntity.ok(channelMapper.toChannelResponseDto(channelUpdateResponseDto));
  }

  @Operation(summary = "Channel 삭제")
  @DeleteMapping("/{channel-id}")
  public ResponseEntity<String> deleteChannel(@PathVariable("channel-id") UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity.ok().body("Channel deleted successfully");
  }

  @Operation(summary = "특정 User의 Channel 조회")
  @GetMapping
  public ResponseEntity<ChannelResponseDtos> getChannelsByUser(@RequestParam("user-id") UUID userId
  ) {
    ChannelResponseDtos channelResponseDtos = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(channelResponseDtos);
  }
}

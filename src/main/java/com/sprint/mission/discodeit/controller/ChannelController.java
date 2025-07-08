package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelDto.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.service.ChannelService;
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

  // ========= 채널을 1개로 통합?
  @PostMapping("/public") // 공개 채널 생성
  public ResponseEntity<ChannelResponseDto> createPublicChannel(
      @RequestBody PublicChannelRequestDto publicChannelRequestDto
  ) {
    ChannelResponseDto channelResponseDto = channelService.createPublic(publicChannelRequestDto);
    return ResponseEntity.ok().body(channelResponseDto);
  }

  @PostMapping("/private")// 비공개 채널 생성
  public ResponseEntity<ChannelResponseDto> createPrivateChannel(
      @RequestBody PrivateChannelRequestDto privateChannelRequestDto
  ) {
    ChannelResponseDto channelResponseDto = channelService.createPrivate(privateChannelRequestDto);
    return ResponseEntity.ok().body(channelResponseDto);
  }

  @PatchMapping("/{channel-id}")
  public ResponseEntity<ChannelResponseDto> updatePublicChannel(
      @PathVariable("channel-id") UUID channelId,
      @RequestBody ChannelUpdateRequestDto channelUpdateRequestDto
  ) {
    ChannelUpdateResponseDto channelUpdateResponseDto = channelService.update(channelId,
        channelUpdateRequestDto);
    return ResponseEntity.ok(channelMapper.toChannelResponseDto(channelUpdateResponseDto));
  }

  @DeleteMapping("/{channel-id}")
  public ResponseEntity<String> deleteChannel(@PathVariable("channel-id") UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity.ok().body("Channel deleted successfully");
  }

  @GetMapping
  public ResponseEntity<ChannelResponseDtos> getChannelsByUser(@RequestParam("user-id") UUID userId
  ) {
    ChannelResponseDtos channelResponseDtos = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(channelResponseDtos);
  }
}

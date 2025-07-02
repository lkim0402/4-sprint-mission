package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.ChannelService.*;
import com.sprint.mission.discodeit.dto.UserService.*;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final ChannelMapper channelMapper;

    /**
     * [x] 공개 채널을 생성할 수 있다.
     * [x] 비공개 채널을 생성할 수 있다.
     * [x] 공개 채널의 정보를 수정할 수 있다.
     * [x] 채널을 삭제할 수 있다.
     * [x] 특정 사용자가 볼 수 있는 모든 채널 목록을 조회할 수 있다.
     */

    // ========= 채널을 1개로 통합?
    @PostMapping("/public") // 공개 채널 생성
    public ResponseEntity<ChannelResponseDto> createPublicChannel(@RequestBody PublicChannelRequestDto publicChannelRequestDto
    ) {
        ChannelResponseDto channelResponseDto = channelService.createPublic(publicChannelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(channelResponseDto);
    }

    @PostMapping("/private")// 비공개 채널 생성
    public ResponseEntity<ChannelResponseDto> createPrivateChannel(@RequestBody PrivateChannelRequestDto privateChannelRequestDto
    ) {
        ChannelResponseDto channelResponseDto = channelService.createPrivate(privateChannelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(channelResponseDto);
    }

    @PatchMapping("/{channel-id}") // 수정
    public ResponseEntity<UpdateChannelResponseDto> updatePublicChannel(@PathVariable("channel-id") UUID channelId,
                                                                        @RequestBody UpdateChannelRequestDto updateChannelRequestDto
    ) {
        UpdateChannelResponseDto updateChannelResponseDto = channelService.update(channelId, updateChannelRequestDto);
        return ResponseEntity.ok(updateChannelResponseDto);
    }

    @DeleteMapping("/{channel-id}") // 채널 삭제
    public ResponseEntity<String> deleteChannel(@PathVariable("channel-id") UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping// 사용자의 모든 채널 조회
    public ResponseEntity<ChannelResponseDtos> getChannelsByUser(@RequestParam("user-id") UUID userId
    ) {
        ChannelResponseDtos channelResponseDtos = channelService.findAllByUserId(userId);
        return ResponseEntity.ok(channelResponseDtos);
    }
}

package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusResponseDtos;
import com.sprint.mission.discodeit.dto.ReadStatusService.UpdateReadStatusDto;
import com.sprint.mission.discodeit.dto.UserStatusService.UpdateUserStatusDto;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/readStatus")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    /**
     * [x] 특정 채널의 메시지 수신 정보를 생성할 수 있다.
     * [x] 특정 채널의 메시지 수신 정보를 수정할 수 있다.
     * [x] 특정 사용자의 메시지 수신 정보를 조회할 수 있다.
     */

    @PostMapping // 등록
    public ResponseEntity<ReadStatusResponseDto> createReadStatus(@RequestBody ReadStatusRequestDto readStatusRequestDto
    ) {
        ReadStatusResponseDto readStatusResponseDto = readStatusService.create(readStatusRequestDto);
        return ResponseEntity.ok().body(readStatusResponseDto);
    }

    @PatchMapping("/{readStatus-id}") // readStatus 업데이트
    public ResponseEntity<String>  updateUserStatus(@PathVariable("readStatus-id") UUID readStatusId
    ) {
        readStatusService.update(readStatusId);
        return ResponseEntity.ok().body("ReadStatus successfully updated");
    }

    @GetMapping// 특정 사용자의 메시지 수신 정보 조회
    public ResponseEntity<ReadStatusResponseDtos> getReadStatusByUserId(@RequestParam("user-Id") UUID userId
    ) {
        ReadStatusResponseDtos readStatusResponseDtos = readStatusService.findAllByUserId(userId);
        return ResponseEntity.ok(readStatusResponseDtos);
    }
}

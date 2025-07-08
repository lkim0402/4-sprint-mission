package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusDto.*;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/readStatus")
@RequiredArgsConstructor
@Tag(name = "ReadStatus", description = "ReadStatus 관련 API")
public class ReadStatusController {

  private final ReadStatusService readStatusService;

  @Operation(summary = "ReadStatus 생성")
  @PostMapping
  public ResponseEntity<ReadStatusResponseDto> createReadStatus(
      @RequestBody ReadStatusRequestDto readStatusRequestDto
  ) {
    ReadStatusResponseDto readStatusResponseDto = readStatusService.create(readStatusRequestDto);
    return ResponseEntity.ok().body(readStatusResponseDto);
  }

  @Operation(summary = "ReadStatus 수정")
  @PatchMapping("/{readStatus-id}")
  public ResponseEntity<String> updateUserStatus(@PathVariable("readStatus-id") UUID readStatusId
  ) {
    readStatusService.update(readStatusId);
    return ResponseEntity.ok().body("ReadStatus successfully updated");
  }

  @Operation(summary = "특정 User의 ReadStatus 조회")
  @GetMapping
  public ResponseEntity<ReadStatusResponseDtos> getReadStatusByUserId(
      @RequestParam("user-Id") UUID userId
  ) {
    ReadStatusResponseDtos readStatusResponseDtos = readStatusService.findAllByUserId(userId);
    return ResponseEntity.ok(readStatusResponseDtos);
  }
}

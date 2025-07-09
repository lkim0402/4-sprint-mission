package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.service.BinaryContentService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
@Tag(name = "BinaryContent", description = "Binary Content 관련 API")
public class BinaryFileController {

  private final BinaryContentService binaryContentService;

  // ============================== GET - 첨부 파일 조회 ==============================
  @Operation(summary = "첨부 파일 조회", operationId = "find")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "첨부 파일 조회 성공",
          content = @Content(mediaType = "*/*",
              schema = @Schema(implementation = BinaryContentResponseDto.class))
      ),
      @ApiResponse(responseCode = "404", description = "첨부 파일을 찾을 수 없음",
          content = @Content(mediaType = "*/*",
              examples = @ExampleObject(value = "BinaryContent with id {binaryContentId} not found"))
      )
  })
  @GetMapping("/{binaryContentId}")
  public ResponseEntity<BinaryContentResponseDto> getBinaryContent(
      @Parameter(description = "조회할 첨부 파일 ID")
      @PathVariable("binaryContentId") UUID binaryContentId) {
    BinaryContentResponseDto binaryContent = binaryContentService.find(binaryContentId);
    return ResponseEntity.ok(binaryContent);
  }

  // ============================== GET - 여러 첨부 파일 조회 ==============================
  @Operation(summary = "여러 첨부 파일 조회", operationId = "findAllByIdIn")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "첨부 파일 목록 조회 성공",
          content = @Content(mediaType = "*/*",
              schema = @Schema(type = "array", implementation = BinaryContentResponseDto.class))
      )
  })
  @GetMapping
  public ResponseEntity<List<BinaryContentResponseDto>> getBinaryContents(
      @Parameter(description = "조회할 첨부 파일 ID 목록")
      @RequestParam("binaryContentIds") List<UUID> binaryContentUUIDList
  ) {
    List<BinaryContentResponseDto> binaryContentResponseDtos = binaryContentService.findAllByIdIn(
        binaryContentUUIDList);
    return ResponseEntity.ok(binaryContentResponseDtos);
  }
}

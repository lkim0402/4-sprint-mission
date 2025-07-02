package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
public class BinaryFileController {

    private final BinaryContentService binaryContentService;

    /**
     * [x] 바이너리 파일을 1개 또는 여러 개 조회할 수 있다.
     */
    @GetMapping
    public ResponseEntity<BinaryContentResponseDtos> getBinaryContents(@RequestParam("ids") List<UUID> binaryContentUUIDList
    ) {
        BinaryContentResponseDtos binaryContentResponseDtos = binaryContentService.findAllByIdIn(binaryContentUUIDList);
        return ResponseEntity.ok(binaryContentResponseDtos);
    }

    /**
     * === 심화 ===
     * [x]  BinaryContent 파일 조회
     */
    @GetMapping("/find")
    public ResponseEntity<BinaryContent> getBinaryContent(@RequestParam("binaryContentId") UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(binaryContent);
    }

    /**
     * 심화 부분 적용 전
     */
//    @GetMapping("/binaryFile/{binaryfile-id}")
//    public ResponseEntity<BinaryContentResponseDto> getBinaryContent(@PathVariable("binaryfile-id")UUID binaryContentId
//    ) {
//        BinaryContentResponseDto binaryContentResponseDto = binaryContentService.find(binaryContentId);
//        return ResponseEntity.ok(binaryContentResponseDto);
//    }
}

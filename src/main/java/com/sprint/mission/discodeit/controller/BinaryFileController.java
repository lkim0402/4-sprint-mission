package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/binaryFile")
@RequiredArgsConstructor
public class BinaryFileController {

    private final BinaryContentService binaryContentService;

    /**
     * [x] 바이너리 파일을 1개 또는 여러 개 조회할 수 있다.
     */

    @GetMapping("/{binaryfile-id}")
    public ResponseEntity<BinaryContentResponseDto> getBinaryFile(
            @PathVariable("binaryfile-id")UUID binaryContentId
    ) {
        BinaryContentResponseDto binaryContentResponseDto = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(binaryContentResponseDto);
    }

    @GetMapping
    public ResponseEntity<BinaryContentResponseDtos> getBinaryFiles(
            @RequestParam("ids") List<UUID> binaryContentUUIDList
    ) {
        BinaryContentResponseDtos binaryContentResponseDtos = binaryContentService.findAllByIdIn(binaryContentUUIDList);
        return ResponseEntity.ok(binaryContentResponseDtos);
    }
}

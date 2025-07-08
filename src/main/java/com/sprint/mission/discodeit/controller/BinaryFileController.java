package com.sprint.mission.discodeit.controller;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
@Tag(name = "BinaryFile", description = "Binary File 관련 API")
public class BinaryFileController {

    private final BinaryContentService binaryContentService;

    @GetMapping
    public ResponseEntity<BinaryContentResponseDtos> getBinaryContents(@RequestParam("ids") List<UUID> binaryContentUUIDList
    ) {
        BinaryContentResponseDtos binaryContentResponseDtos = binaryContentService.findAllByIdIn(binaryContentUUIDList);
        return ResponseEntity.ok(binaryContentResponseDtos);
    }

    @GetMapping("/find")
    public ResponseEntity<BinaryContent> getBinaryContent(@RequestParam("binaryContentId") UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(binaryContent);
    }
}

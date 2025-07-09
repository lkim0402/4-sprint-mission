package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentMapper binaryContentMapper;

  @Override
  public BinaryContentResponseDto create(BinaryContentRequestDto binaryContentRequestDto) {

    String fileName = binaryContentRequestDto.fileName();
    byte[] bytes = binaryContentRequestDto.bytes();
    String contentType = binaryContentRequestDto.contentType();
    BinaryContent binaryContent = new BinaryContent(
        fileName,
        (long) bytes.length,
        contentType,
        bytes
    );
    return binaryContentMapper.toResponseDto(binaryContentRepository.save(binaryContent));
  }

  @Override
  public BinaryContentResponseDto find(UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
        .orElseThrow(() -> new NoSuchElementException(
            "Binary Content with id " + binaryContentId + " not found!"));

    return binaryContentMapper.toResponseDto(binaryContent);
  }

  @Override
  public List<BinaryContentResponseDto> findAllByIdIn(List<UUID> binaryContentIds) {
    return binaryContentRepository.findAllByIdIn(binaryContentIds).stream()
        .map(binaryContentMapper::toResponseDto)
        .toList();
  }

  @Override
  public void delete(UUID binaryContentId) {
    if (!binaryContentRepository.existsById(binaryContentId)) {
      throw new NoSuchElementException("BinaryContent with id " + binaryContentId + " not found!");
    }
    binaryContentRepository.deleteById(binaryContentId);
  }

  @Override
  public void deleteAll() {
    binaryContentRepository.deleteAll();
  }

}

package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
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
         BinaryContent newBinaryContent = binaryContentRepository.save(
                binaryContentMapper.toBinaryContent(
                        binaryContentRequestDto.userId(),
                        binaryContentRequestDto.messageId(),
                        binaryContentRequestDto.file()
                )
        );

        return binaryContentMapper.toBinaryContentResponseDto(newBinaryContent);
    }

    @Override
    public BinaryContentResponseDto find(UUID binaryContentId) {
        BinaryContent binaryContent =  binaryContentRepository.findById(binaryContentId)
                .orElseThrow(() -> new NoSuchElementException("Binary Content with id " + binaryContentId + " not found!"));

        return binaryContentMapper.toBinaryContentResponseDto(binaryContent);

    }

    @Override
    public BinaryContentResponseDtos findAllByIdIn(List<UUID> binaryContentIds) {
        List<BinaryContent> binaryContents = binaryContentRepository.findAllByIdIn(binaryContentIds);
        return binaryContentMapper.toBinaryContentResponseDtos(binaryContents);
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

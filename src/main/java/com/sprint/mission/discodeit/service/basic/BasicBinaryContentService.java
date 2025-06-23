package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentResponseDtos;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.Mapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;
    private final Mapper mapper;

    @Override
    public BinaryContent create(BinaryContentRequestDto binaryContentRequestDto, UUID userId, UUID messageId) {
        return binaryContentRepository.save(
                mapper.toBinaryContent(binaryContentRequestDto, userId, messageId)
        );
    }

    @Override
    public BinaryContentResponseDto find(UUID binaryContentId) {
        BinaryContent binaryContent =  binaryContentRepository.findById(binaryContentId)
                .orElseThrow(() -> new NoSuchElementException("Binary Content with id " + binaryContentId + " not found!"));

        return mapper.toBinaryContentResponseDto(binaryContent);

    }

    @Override
    public BinaryContentResponseDtos findAllByIdIn(List<UUID> binaryContentIds) {
        List<BinaryContent> binaryContents = binaryContentRepository.findAllByIdIn(binaryContentIds);
        return mapper.toBinaryContentResponseDtos(binaryContents);
    }

    @Override
    public void delete(UUID binaryContentId) {
        if (!binaryContentRepository.existsById(binaryContentId)) {
            throw new NoSuchElementException("BinaryContent with id " + binaryContentId + " not found!");
        }
        binaryContentRepository.deleteById(binaryContentId);
    }

}

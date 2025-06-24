package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class JCFBinaryContentRepository implements BinaryContentRepository {

    private final Map<UUID, BinaryContent> data; // BinaryContentId : BinaryContent
    private final Map<UUID, List<BinaryContent>> userIndex; // userId : BinaryContent

    public JCFBinaryContentRepository() {
        this.data = new HashMap<>();
        this.userIndex = new HashMap<>();
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        this.data.put(binaryContent.getId(), binaryContent);

        // get or create the list for the user and add the new content
        this.userIndex.computeIfAbsent(
                binaryContent.getUserId(),  // key
                    // mapping function (only computes when key is absent)
                    key -> new ArrayList<>()
                )
                .add(binaryContent);

        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<BinaryContent> findByUserId(UUID userId) {
        return this.userIndex.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public List<BinaryContent> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> binaryContentIds) {
        return findAll().stream()
                .filter(b -> binaryContentIds.contains(b.getId()))
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        BinaryContent deletedContent = this.data.remove(id);

        if (deletedContent != null) {
            List<BinaryContent> binaryContentList = userIndex.get(deletedContent.getUserId());
            if (binaryContentList != null) {
                binaryContentList.remove(deletedContent);

                // if binaryContentList is empty, just remove the key
                if (binaryContentList.isEmpty()) {
                    userIndex.remove(deletedContent.getUserId());
                }
            }
        }
    }
}

package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;

@Primary
@Repository
public class JCFBinaryContentRepository implements BinaryContentRepository {

    /**
     * userIndex: User Id를 key로 사용하여 O(1) 시간에 유저의 binary Content 목록을 조회합니다.
     * Key: UUID UserId, Value: List<UUID> BinaryContentIds
     * 유저 아이디로 조회할때 O(n)에서 O(1) 시간 복잡도로 줄일 수 있어서 구현했습니다.
     */

    private final Map<UUID, BinaryContent> data; // BinaryContentId : BinaryContent
    private final Map<UUID, List<UUID>> userIndex; // userId: BinaryContentId

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
                .add(binaryContent.getId());

        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<BinaryContent> findByUserId(UUID userId) {
        return toBinaryContentList(userIndex.getOrDefault(userId, Collections.emptyList()));
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
            List<UUID> binaryContentList = userIndex.get(deletedContent.getUserId());
            if (binaryContentList != null) {
                binaryContentList.remove(deletedContent.getId());

                // if binaryContentList is empty, just remove the key
                if (binaryContentList.isEmpty()) {
                    userIndex.remove(deletedContent.getUserId());
                }
            }
        }
    }

    @Override
    public void deleteAll() {
        this.data.clear();
        this.userIndex.clear();
    }

    private List<BinaryContent> toBinaryContentList(List<UUID> binaryContentUUIDList) {
        return binaryContentUUIDList.stream()
                .map(this::findById)
                .flatMap(Optional::stream) // Stream<Optional<T>> -> Stream<T>
                .toList();
    }
}

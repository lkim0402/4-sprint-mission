package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final Map<UUID, ReadStatus> data; // ReadStatusId : ReadStatus
    private final Map<UUID, List<ReadStatus>> userIndex; // UserIndexId: UserIndex
    private final Map<UUID, List<ReadStatus>> channelIndex;

    public JCFReadStatusRepository() {
        this.data = new HashMap<>();
        this.userIndex = new HashMap<>();
        this.channelIndex = new HashMap<>();
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        this.data.put(readStatus.getId(), readStatus);

        this.userIndex.computeIfAbsent(
                        readStatus.getUserId(),  // key
                        // mapping function (only when key is absent)
                        key -> new ArrayList<>()
                )
                .add(readStatus);

        this.channelIndex.computeIfAbsent(
                        readStatus.getChannelId(),  // key
                        // mapping function (only when key is absent)
                        key -> new ArrayList<>()
                )
                .add(readStatus);

        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        return this.channelIndex.getOrDefault(channelId, Collections.emptyList());
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        return this.userIndex.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Optional<ReadStatus> findByChannelAndUserId(UUID channelId, UUID userId) {
        List<ReadStatus> readStatusUserList = this.userIndex.getOrDefault(userId, Collections.emptyList());

        return readStatusUserList.stream()
                .filter(r -> r.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public List<ReadStatus> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        ReadStatus deletedReadStatus = this.data.remove(id);

        if (deletedReadStatus != null) {
            List<ReadStatus> readStatusUserList = userIndex.get(deletedReadStatus.getUserId());
            List<ReadStatus> readStatusChannelList = channelIndex.get(deletedReadStatus.getChannelId());


            if (readStatusUserList != null) {
                readStatusUserList.remove(deletedReadStatus);

                // if userList is empty, just remove the key
                if (readStatusUserList.isEmpty()) {
                    userIndex.remove(deletedReadStatus.getUserId());
                }
            }

            if (readStatusChannelList != null) {
                readStatusChannelList.remove(deletedReadStatus);

                // if channelList is empty, just remove the key
                if (readStatusChannelList.isEmpty()) {
                    channelIndex.remove(deletedReadStatus.getChannelId());
                }
            }
        }

    }
}

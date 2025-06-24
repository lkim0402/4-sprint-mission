package com.sprint.mission.discodeit.repository.jcf;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {

    /**
     * userIndex: User Id를 key로 사용하여 O(1) 시간에 유저의 ReadStatus UUID 목록을 조회합니다.
     * channelIndex: Channel Id를 key로 사용하여 O(1) 시간에 채널의 ReadStatus UUID 목록을 조회합니다.
     * 두가지 옵션 모두 유저나 채널로 조회할 때 O(n)에서 O(1) 시간 복잡도로 줄일 수 있어서 구현했습니다.
     */

    private final Map<UUID, ReadStatus> data; // ReadStatusId : ReadStatus
    private final Map<UUID, List<UUID>> userIndex; // UserId: ReadStatus UUID
    private final Map<UUID, List<UUID>> channelIndex; // ChannelId: ReadStatus UUID


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
                .add(readStatus.getId());

        this.channelIndex.computeIfAbsent(
                        readStatus.getChannelId(),  // key
                        // mapping function (only when key is absent)
                        key -> new ArrayList<>()
                )
                .add(readStatus.getId());

        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        return toReadStatusList(this.channelIndex.getOrDefault(channelId, Collections.emptyList()));
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        return toReadStatusList(this.userIndex.getOrDefault(userId, Collections.emptyList()));
    }

    @Override
    public Optional<ReadStatus> findByChannelAndUserId(UUID channelId, UUID userId) {
        List<UUID> readStatusUserList = this.userIndex.getOrDefault(userId, Collections.emptyList());

        return readStatusUserList.stream()
                .map(this.data::get)
                .filter(Objects::nonNull)
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
            List<UUID> readStatusUserList = userIndex.get(deletedReadStatus.getUserId());
            List<UUID> readStatusChannelList = channelIndex.get(deletedReadStatus.getChannelId());


            if (readStatusUserList != null) {
                readStatusUserList.remove(deletedReadStatus.getId());

                // if userList is empty, just remove the key
                if (readStatusUserList.isEmpty()) {
                    userIndex.remove(deletedReadStatus.getUserId());
                }
            }

            if (readStatusChannelList != null) {
                readStatusChannelList.remove(deletedReadStatus.getId());

                // if channelList is empty, just remove the key
                if (readStatusChannelList.isEmpty()) {
                    channelIndex.remove(deletedReadStatus.getChannelId());
                }
            }
        }
    }

    private List<ReadStatus> toReadStatusList(List<UUID> readStatusUUIDList) {
        return readStatusUUIDList.stream()
                .map(this.data::get)
                .toList();
    }
}

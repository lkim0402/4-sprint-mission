package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {

    /**
     * 채널 목록 전체를 저장합니다.
     *
     * @param channels 저장할 채널 목록
     */
    void saveAll(List<Channel> channels);

    /**
     * 채널 목록 전체를 반환합니다.
     *
     * @return 전체 채널 목록
     */
    List<Channel> findAll();

    /**
     * 주어진 ID에 해당하는 채널을 조회합니다.
     *
     * @param id 조회할 채널의 ID
     * @return 해당 채널이 존재하면 Optional에 담아 반환, 없으면 빈 Optional 반환
     */
    Optional<Channel> findById(UUID id);

    /**
     * 채널을 저장합니다.
     *
     * @param channel 저장할 채널
     */
    void save(Channel channel);

    /**
     * 채널을 삭제합니다.
     *
     * @param id 삭제할 채널의 ID
     */
    void deleteById(UUID id);
}

package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    /**
     * 메시지 목록 전체를 저장합니다.
     *
     * @param messages 저장할 메시지 목록
     */
    void saveAll(List<Message> messages);

    /**
     * 모든 메시지를 반환합니다.
     *
     * @return 전체 메시지 목록
     */
    List<Message> findAll();

    /**
     * 주어진 ID에 해당하는 메시지를 조회합니다.
     *
     * @param id 조회할 메시지의 ID
     * @return 해당 ID의 메시지가 존재하면 Optional에 담아 반환, 없으면 빈 Optional 반환
     */
    Optional<Message> findById(UUID id);

    /**
     * 메세지를 저장합니다.
     *
     * @param message 저장할 채널
     */
    void save(Message message);

    /**
     * 메세지를 삭제합니다.
     *
     * @param id 삭제할 메세지의 ID
     */
    void deleteById(UUID id);
}

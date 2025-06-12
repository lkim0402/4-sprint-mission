package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    /**
     * 사용자 목록 전체를 저장합니다.
     *
     * @param users 저장할 사용자 목록
     */
    void saveAll(List<User> users);

    /**
     * 모든 사용자를 반환합니다.
     *
     * @return 전체 사용자 목록
     */
    List<User> findAll();

    /**
     * 주어진 ID에 해당하는 사용자를 조회합니다.
     *
     * @param id 조회할 사용자의 ID
     * @return 해당 ID의 사용자가 존재하면 Optional에 담아 반환, 없으면 빈 Optional 반환
     */
    Optional<User> findById(UUID id);

    /**
     * 유저를 저장합니다.
     * 등록되지 않은 유저면 새로 저장이 되고, 등록되어 있다면 정보를 갱신합니다.
     * @param id 저장할 유저의 id
     * @param user 저장할 유저 객체
     */
    void save(UUID id, User user);

    /**
     * 유저를 삭제합니다.
     *
     * @param id 삭제할 유저의 ID
     */
    void deleteById(UUID id);
}

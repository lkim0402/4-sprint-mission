package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
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
    Optional<User> findUser(UUID id);
}

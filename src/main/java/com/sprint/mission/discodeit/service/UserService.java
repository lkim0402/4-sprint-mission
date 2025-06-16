package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    // 생성(Create)
    /**
     * 새로운 유저를 생성합니다.
     * @param userName 유저 이름
     * @param email 이메일 주소
     * @param password 비밀번호
     * @return 생성된 유저 객체
     */
    User createUser(String userName, String email, String password);

    // 조회(Read)
    /**
     * 주어진 UUID에 해당하는 유저를 조회합니다.
     * @param userId 유저의 UUID
     * @return 조회된 유저 객체 (없을 경우 null)
     */
    User findVerifiedUser(UUID userId);

    /**
     * 전체 유저 목록을 반환합니다.
     * @return 유저 리스트
     */
    List<User> getUsers();

    /**
     * Status에 해당하는 유저 목록을 반환합니다.
     * @return status를 가진 유저 리스트
     */
    List<User> getUsersByStatus(UserStatus userStatus);

    // 수정(Update)
    /**
     * 주어진 UUID를 가진 유저 정보를 수정거나, 존재하지 않을경우 추가합니다.
     *
     * 전달된 partialUser 객체는 수정하고자 하는 필드만 값을 가지며,
     * 그 외의 필드는 null일 수 있습니다.
     * 기존 유저 객체의 해당 필드만 업데이트됩니다.
     * 수정 가능한 필드는 userName, email, password, userStatus 입니다.
     * @param id 수정할 경우, 새로운 정보를 갱신할 유저의 id
     * @param user 새로운 유저 정보
     */
    void updateUser(UUID id, User user);

//    User updateUserStatus(UUID id, UserStatus userStatus);


    // 삭제(Delete)
    /**
     * UUID에 해당하는 유저의 status를 Withdrawn으로 바꿉니다.
     * 유저가 작성한 메시지는 보존되지만, 유저는 참여 중인 모든 채널에서 제거됩니다.
     * @param id 유저의 UUID
     * @return Status 변환 성공 여부
     */
    void deleteUser(UUID id);

    // ------- 따로 추가 -------

    /**
     * 모든 유저 데이터를 초기화(삭제)합니다.
     */
    void clearUsers();


}




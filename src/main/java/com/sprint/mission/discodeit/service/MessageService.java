package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface MessageService {

    // 생성(Create)
    /**
     * 새로운 메시지를 생성하고 저장합니다.
     * 유저와 채널에도 해당 메시지를 추가합니다.
     *
     * @param user 메시지를 작성한 유저
     * @param message 메시지 내용
     * @param channel 메시지가 속한 채널
     * @return 생성된 메시지 객체
     */
    Message createMessage(User user, String message, Channel channel);

    // 조회(Read)
    /**
     * UUID를 기반으로 메시지를 조회합니다.
     *
     * @param id 메시지 UUID
     * @return 메시지 객체 (없으면 null 반환)
     */
    Message findById(UUID id);

    /**
     * 저장된 모든 메시지를 리스트로 반환합니다.
     *
     * @return 전체 메시지 리스트
     */
    List<Message> getMessages();

    // 수정(Update)
    /**
     * 기존 메시지를 수정합니다.
     *
     * @param msgUUID 수정할 메시지의 UUID
     * @param message 새 메시지 내용
     * @return 수정된 메시지 객체 (없으면 null 반환)
     */
    Message updateMessage(UUID msgUUID, String message);

    // 삭제(Delete)
    /**
     * 메시지를 삭제합니다.
     *
     * @param id 삭제할 메시지의 UUID
     */
    void deleteById(UUID id);

    // ------- 따로 추가 -------

    /**
     * 모든 메시지를 삭제합니다.
     */
    void clearMessages();

}




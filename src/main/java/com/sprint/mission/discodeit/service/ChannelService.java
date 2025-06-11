package com.sprint.mission.discodeit.service;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

    // 생성(Create)
    /**
     * 새로운 채널을 생성합니다.
     * @param channelName 채널 이름
     * @return 생성된 채널 객체
     */
    Channel createChannel(String channelName);

    // 조회(Read)
    /**
     * 주어진 ID에 해당하는 채널을 조회합니다.
     *
     * @param id 채널의 UUID
     * @return 조회된 채널 (없으면 null)
     */
    Channel getChannel(UUID id);

    /**
     * 모든 채널 목록을 반환합니다.
     *
     * @return 전체 채널 리스트
     */
    List<Channel> getChannels();

    // 수정(Update)
    /**
     * 채널 이름을 수정합니다.
     *
     * @param channelId 채널 UUID
     * @param channelName 새 채널 이름
     * @return 수정된 채널 객체 (없으면 null)
     */
    Channel updateChannel(UUID channelId, String channelName);

    // 삭제(Delete)
    /**
     * 주어진 ID의 채널을 삭제합니다.
     *
     * @param id 삭제할 채널의 UUID
     * @return 삭제 성공 여부
     */
    void deleteChannel(UUID id);

    // ------- 따로 추가 -------
    /**
     * 채널에 유저를 추가하는 기능입니다.
     * 유저가 이미 존재하지 않으면 채널에 유저를 등록하고,
     * 유저 쪽에서도 해당 채널을 추가해줍니다.
     *
     * @param channel 참여할 채널
     * @param user 참여할 유저
     */
    void joinChannel(Channel channel, User user);

    /**
     * 채널에서 유저를 탈퇴시키는 기능입니다.
     * 유저가 탈퇴하면 해당 유저가 작성한 모든 메시지는 채널에 남아있습니다.
     *
     * @param channel 탈퇴할 채널
     * @param user 탈퇴할 유저
     */
    void leaveChannel(Channel channel, User user);

    /**
     * 모든 채널 데이터를 초기화(삭제)합니다.
     */
    void clearChannels();


}

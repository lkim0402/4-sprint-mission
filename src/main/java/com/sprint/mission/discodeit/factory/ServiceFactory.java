package com.sprint.mission.discodeit.factory;

import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.*;


public class ServiceFactory {

    /**
     * 생성자: 서비스 타입을 지정합니다.
     *
     * @param type 사용할 구현 방식 (JCF_LIST 또는 JCF_MAP)
     */
    public ServiceFactory(ServiceType type) {
    }

    /**
     * 지정된 타입에 따라 UserService 인스턴스를 생성합니다.
     *
     * @return UserService 객체
     */

    public static UserService createUserService(ServiceType type) {
        return switch (type) {
            case JCF_LIST -> new JCFListUserService();
            case JCF_MAP -> new JCFMapUserService();
        };
    }

    /**
     * 지정된 타입에 따라 ChannelService 인스턴스를 생성합니다.
     *
     * @return ChannelService 객체
     */

    public static ChannelService createChannelService(ServiceType type) {
        return switch (type) {
            case JCF_LIST -> new JCFListChannelService();
            case JCF_MAP -> new JCFMapChannelService();
        };
    }

    /**
     * 지정된 타입에 따라 MessageService 인스턴스를 생성합니다.
     *
     * @return MessageService 객체
     */
    public static MessageService createMessageService(ServiceType type) {
        return switch (type) {
            case JCF_LIST -> new JCFListMessageService();
            case JCF_MAP -> new JCFMapMessageService();
        };
    }
}
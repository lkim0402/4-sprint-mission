package com.sprint.mission.discodeit.factory;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.*;


public class ServiceFactory {

    /**
     * 이 클래스는 인스턴스화될 필요가 없습니다. 모든 서비스 생성 메서드는 static으로 제공됩니다.
     */
    public ServiceFactory() {
    }

    // MAP은 잠시 보류
    /**
     * 지정된 타입에 따라 UserService 인스턴스를 생성합니다.
     *
     * @param type 사용할 구현 방식 (JCF_LIST 또는 JCF_MAP)
     * @return UserService 객체
     */
    public static UserService createUserService(String type, UserRepository repository) {
        return switch (type) {
            case "JCF" -> new JCFListUserService(repository);
            case "File" -> new FileUserService(repository);
            default -> null;
        };
    }


    /**
     * 지정된 타입에 따라 ChannelService 인스턴스를 생성합니다.
     *
     * @param type 사용할 구현 방식 (JCF_LIST 또는 JCF_MAP)
     * @return ChannelService 객체
     */
    public static ChannelService createChannelService(String type, ChannelRepository repository) {
        return switch (type) {
            case "JCF" -> new JCFListChannelService(repository);
            case "File" -> new FileChannelService(repository);
            default -> null;
        };
    }

    /**
     * 지정된 타입에 따라 MessageService 인스턴스를 생성합니다.
     *
     * @param type 사용할 구현 방식 (JCF_LIST 또는 JCF_MAP)
     * @return MessageService 객체
     */
    public static MessageService createMessageService(String type, MessageRepository repository) {
        return switch (type) {
            case "JCF" -> new JCFListMessageService(repository);
            case "File" -> new FileMessageService(repository);
            default -> null;
        };
    }
}
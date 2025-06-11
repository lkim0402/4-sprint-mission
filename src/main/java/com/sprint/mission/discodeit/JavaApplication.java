package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFListChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMapUserService;
import com.sprint.mission.discodeit.factory.ServiceFactory;
import com.sprint.mission.discodeit.factory.ServiceType;

import java.util.*;

import static com.sprint.mission.discodeit.factory.ServiceType.JCF_LIST;

public class JavaApplication {

    public static void main(String[] args) {


        // ====================== JCF/File 기반 저장소로 서비스 초기화 ======================
        ChannelService channelService = ServiceFactory.createChannelService( "JCF", new JCFChannelRepository());
//        ChannelService channelService = ServiceFactory.createChannelService("File", new FileChannelRepository());
        channelService.clearChannels();

        MessageService messageService = ServiceFactory.createMessageService("JCF", new JCFMessageRepository());
//        MessageService messageService = ServiceFactory.createMessageService("File", new FileMessageRepository());
        messageService.clearMessages();

//        UserService userService = ServiceFactory.createUserService("JCF", new JCFUserRepository());
        UserService userService = ServiceFactory.createUserService("File", new FileUserRepository());
        userService.clearUsers();


        // ====================== 테스팅
//        channelServiceTest(channelService, messageService, userService);
//        messageServiceTest(channelService, messageService, userService);
        userServiceTest(channelService, messageService, userService);

        // 따로 테스팅 필요 -> 채널은 userList, messageList 를 따로 관리 / 유저는 channelList, messageList 를 따로 관리
//        entityTest();

    }

    /**
     * ChannelService 기능을 테스트하는 메서드입니다.
     * 채널 생성, 조회, 수정, 삭제, 유저 입장/퇴장 등을 검증합니다.
     */
    public static void channelServiceTest(ChannelService channelService, MessageService messageService, UserService userService) {

        // 등록
        System.out.println("\n[CREATE] Channels created:");
        Channel studyChannel = channelService.createChannel("Study-channel");
        Channel gameChannel = channelService.createChannel("game-channel");
        Channel foodChannel = channelService.createChannel("food-channel");
        System.out.println("See all channels: " + channelService.getChannels());


        // 조회(단건, 다건)
        System.out.println("[READ] Read one channel (study channel): ");
        UUID id = studyChannel.getId();
        System.out.println("Id to check: " + id);
        System.out.println(channelService.getChannel(id));
        System.out.println("\n[READ] Read all channels:");
        System.out.println("See all channels: " + channelService.getChannels());


        // 수정
        // 수정된 데이터 조회
        System.out.println("\n[Update] Update individual channel (study channel):");
        System.out.println("Read study channel name: "
                + channelService.getChannel(studyChannel.getId()));
        System.out.println("Read updated study channel name: "
                + channelService.updateChannel(studyChannel.getId(), "study-channel-edited"));

        // 삭제
        // 조회를 통해 삭제되었는지 확인
        System.out.println("\n[Delete] Delete individual channel (study channel):");
        channelService.deleteChannel(studyChannel.getId());
        System.out.println("See all channels"
                + channelService.getChannels());
        System.out.println("\n[Delete] Delete all channels:");
        channelService.clearChannels();
        System.out.println("See all channels"
                + channelService.getChannels());
//
//        // 삭제를 한 후 read, update, delete 진행할때 -> throws Error
//        System.out.println("Read study channel name (deleted): "
//                + channelService.getChannel(studyChannel.getId()));
//        System.out.println("Update study channel name (deleted): "
//                + channelService.updateChannel(studyChannel.getId(), "study-channel-edited"));
//
//        channelService.deleteChannel(studyChannel.getId());
//        System.out.println("Delete channel (deleted): " + channelService.getChannels());
    }

    /**
     * MessageService 기능을 테스트하는 메서드입니다.
     * 메시지 생성, 조회, 수정, 삭제 기능을 검증합니다.
     */
    public static void messageServiceTest(ChannelService channelService, MessageService messageService, UserService userService) {

        User newUser = new User("newUser", "test@gmail.com", "pw1");
        System.out.println("\n[CREATE] New user: " + newUser);
        Channel newChannel = new Channel("newChannel1");
        Channel newChannel2 = new Channel("newChannel2");

        // 등록
        System.out.println("\n[CREATE] Messages created:");
        Message test1 = messageService.createMessage(newUser, "testmessage1", newChannel);
        Message test2 = messageService.createMessage( newUser, "testmessage2",newChannel2);
        System.out.println("See all messages: " + messageService.getMessages());

        // 조회(단건, 다건)
        System.out.println("\n[READ] Read one message (test1):");
        System.out.println(messageService.getMessage(test1.getId()));
        System.out.println("\n[READ] Read all messages:");
        System.out.println("See all messages: " + messageService.getMessages());

        // 수정
        // 수정된 데이터 조회
        System.out.println("\n[Update] Update individual message (test2):");
        System.out.println("Read test2 message: "
                + messageService.getMessage(test2.getId()));
        System.out.println("Read updated test2 message: " +
                messageService.updateMessage(test2.getId(), "testmessage2 updated!!"));

        // 삭제
        // 조회를 통해 삭제되었는지 확인
        System.out.println("\n[Delete] Delete individual message (test2):");
        System.out.println("See all messages before deletion: "
                + messageService.getMessages());
        messageService.deleteMessage(test2.getId());
        System.out.println("See all messages after deletion: "
                + messageService.getMessages());
        System.out.println("\n[Delete] Delete all messages:");
        messageService.clearMessages();
        System.out.println("See all messages: "
                + messageService.getMessages());

        // 삭제를 한 후 read, update, delete 진행할때 -> throws error
//        System.out.println("Read test1 (deleted): "
//                + messageService.getMessage(test1.getId()));
//        System.out.println("Update test1 username (deleted): "
//                + messageService.updateMessage(test1.getId(), "study-channel-edited"));
//        messageService.deleteMessage(test1.getId());
//        System.out.println("Delete channel (deleted): " + messageService.getMessages());

    }

    /**
     * UserService 기능을 테스트하는 메서드입니다.
     * 유저 생성, 조회, 수정, 삭제 등의 기능을 검증합니다.
     */
    public static void userServiceTest(ChannelService channelService, MessageService messageService, UserService userService) {

        //등록
        System.out.println("\n[CREATE] Users created:");
        User newUser1 = userService.createUser("newUser1", "test@gmail.com", "pw1");
        User newUser2 = userService.createUser("newUser2", "test2@gmail.com", "pw2");

        System.out.println("See all users: " + userService.getUsers());

        // 조회(단건, 다건)
        System.out.println("\n[READ] Read one user (newUser1):");
        System.out.println(userService.getUser(newUser1.getId()));
        System.out.println("\n[READ] Read all users:");
        System.out.println("See all users: " + userService.getUsers());

        // 수정
        // 수정된 데이터 조회
        System.out.println("\n[Update] Update (newUser2):");
        System.out.println("Read newUser2: "
                + userService.getUser(newUser2.getId()));
        User userInfo = new User(null, "CHANGED EMAIL!!", "CHANGED PASSWORD!!");
        System.out.println("Read updated newUser2 (new email and pw): " +
                userService.updateUser(newUser2.getId(), userInfo));

        // 삭제
        // 조회를 통해 삭제되었는지 확인
        System.out.println("\n[Delete] Delete individual user (newUser2):");
        System.out.println("See all users before deletion: "
                + userService.getUsers());
        userService.deleteUser(newUser2.getId());
        System.out.println("See all users after deletion (newUser2): "
                + userService.getUsers());
        System.out.println("Check deleted user status/info (newUser2): "
                + newUser2);
        System.out.println("\n[Delete] Delete all users (clear):");
        userService.clearUsers();
        System.out.println("See all users: "
                + userService.getUsers());

        // 삭제를 한 후 read, update, delete 진행할때 -> throws Error
//        System.out.println("Read newUser1 (deleted): "
//                + userService.getUser(newUser1.getId()));
//        User userInfoTest = new User(null, "CHANGED EMAIL AGAIN!!", "CHANGED PASSWORD AGAIN!!");
//        System.out.println("Update test1 username (deleted): "
//                + userService.updateUser(newUser1.getId(), userInfoTest));
//        userService.deleteUser(newUser1.getId());
//        System.out.println("Delete channel (deleted): ");
//        userService.getUsers();

    }

    /**
     * Entity 클래스들(User, Channel, Message)의 기본 동작을 테스트하는 메서드입니다.
     * 각 엔티티의 관계 연결 등을 검증합니다.
     */
    public static void entityTest(ChannelService channelService, MessageService messageService, UserService userService) {

        // memberIds 확인
        System.out.println("\n========== Channel's user Test ==========");
        Channel testChannel = channelService.createChannel("Study-channel");
        System.out.println("Created channel: " + testChannel);

        // 멤버 생성 + 추가
        System.out.println("\n[CREATE] Users created:");
        User newUser1 = userService.createUser("newUser1", "test@gmail.com", "pw1");
        User newUser2 = userService.createUser("newUser1", "test2@gmail.com", "pw2");
        channelService.joinChannel(testChannel, newUser1);
        channelService.joinChannel(testChannel, newUser2);
        System.out.println("After adding users: " + testChannel.getUsers());
        System.out.println("ChannelList of newUser1: " + newUser2.getChannels());
        System.out.println("ChannelList of newUser2: " + newUser2.getChannels());

        // 멤버 삭제
        channelService.leaveChannel(testChannel, newUser2);
        System.out.println("All users after deleting newUser2: " + testChannel.getUsers());
        System.out.println("Deleted newUser status: " + newUser2);


        // messages 확인
        System.out.println("\n========== Channel's Message Test ==========");
        System.out.println("Messages in testChannel: " + testChannel.getMessages());
        System.out.println("MessageList of newUser1: " + newUser1.getMessagesList());
        System.out.println("MessageList of newUser2: " + newUser2.getMessagesList());

        // 메세지 + 추가
        Message testMsg1 = new Message(newUser1, "Hello test", testChannel);
        newUser1.addMessage(testMsg1);
        System.out.println("Messages in testChannel after adding: " + testChannel.getMessages());
        System.out.println("MessageList of newUser1: " + newUser1.getMessagesList());
        System.out.println("MessageList of newUser2: " + newUser2.getMessagesList());


        // 멤버 삭제
        testChannel.deleteMessage(testMsg1);
        System.out.println("After deleting testMsg1: " + testChannel.getMessages());
        System.out.println("MessageList of newUser1: " + newUser2.getMessagesList());

    }
}

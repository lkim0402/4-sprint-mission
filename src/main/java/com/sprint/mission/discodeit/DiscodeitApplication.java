package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PrivateChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PublicChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.UpdateChannelRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.UpdateMessageRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UpdateUserRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UpdateUserResponseDto;
import com.sprint.mission.discodeit.dto.UserService.UserRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {

		/**
		 * =============== Repository 테스팅 ===============
		 * Repository를 테스트 할때 File, Jcf 번갈아가면서 테스팅 하기
		 * 방법: Repository 클래스 위에 @Primary 어노테이션 추가
		 * 1차 테스트: File*Repository
		 * 2차 테스트: JCF*Repository
		 *
		 * =============== Mapper ===============
		 * 서비스 별로 들어있는 Mapper 클래스는 @Component 어노테이션이 붙혀져 있으므로
		 * 따로 설정 불필요
		 */

		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		ChannelService channelService = context.getBean(ChannelService.class);
		UserService userService = context.getBean(UserService.class);
		MessageService messageService = context.getBean(MessageService.class);
		AuthService authService = context.getBean(AuthService.class);
		BinaryContentService binaryContentService = context.getBean(BinaryContentService.class);
		ReadStatusService readStatusService = context.getBean(ReadStatusService.class);
		UserStatusService userStatusService = context.getBean(UserStatusService.class);

		// 테스트 하기 전 모든 데이터 초기화
		channelService.deleteAll();
		userService.deleteAll();
		messageService.deleteAll();
		binaryContentService.deleteAll();
		readStatusService.deleteAll();
		userStatusService.deleteAll();

		// =============================== 서비스 테스팅 ===============================
//		channelServiceTest(channelService);
//        userServiceTest(userService);
        messageServiceTest(messageService, channelService, userService);

//		 ====================== Entity 테스팅  ======================
//        entityTest(channelService, messageService, userService);

	}

//    static User setupUser(UserService userService) {
//
//		UserRequestDto userRequestDto = new UserRequestDto(
//				"woody",
//				"woody@codeit.com",
//				"woody1234",
//				new BinaryContentRequestDto(
//						null,
//						null,
//						null,
//						"이미지",
//						"PROFILE"
//				)
//		);
//
//		return userService.create(userRequestDto);
//	}
//
//
//	static Channel setupChannel(ChannelService channelService) {
//		Channel newChannel = new Channel(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
//		return channelService.create(newChannel);
//	}
//
//	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
//		Message newMessage = new Message("안녕하세요.", channel.getId(), author.getId());
//		System.out.println("메시지 생성: " + newMessage.getId() + ": " + newMessage.getContent());
//	}
//
//	// new methods

	/**
	 * ChannelService 기능을 테스트하는 메서드입니다.
	 * 채널 생성, 조회, 수정, 삭제, 유저 입장/퇴장 등을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void channelServiceTest(ChannelService channelService) {

		// =================== test users ===================
		User user1 = new User(
				"codeit",
				"codeit@codeit.com",
				"q1w2e3"
		);

		User user2 = new User(
				"woody",
				"woody@codeit.com",
				"w2e3r4"
		);

		// =================== 등록 + 조회 ===================
		System.out.println("\n[CREATE] Public channels created:");
		PublicChannelRequestDto publicChannelRequestDto1 = new PublicChannelRequestDto(
				ChannelType.PUBLIC,
				"Study-channel"
				,"This is a study channel"
		);
		PublicChannelRequestDto publicChannelRequestDto2 = new PublicChannelRequestDto(
				ChannelType.PUBLIC,
				"game-channel"
				,"This is a game channel"
		);
		Channel studyChannel = channelService.createPublic(publicChannelRequestDto1);
		Channel gameChannel = channelService.createPublic(publicChannelRequestDto2);
		System.out.println("See all public channels: " + channelService.findAllPublicChannels());

		System.out.println("\n[CREATE] Private channels created (added users 1,2):");
		PrivateChannelRequestDto privateChannelRequestDto = new PrivateChannelRequestDto(
				ChannelType.PRIVATE,
				List.of(user1.getId(), user2.getId())
		);
		Channel privateChannel = channelService.createPrivate(privateChannelRequestDto);
		System.out.println("See all channels for user1 (id " + user1.getId() + ")" +
				channelService.findAllByUserId(user1.getId()));


		// =================== 수정 ===================
		// 수정된 데이터 조회
		System.out.println("\n[Update] Update individual channel (study channel):");
		System.out.println("Read public study channel name: "
				+ channelService.find(studyChannel.getId()));
		UpdateChannelRequestDto updateChannelRequestDto = new UpdateChannelRequestDto(
				studyChannel.getId(),
				ChannelType.PUBLIC,
				"changed-name!",
				"This is updated study channel"
		);
		System.out.println("Read updated public study channel name: "
				+ channelService.update(updateChannelRequestDto));

		// =================== 삭제 ===================
		// 조회를 통해 삭제되었는지 확인
		System.out.println("\n[Delete] Delete public study channel:");
		System.out.println("See all public channels: "
				+ channelService.findAllPublicChannels());
		channelService.delete(studyChannel);
		System.out.println("See all public channels after deletion: "
				+ channelService.findAllPublicChannels());
		System.out.println("\n[Delete] Delete private channel (has user1 and user2):");
		System.out.println("See all channels of user1: "
				+ channelService.findAllByUserId(user1.getId()));
		channelService.delete(privateChannel);
		System.out.println("See all channels of user1 (after deletion):"
				+ channelService.findAllByUserId(user1.getId()));
		channelService.deleteAll(); // deleting both public and private
		System.out.println("\n[Delete] Delete ALL public channels: "
				+ channelService.findAllPublicChannels());


		// 삭제를 한 후 read, update, delete 진행할때 에러 던짐
//        System.out.println("Read study channel name (deleted): " + channelService.find(studyChannel.getId()));
//        System.out.println("Update study channel name (deleted): " + channelService.update(updateChannelRequestDto));
//        channelService.delete(studyChannel);
//        System.out.println("Delete channel (deleted): " + channelService.findAllPublicChannels());
	}

	/**
	 * UserService 기능을 테스트하는 메서드입니다.
	 * 유저 생성, 조회, 수정, 삭제 등의 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void userServiceTest(UserService userService) {

		// =================== 등록 + 조회 ===================
		System.out.println("\n[CREATE] Users created:");
		UserRequestDto userRequestDto1 = new UserRequestDto(
				"codeit",
				"codeit@gmail.com",
				"q1w2e3",
				new BinaryContentRequestDto(
						null,
						null,
						null,
						null,
						null
				)
		);
		UserRequestDto userRequestDto2 = new UserRequestDto(
				"woody",
				"woody@gmail.com",
				"w2e3r4",
				new BinaryContentRequestDto(
						null,
						null,
						null,
						null,
						null
				)
		);
		User newUser1 = userService.create(userRequestDto1);
		User newUser2 = userService.create(userRequestDto2);
		System.out.println("Find one specific user by Id (userRequestDto1): " + userService.find(newUser1.getId()));
		System.out.println("Find one specific user by Id (userRequestDto2): " + userService.find(newUser2.getId()));
		System.out.println("See all users: " + userService.findAll());


		// =================== 수정 ===================
		// 수정된 데이터 조회
		System.out.println("\n[Update] Update individual user (userRequestDto1):");
		System.out.println("Find newUser1: "
				+ userService.find(newUser1.getId()));
		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto(
				newUser1.getId(),
				"codeit-changed-username",
				"changed-name!",
				null,
				UUID.randomUUID()
		);
		System.out.println("Read updated newUser2: "
				+ userService.update(updateUserRequestDto));

		// =================== 삭제 ===================
		// 조회를 통해 삭제되었는지 확인
		System.out.println("\n[Delete] Delete individual user (newUser2):");
		System.out.println("See all users before deletion: "
				+ userService.findAll());
		userService.delete(newUser2);
		System.out.println("See all users after deletion (newUser2): "
				+ userService.findAll());
		System.out.println("\n[Delete] Delete ALL users (clear):");
		userService.deleteAll();
		System.out.println("See all users: "
				+ userService.findAll());

//		 삭제를 한 후 read, update, delete 진행할때 에러 던짐
//        System.out.println("Read newUser1 (deleted): "
//                + userService.find(newUser1.getId()));
//		UpdateUserRequestDto updateUserRequestDtoTest = new UpdateUserRequestDto(
//				newUser1.getId(),
//				"codeit-changed-username-after-deletion",
//				"changed-name!",
//				null,
//				UUID.randomUUID()
//		);
//        System.out.println("Update test1 username (deleted): "
//                + userService.update(updateUserRequestDtoTest));
//        userService.delete(newUser1);
//        System.out.println("Delete User (deleted): ");
//        userService.findAll();

	}

	/**
	 * MessageService 기능을 테스트하는 메서드입니다.
	 * 메시지 생성, 조회, 수정, 삭제 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void messageServiceTest(MessageService messageService, ChannelService channelService, UserService userService) {

		// =================== test channel and users ===================

		PublicChannelRequestDto publicChannelRequestDto1 = new PublicChannelRequestDto(
				ChannelType.PUBLIC,
				"Study-channel"
				,"This is a study channel"
		);
		Channel studyChannel = channelService.createPublic(publicChannelRequestDto1);

		UserRequestDto userRequestDto1 = new UserRequestDto(
				"codeit",
				"codeit@gmail.com",
				"q1w2e3",
				new BinaryContentRequestDto(
						null,
						null,
						null,
						null,
						"PROFILE"
				)
		);
		UserRequestDto userRequestDto2 = new UserRequestDto(
				"woody",
				"woody@gmail.com",
				"w2e3r4",
				new BinaryContentRequestDto(
						null,
						null,
						null,
						null,
						"PROFILE"
				)
		);
		User newUser1 = userService.create(userRequestDto1);
		User newUser2 = userService.create(userRequestDto2);


		// =================== 등록 + 조회 ===================
		System.out.println("\n[CREATE] Users created:");
		UUID channelId1 = studyChannel.getId();
		UUID authorId1 = newUser1.getId();
		UUID authorId2 = newUser2.getId();
		MessageRequestDto messageRequestDto1 = new MessageRequestDto(
				"content1",
				channelId1,
				authorId1,
				List.of()
		);
		MessageRequestDto messageRequestDto2 = new MessageRequestDto(
				"content2",
				channelId1,
				authorId2, // different author at same channel
				List.of(
						new BinaryContentRequestDto(
							null, // set in create() in service
							null, // // set in create() in service
							null,
							"이미지1",
							"PNG"),
						new BinaryContentRequestDto(
							null, // set in create() in service
							null, // // set in create() in service
							null,
							"이미지2",
							"PNG"
						))
		);

		Message newMessage1 = messageService.create(messageRequestDto1);
		Message newMessage2 = messageService.create(messageRequestDto2);
		System.out.println("Find one specific message by Id (messageRequestDto1): "
				+ messageService.find(newMessage1.getId()));
		System.out.println("Find one specific message by Id (messageRequestDto2): "
				+ messageService.find(newMessage2.getId()));
		System.out.println("See all messages in channel id " + channelId1 + ": " +
				messageService.findallByChannelId(channelId1));


		// =================== 수정 ===================
		// 수정된 데이터 조회
		System.out.println("\n[Update] Update individual message (messageRequestDto1):");
		System.out.println("Find one specific message by Id (messageRequestDto1): "
				+ messageService.find(newMessage1.getId()));
		UpdateMessageRequestDto updateMessageRequestDto = new UpdateMessageRequestDto(
			"content1 - CHANGED CONTENT OF MESSAGE"
		);
		System.out.println("Read updated message: "
				+ messageService.update(newMessage1.getId(), updateMessageRequestDto));
		System.out.println("See all messages in channel id " + channelId1 + " after update: " +
				messageService.findallByChannelId(channelId1));


		// =================== 삭제 ===================
		// 조회를 통해 삭제되었는지 확인
		System.out.println("\n[Delete] Delete individual message (messageRequestDto1):");
		System.out.println("See all messages in channel1 before deletion: "
				+ messageService.findallByChannelId(channelId1));
		messageService.delete(newMessage1);
		System.out.println("See all messages after deleting message1: "
				+ messageService.findallByChannelId(channelId1));
		System.out.println("\n[Delete] Delete ALL messages (clear):");
		messageService.deleteAll();
		System.out.println("See all messages in channel: "
				+ messageService.findallByChannelId(channelId1));

//		 삭제를 한 후 read, update, delete 진행할때 에러 던짐
//		System.out.println("Find deleted message by Id (messageRequestDto1): "
//				+ messageService.find(newMessage1.getId()));
//		UpdateMessageRequestDto updateMessageRequestDtoTest = new UpdateMessageRequestDto(
//				"CHANGED CONTENT OF MESSAGE"
//		);
//        System.out.println("Update test1 username (deleted): "
//                + messageService.update(newMessage1.getId(), updateMessageRequestDtoTest));
//        System.out.println("Delete Message (deleted): ");
//		messageService.delete(newMessage1);
//		messageService.findallByChannelId(channelId1);
	}


	/**
	 * Entity 클래스들(User, Channel, Message)의 기본 동작을 테스트하는 메서드입니다.
	 * 각 엔티티의 관계 연결 등을 검증합니다.
	 * [] file repo 테스트 완료
	 * [] jcf repo 테스트 완료
	 */
	public static void entityTest(ChannelService channelService, MessageService messageService, UserService userService) {

//		// memberIds 확인
//		System.out.println("\n========== Channel's user Test ==========");
//		Channel testChannel = channelService.createChannel("Study-channel");
//		System.out.println("Created channel: " + testChannel);
//
//		// 멤버 생성 + 추가
//		System.out.println("\n[CREATE] Users created:");
//		User newUser1 = userService.createUser("newUser1", "test@gmail.com", "pw1");
//		User newUser2 = userService.createUser("newUser1", "test2@gmail.com", "pw2");
//		channelService.joinChannel(testChannel, newUser1);
//		channelService.joinChannel(testChannel, newUser2);
//		System.out.println("After adding users: " + testChannel.getUsers());
//		System.out.println("ChannelList of newUser1: " + newUser2.getChannels());
//		System.out.println("ChannelList of newUser2: " + newUser2.getChannels());
//
//		// 멤버 삭제
//		channelService.leaveChannel(testChannel, newUser2);
//		System.out.println("All users after deleting newUser2: " + testChannel.getUsers());
//		System.out.println("Deleted newUser status: " + newUser2);
//
//
//		// messages 확인
//		System.out.println("\n========== Channel's Message Test ==========");
//		System.out.println("Messages in testChannel: " + testChannel.getMessages());
//		System.out.println("MessageList of newUser1: " + newUser1.getMessagesList());
//		System.out.println("MessageList of newUser2: " + newUser2.getMessagesList());
//
//		// 메세지 + 추가
//		Message testMsg1 = new Message(newUser1, "Hello test", testChannel);
//		newUser1.addMessage(testMsg1);
//		System.out.println("Messages in testChannel after adding: " + testChannel.getMessages());
//		System.out.println("MessageList of newUser1: " + newUser1.getMessagesList());
//		System.out.println("MessageList of newUser2: " + newUser2.getMessagesList());
//
//
//		// 멤버 삭제
//		testChannel.deleteMessage(testMsg1);
//		System.out.println("After deleting testMsg1: " + testChannel.getMessages());
//		System.out.println("MessageList of newUser1: " + newUser2.getMessagesList());

	}


//	static User setupUser(UserService userService) {
//		User user = userService.createUser("woody", "woody@codeit.com", "woody1234");
//		System.out.println("유저 생성: " + user);
//		return user;
//	}
//
//	static Channel setupChannel(ChannelService channelService) {
//		Channel channel = channelService.createChannel( "공지");
//		System.out.println("채널 생성: " + channel);
//		return channel;
//	}
//
//	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
//		Message message = messageService.createMessage(
//				new User("woody", "woody@codeit.com", "woody1234"),
//				"안녕하세요.",
//				new Channel("testChannel")
//		);
//		System.out.println("메시지 생성: " + message);
//	}


}

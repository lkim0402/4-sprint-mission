package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PrivateChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.PublicChannelRequestDto;
import com.sprint.mission.discodeit.dto.ChannelService.UpdateChannelRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.MessageRequestDto;
import com.sprint.mission.discodeit.dto.MessageService.UpdateMessageRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.ReadStatusRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusService.UpdateReadStatusDto;
import com.sprint.mission.discodeit.dto.UserService.UpdateUserRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UpdateUserResponseDto;
import com.sprint.mission.discodeit.dto.UserService.UserRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
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

		// 테스트 전 모든 데이터 초기화
		clearAllData(channelService, userService, messageService, binaryContentService, readStatusService, userStatusService);


		/**
		 * 테스트하고 싶은 서비스의 해당 라인 앞 주석(//)을 제거하여 실행하고,
		 * 테스트가 끝나면 다시 주석 처리하는 방식으로 하나씩 확인해주세요.
		 */
		// =============================== 서비스 테스팅 ===============================
//		channelServiceTest(channelService);
//        userServiceTest(userService);
//        messageServiceTest(messageService, channelService, userService);
//		authServiceTest(authService, userService);
//		binaryContentServiceTest(binaryContentService);
		readStatusServiceTest(readStatusService, userService, channelService);
		userStatusServiceTest(userStatusService);
	}

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
	 * AuthService 기능을 테스트하는 메서드입니다.
	 * login 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void authServiceTest(AuthService authService, UserService userService) {

		// =================== test users ===================

		System.out.println("\n Test user created:");
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
		User newUser1 = userService.create(userRequestDto1);

		// =============== [Success] testing login ===============
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(
				"codeit",
				"q1w2e3"
		);

		UserLoginResponseDto userLoginResponseDto = authService.login(userLoginRequestDto);
		System.out.println("Successful Log in: " + userLoginResponseDto);

		// =============== [Failure - wrong password] testing login ===============
		// Will throw error
//		UserLoginRequestDto userLoginRequestDto2 = new UserLoginRequestDto(
//				"codeit",
//				"q1w2e3567"
//		);
//		authService.login(userLoginRequestDto2);

		// =============== [Failure - non existent id] testing login ===============
		// Will throw error
//		UserLoginRequestDto userLoginRequestDto3 = new UserLoginRequestDto(
//				"codeit11223344",
//				"q1w2e3"
//		);
//		authService.login(userLoginRequestDto3);
//		System.out.println("Successful Log in: " + userLoginResponseDto);
	}

	/**
	 * BinaryContentService 기능을 테스트하는 메서드입니다.
	 * BinaryContent 생성, 조회, 삭제 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void binaryContentServiceTest(BinaryContentService binaryContentService) {
		// =================== 등록 + 조회 ===================
		System.out.println("\n[CREATE] BinaryContents created:");
		UUID userId = UUID.randomUUID();
		UUID messageId = UUID.randomUUID();

		String dummyContent1 = "First file's content.";
		String dummyContent2 = "Second file's content.";
		byte[] fileBytes1 = dummyContent1.getBytes(StandardCharsets.UTF_8);
		byte[] fileBytes2 = dummyContent2.getBytes(StandardCharsets.UTF_8);

		BinaryContentRequestDto binaryContentRequestDto1 = new BinaryContentRequestDto(
				userId,
				messageId,
				fileBytes1,
		"testFileName1",
				"PNG"
		);
		BinaryContentRequestDto binaryContentRequestDto2 = new BinaryContentRequestDto(
				userId,
				messageId,
				fileBytes2,
				"testFileName2",
				"JPG"
		);

		BinaryContent binaryContent1 = binaryContentService.create(binaryContentRequestDto1);
		BinaryContent binaryContent2 = binaryContentService.create(binaryContentRequestDto2);
		System.out.println("Find one specific binary content by Id (binaryContentRequestDto1): " + binaryContentService.find(binaryContent1.getId()));
		System.out.println("Find one specific binary content by Id (binaryContentRequestDto2): " + binaryContentService.find(binaryContent2.getId()));
		List<UUID> binaryContentIds = List.of(binaryContent1.getId(), binaryContent2.getId());
		System.out.println("See all binaryContents: " + binaryContentService.findAllByIdIn(binaryContentIds));

		// =================== 삭제 ===================
		// 조회를 통해 삭제되었는지 확인
		System.out.println("\n[Delete] Delete individual binaryContent (binaryContentRequestDto1):");
		System.out.println("See all binary content before deletion: "
				+ binaryContentService.findAllByIdIn(binaryContentIds));
		binaryContentService.delete(binaryContent1.getId());
		System.out.println("Successfully Deleted! \n Deleting binaryContentRequestDto1 will give an error");
//		binaryContentService.delete(binaryContent1.getId());
		System.out.println("\n[Delete] Delete ALL users (clear):");
		binaryContentService.deleteAll();
		System.out.println("Successfully Deleted! \n Deleting any binary content will give an error");
//		binaryContentService.delete(binaryContent2.getId());


//		 삭제를 한 후 read 진행할때 에러 던짐
//        System.out.println("Read binaryContent1 (deleted): "
//                + binaryContentService.find(binaryContent1.getId()));

	}

	/**
	 * ReadStatusService 기능을 테스트하는 메서드입니다.
	 * ReadStatus 생성, 조회, 삭제 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void readStatusServiceTest(ReadStatusService readStatusService, UserService userService, ChannelService channelService) {

		// =================== test users and channels ===================
		System.out.println("\nTest Users created:");
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
		UUID newUser1Id = newUser1.getId();
		UUID newUser2Id = newUser2.getId();

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


		// =================== 등록 + 조회 ===================
		System.out.println("\n[CREATE] ReadStatuses created:");
		ReadStatusRequestDto readStatusRequestDto1 = new ReadStatusRequestDto(
				newUser1Id,
				studyChannel.getId(),
				Instant.now()
		);
		ReadStatusRequestDto readStatusRequestDto2 = new ReadStatusRequestDto(
				newUser1Id,
				gameChannel.getId(),
				Instant.now()
		);

		ReadStatus readStatus1 = readStatusService.create(readStatusRequestDto1);
		ReadStatus readStatus2 = readStatusService.create(readStatusRequestDto2);
		System.out.println("Find readstatus by Id (readStatus1): "
				+ readStatusService.find(readStatus1.getId()));
		System.out.println("Find readstatus by Id (readStatus2): "
				+ readStatusService.find(readStatus2.getId()));
		System.out.println("See all ReadStatus of user1: " + readStatusService.findAllByUserId(newUser1Id));
		System.out.println("See all ReadStatus of user2: " + readStatusService.findAllByUserId(newUser2Id));


		// =================== 수정 ===================
		// 수정된 데이터 조회
		System.out.println("\n[Update] Update individual readstatus (readStatus1):");
		System.out.println("Find readStatus1: "
				+ userService.find(newUser1.getId()));
//		UpdateReadStatusDto updateReadStatusDto = new UpdateReadStatusDto(
//				Instant.now()
//		);
		readStatusService.update(readStatus1.getId());
		System.out.println("Read updated readStatus1: "
				+ readStatus1);

		// =================== 삭제 ===================
		// 조회를 통해 삭제되었는지 확인
		System.out.println("\n[Delete] Delete individual readStatus (readStatus1):");
		System.out.println("See all readStatus of user1 before deletion: "
				+ readStatusService.findAllByUserId(newUser1Id));
		readStatusService.delete(readStatus1.getId());
		System.out.println("See all readStatus after deletion (readStatus1): "
				+ readStatusService.findAllByUserId(newUser1Id));
		System.out.println("\n[Delete] Delete ALL users (clear):");
		readStatusService.deleteAll();
		System.out.println("See all readStatus: "
				+ readStatusService.findAllByUserId(newUser1Id));

//		 삭제를 한 후 read, update, delete 진행할때 에러 던짐
//        System.out.println("Read readStatus1 (deleted): "
//                + readStatusService.find(readStatus1.getId()));
//		readStatusService.update(readStatus1.getId());
//		readStatusService.delete(readStatus1.getId());

	}

	/**
	 * UserStatusService 기능을 테스트하는 메서드입니다.
	 * UserStatus 생성, 조회, 삭제 기능을 검증합니다.
	 * [x] file repo 테스트 완료
	 * [x] jcf repo 테스트 완료
	 */
	public static void userStatusServiceTest(UserStatusService userStatusService) {

	}
	// ========================= Helper methods =========================

	/**
	 * 테스트 하기 전 모든 데이터 초기화합니다.
	 * @param channelService
	 * @param userService
	 * @param messageService
	 * @param binaryContentService
	 * @param readStatusService
	 * @param userStatusService
	 */
	private static void clearAllData(ChannelService channelService, UserService userService, MessageService messageService, BinaryContentService binaryContentService, ReadStatusService readStatusService, UserStatusService userStatusService) {
		channelService.deleteAll();
		userService.deleteAll();
		messageService.deleteAll();
		binaryContentService.deleteAll();
		readStatusService.deleteAll();
		userStatusService.deleteAll();
	}



}

package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.BinaryContentService.BinaryContentRequestDto;
import com.sprint.mission.discodeit.dto.UserService.UserRequestDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DiscodeitApplication {

    static User setupUser(UserService userService) {

		UserRequestDto userRequestDto = new UserRequestDto(
				"woody",
				"woody@codeit.com",
				"woody1234",
				new BinaryContentRequestDto(
						null,
						null,
						null,
						"이미지",
						"PROFILE"
				)
		);

		return userService.create(userRequestDto);
	}


	static Channel setupChannel(ChannelService channelService) {
		Channel newChannel = new Channel(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
		return channelService.create(newChannel);
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		Message newMessage = new Message("안녕하세요.", channel.getId(), author.getId());
		System.out.println("메시지 생성: " + newMessage.getId() + ": " + newMessage.getContent());
	}

	// new methods

	public static void main(String[] args) {

//		SpringApplication.run(DiscodeitApplication.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		// From Java Application
		// 셋업
		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);
		// 테스트
		messageCreateTest(messageService, channel, user);

		System.out.println("User created: " + user);




	}

}

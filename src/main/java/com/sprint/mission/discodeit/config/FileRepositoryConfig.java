package com.sprint.mission.discodeit.config;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name="type",
        havingValue = "file"
)
public class FileRepositoryConfig {

    @Bean
    public BinaryContentRepository binaryContentRepository(RepositorySettings settings) {
        return new FileBinaryContentRepository(settings);
    }

    @Bean
    public ChannelRepository channelRepository(RepositorySettings settings) {
        return new FileChannelRepository(settings);
    }

    @Bean
    public MessageRepository messageRepository(RepositorySettings settings) {
        return new FileMessageRepository(settings);
    }

    @Bean
    public ReadStatusRepository readStatusRepository(RepositorySettings settings) {
        return new FileReadStatusRepository(settings);
    }

    @Bean
    public UserRepository userRepository(RepositorySettings settings) {
        return new FileUserRepository(settings);
    }

    @Bean
    public UserStatusRepository userStatusRepository(RepositorySettings settings) {
        return new FileUserStatusRepository(settings);
    }
}

package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.config.aws.AwsProperties;
import com.sprint.mission.discodeit.storage.s3.S3BinaryContentStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3BinaryContentStorage.class)
public class DiscodeitApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiscodeitApplication.class, args);
  }
}

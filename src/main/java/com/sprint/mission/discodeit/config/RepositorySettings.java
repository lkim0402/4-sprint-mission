package com.sprint.mission.discodeit.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("discodeit.repository")
// need getters and setters for spring to inject values
@Getter
@Setter
public class RepositorySettings {
    // default values unless stated otherwise
    private String TYPE = "jcf";
    private String FILEDIRECTORY = ".discodeit";
    private String EXTENSION = ".ser";
    private String PATH = "user.dir";
}

package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.config.RepositorySettings;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileChannelRepository implements ChannelRepository {
    private final Path directory;
    private final String extension;

    public FileChannelRepository(RepositorySettings repositorySettings) {
        this.extension = repositorySettings.getExtension();
        String fileDirectory = repositorySettings.getFileDirectory();
        this.directory = Paths.get(System.getProperty("user.dir"),
                fileDirectory,
                "file-data-map",
                Channel.class.getSimpleName());

        try {
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 디렉토리 생성 실패", e);
        }
    }

    private Path resolvePath(UUID id) {
        return directory.resolve(id + extension);
    }

    @Override
    public Channel save(Channel channel) {
        Path path = resolvePath(channel.getId());
        try (
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        Channel channelNullable = null;
        Path path = resolvePath(id);
        if (Files.exists(path)) {
            try (
                    FileInputStream fis = new FileInputStream(path.toFile());
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                channelNullable = (Channel) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(channelNullable);
    }

    @Override
    public List<Channel> findAll() {
        try {
            return Files.list(directory)
                    .filter(path -> path.toString().endsWith(extension))
                    .map(FileChannelRepository::getChannel)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        Path path = resolvePath(id);
        return Files.exists(path);
    }

    @Override
    public boolean existsByName(String channelName) {
        try {
            return Files.list(directory)
                    .filter(path -> path.toString().endsWith(extension))
                    .map(FileChannelRepository::getChannel)
                    .anyMatch(c -> channelName.equals(c.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }    }

    private static Channel getChannel(Path path) {
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Channel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Path path = resolvePath(id);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.list(directory)
                    .filter(path -> path.toString().endsWith(extension))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to list directory for deletion: " + directory, e);
        }
    }
}

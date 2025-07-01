package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.config.RepositorySettings;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class FileChannelRepository implements ChannelRepository {
    private final static String PATH = "user.dir";
    private final Path DIRECTORY;
    private final String EXTENSION;

    public FileChannelRepository(RepositorySettings repositorySettings) {
        this.EXTENSION = repositorySettings.getEXTENSION();
        String fileDirectory = repositorySettings.getFILEDIRECTORY();
        this.DIRECTORY = Paths.get(System.getProperty(PATH),
                fileDirectory,
                "file-data-map",
                Channel.class.getSimpleName());

        try {
            if (Files.notExists(DIRECTORY)) {
                Files.createDirectories(DIRECTORY);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 디렉토리 생성 실패", e);
        }
    }

    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
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
        try (Stream<Path> paths = Files.list(DIRECTORY)){
            return paths
                    .filter(path -> path.toString().endsWith(EXTENSION))
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
        try (Stream<Path> paths = Files.list(DIRECTORY)){
            return paths
                    .filter(path -> path.toString().endsWith(EXTENSION))
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
        try (Stream<Path> paths = Files.list(DIRECTORY)){
            paths
                .filter(path -> path.toString().endsWith(EXTENSION))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException("Failed to list directory for deletion: " + DIRECTORY, e);
        }
    }
}

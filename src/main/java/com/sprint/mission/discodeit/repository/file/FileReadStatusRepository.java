package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class FileReadStatusRepository implements ReadStatusRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileReadStatusRepository() {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"),
                "file-data-map",
                ReadStatus.class.getSimpleName()
        );
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        Path path = resolvePath(readStatus.getId());
        try (
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(readStatus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        ReadStatus readStatusNullable = null;
        Path path = resolvePath(id);
        if (Files.exists(path)) {
            try (
                    FileInputStream fis = new FileInputStream(path.toFile());
                    ObjectInputStream ois = new ObjectInputStream(fis);
            ) {
                readStatusNullable = (ReadStatus) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(readStatusNullable);
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        try {
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    // convert each object in path
                    .map(FileReadStatusRepository::getReadStatus
                    )
                    .filter(r -> r.getChannelId().equals(channelId))
                    .toList();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        try {
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    // convert each object in path
                    .map(FileReadStatusRepository::getReadStatus)
                    .filter(readStatus -> readStatus.getUserId().equals(userId))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<ReadStatus> findByChannelAndUserId(UUID channelId, UUID userId) {
        try {
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(".ser"))
                    // convert each object in path
                    .map(FileReadStatusRepository::getReadStatus)
                    .filter(r -> r.getUserId().equals(userId) && r.getChannelId().equals(channelId))
                    .findFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReadStatus> findAll() {
        try {
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(".ser"))
                    // convert each object in path
                    .map(FileReadStatusRepository::getReadStatus)
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
    public void deleteById(UUID id) {
        Path path = resolvePath(id);
        try {
            Files.delete(path);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.list(DIRECTORY)
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

    private static ReadStatus getReadStatus(Path path) {
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (ReadStatus) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

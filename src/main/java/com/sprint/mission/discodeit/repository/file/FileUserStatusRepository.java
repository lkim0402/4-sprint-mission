package com.sprint.mission.discodeit.repository.file;
import com.sprint.mission.discodeit.config.RepositorySettings;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
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
public class FileUserStatusRepository implements UserStatusRepository {
    private final static String PATH = "user.dir";
    private final Path DIRECTORY;
    private final String EXTENSION;

    public FileUserStatusRepository(RepositorySettings repositorySettings) {
        this.EXTENSION = repositorySettings.getEXTENSION();
        String fileDirectory = repositorySettings.getFILEDIRECTORY();
        this.DIRECTORY = Paths.get(System.getProperty(PATH),
                fileDirectory,
                "file-data-map",
                UserStatus.class.getSimpleName());

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
    public UserStatus save(UserStatus userStatus) {
        Path path = resolvePath(userStatus.getId());
        try (
            FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(userStatus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        UserStatus userStatusNullable = null;
        Path path = resolvePath(id);
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis);
        ){
            userStatusNullable = (UserStatus) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(userStatusNullable);
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        try (Stream<Path> paths = Files.list(DIRECTORY)){
            return paths
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(FileUserStatusRepository::getUserStatus).toList();
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
//            Files.deleteIfExists(path);

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

    private static UserStatus getUserStatus(Path path) {
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (UserStatus) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

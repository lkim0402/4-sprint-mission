package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class User extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private UUID profileId;

    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public void update(String newUsername, String newEmail, String newPassword, UUID newProfileId) {

        boolean anyValueUpdated = updateField(newUsername, this.username, this::setUsername) ||
                updateField(newEmail, this.email, this::setEmail) ||
                updateField(newPassword, this.password, this::setPassword) ||
                updateField(newProfileId, this.profileId, this::setProfileId);

        if (anyValueUpdated) {
            this.updateTimeStamp();
        }
    }

    /**
     * 필드 값을 업데이트하고, 변경 여부를 반환하는 헬퍼 메서드
     * @param newValue 새로운 값
     * @param oldValue 기존 값
     * @param setter   값을 할당할 setter 메서드 (ex: this::setUsername)
     * @return       값이 실제로 변경되었으면 true
     */
    private <T> boolean updateField(T newValue, T oldValue, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue); // setter 메서드 호출
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\n" +
                "User {" + "\n" +
                "  ID        = " + this.getId() + ",\n" +
                "  username  = " + this.username + ",\n" +
                "  email     = " + this.email + ",\n" +
                "  updatedAt = " + this.getUpdatedAt() + "\n" +
                "}";
    }
}

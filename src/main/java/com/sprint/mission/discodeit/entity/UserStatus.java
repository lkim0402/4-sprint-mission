package com.sprint.mission.discodeit.entity;

public enum UserStatus {
    ACTIVE("활동중"),
    INACTIVE("휴면 상태"),
    WITHDRAWN("탈퇴");

    private String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

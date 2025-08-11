package com.sprint.mission.discodeit.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  //Auth
  AUTHENTICATION_FAILED(HttpStatus.CONFLICT, "A001", "Login attempt failed."),

  // User
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "The requested user was not found."),
  DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U003", "This username is already in use."),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U004", "This email is already in use."),

  // Channel
  CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "The requested channel was not found."),
  CANNOT_UPDATE_PRIVATE_CHANNEL(HttpStatus.BAD_REQUEST, "C002",
      "Private channel details cannot be updated."),
  USER_ALREADY_IN_CHANNEL(HttpStatus.CONFLICT, "C003", "User already exists in channel"),
  USER_NOT_IN_CHANNEL(HttpStatus.CONFLICT, "C004", "User does not exist in channel"),

  // Message
  MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "The requested channel was not found."),
  MESSAGE_ACTION_NOT_PERMITTED(HttpStatus.FORBIDDEN, "M002",
      "You can only edit or delete your own messages."),

  // ReadStatus
  READSTATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "The requested readStatus was not found."),
  READSTATUS_WITH_USERID_AND_CHANNELID_EXISTS(HttpStatus.CONFLICT, "M002",
      "The request readStatus already exists with the requested userId and channelId"),

  // UserStatus
  USERSTATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "US001", "The requested userStatus was not found."),
  USERSTATUS_WITH_USERID_NOT_FOUND(HttpStatus.NOT_FOUND, "US002",
      "The requested userStatus with userId was not found."),
  USERSTATUS_ALREADY_EXISTS(HttpStatus.CONFLICT, "US003",
      "The requested userStatus already exists"),

  // binaryContent
  BINARY_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "B001",
      "The request binary content was not found.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}

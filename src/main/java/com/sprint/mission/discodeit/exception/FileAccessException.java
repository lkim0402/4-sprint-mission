package com.sprint.mission.discodeit.exception;

public class FileAccessException extends RuntimeException {

  private String message;
  private int status;

  public FileAccessException(String message, int status) {
    super(message);
    this.status = status;
  }
}

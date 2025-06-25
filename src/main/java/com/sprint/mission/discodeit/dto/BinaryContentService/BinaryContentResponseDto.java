package com.sprint.mission.discodeit.dto.BinaryContentService;

public record BinaryContentResponseDto(
    byte[] bytes,
    String fileName,
    String fileType
) {
    @Override
    public String toString() {
        return "\n" +
                "    BinaryContentResponseDto {" + "\n" +
                "    fileName = " + this.fileName + ",\n" +
                "    fileType = " + this.fileType + "\n" +
                "  }";
    }
}

package com.sprint.mission.discodeit.storage.s3;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@ConditionalOnProperty(
    prefix = "discodeit.storage",
    name = "type",
    havingValue = "s3"
)
@RequiredArgsConstructor
public class S3BinaryContentStorage implements BinaryContentStorage {

  private final String accessKey;
  private final String secretKey;
  private final String region;
  private final String bucket;

  @Override
  public UUID put(UUID binaryContentId, byte[] bytes) {

    try {

      S3Client s3Client = getS3Client();
      String contentType = "application/octet-stream";

      PutObjectRequest putReq = PutObjectRequest.builder()
          .bucket(bucket)
          .key(binaryContentId.toString())
          .contentType(contentType)
          .build();

      s3Client.putObject(putReq,
          RequestBody.fromBytes(bytes)
      );
    } catch (Exception e) {
      throw new RuntimeException("S3 업로드 실패", e);
    }

    return binaryContentId;
  }

  @Override
  public InputStream get(UUID binaryContentId) {
    S3Client s3Client = getS3Client();

    String key = binaryContentId.toString();

    GetObjectRequest getReq = GetObjectRequest.builder()
        .bucket(bucket)
        .key(key)
        .build();

    return s3Client.getObject(getReq);
  }

  @Override
  public ResponseEntity<?> download(BinaryContentDto metaData) {
    String key = metaData.id().toString();
    String presignedUrl = generatePresignedUrl(key, metaData.contentType());
    return ResponseEntity.status(302).location(URI.create(presignedUrl)).build();
  }

  public S3Client getS3Client() {
    if (accessKey != null && !accessKey.isBlank()) {
      return S3Client.builder()
          .region(Region.of(region))
          .credentialsProvider(
              StaticCredentialsProvider.create(
                  AwsBasicCredentials.create(
                      accessKey,
                      secretKey
                  )
              )
          )
          .build();
    }
    // 그렇지 않으면: 기본 체인(환경변수, 프로파일, IAM Role)을 자동 탐색
    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build();
  }

  public String generatePresignedUrl(String key, String contentType) {
    S3Presigner presigner = S3Presigner
        .builder()
        .s3Client(getS3Client())
        .build();

    // download -> GET
    GetObjectRequest getReq = GetObjectRequest.builder()
        .bucket(bucket)
        // tells the presigned URL to instruct S3 to serve the file with the correct Content-Type
        .responseContentType(contentType)
        .key(key)
        .build();

    GetObjectPresignRequest preReq = GetObjectPresignRequest.builder()
        .getObjectRequest(getReq)
        .signatureDuration(Duration.ofMinutes(5)) // duration
        .build();

    return presigner.presignGetObject(preReq).url().toString();
  }

}

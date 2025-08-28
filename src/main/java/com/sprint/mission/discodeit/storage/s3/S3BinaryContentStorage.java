package com.sprint.mission.discodeit.storage.s3;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
@Component
public class S3BinaryContentStorage implements BinaryContentStorage {

  private final String bucket;
  private final S3Client s3Client;
  private final Long presignedUrlExpiration;

  public S3BinaryContentStorage(
      @Value("${discodeit.storage.s3.bucket}") String bucket,
      @Value("${discodeit.storage.s3.presigned-url-expiration}") Long presignedUrlExpiration
  ) {
    this.bucket = bucket;
    this.presignedUrlExpiration = presignedUrlExpiration;

    // AWS SDK's builder automatically finds credentials and region from the environment
    this.s3Client = S3Client.builder().build();
  }


  @Override
  public UUID put(UUID binaryContentId, byte[] bytes) {

    try {
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

    return ResponseEntity.status(HttpStatus.FOUND) // Or HttpStatus.SEE_OTHER (303)
        .location(URI.create(presignedUrl))
        .build();
  }

  public S3Client getS3Client() {
    return S3Client.builder().build();
  }

  public String generatePresignedUrl(String key, String contentType) {
    S3Presigner presigner = S3Presigner
        .builder()
        .s3Client(s3Client)
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
        .signatureDuration(Duration.ofMinutes(presignedUrlExpiration)) // duration
        .build();

    return presigner.presignGetObject(preReq).url().toString();
  }

}

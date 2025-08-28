package com.sprint.mission.discodeit.storage.s3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@TestInstance(Lifecycle.PER_CLASS)
public class AWSS3Test {

  private S3Client s3Client;
  private S3Presigner s3Presigner;
  private String bucket; // bucketName
  private final String testFileKey = UUID.randomUUID().toString();

  @BeforeAll
  void setup() throws IOException {
//    Properties properties = new Properties();
//    properties.load(new FileInputStream(".env"));
//    bucket = properties.getProperty("S3_BUCKET_NAME");
    bucket = getConfigValue("S3_BUCKET_NAME");

    // setting up s3Client
    this.s3Client = S3Client.builder().build();

    // setting up s3Presigner
    s3Presigner = S3Presigner
        .builder()
        .s3Client(s3Client)
        .build();
  }

  @Test
  @Order(1)
  @DisplayName("S3 파일 업로드 테스트")
  void uploadS3Test() {
    // given
    String content = "Hello!";

    PutObjectRequest putReq = PutObjectRequest.builder()
        .bucket(bucket)
        .key(testFileKey)
        .build();

    // when
    PutObjectResponse putObjectResponse = s3Client.putObject(putReq,
        RequestBody.fromString(content, StandardCharsets.UTF_8)
    );

    // then
    assertNotNull(putObjectResponse);
    assertEquals(HttpStatus.OK.value(), putObjectResponse.sdkHttpResponse().statusCode());
    assertNotNull(putObjectResponse.eTag());
  }

  @Test
  @Order(2)
  @DisplayName("S3 파일 다운로드 테스트")
  void downloadFromS3Test() throws IOException {
    // given
    String content = "Hello!";
    byte[] contentBytes = content.getBytes();
    GetObjectRequest getReq = GetObjectRequest.builder()
        .bucket(bucket)
        .key(testFileKey)
        .build();

    // when
    ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getReq);

    // then
    byte[] bytes = s3Object.readAllBytes();
    assertArrayEquals(bytes, contentBytes);

  }

  @Test
  @Order(3)
  @DisplayName("S3 Presigned URL 생성 테스트")
  void generatePresignedUrl() {
    // given
    String contentType = "text/html";
    GetObjectRequest getReq = GetObjectRequest.builder()
        .bucket(bucket)
        .responseContentType(contentType)
        .key(testFileKey)
        .build();

    GetObjectPresignRequest preReq = GetObjectPresignRequest.builder()
        .getObjectRequest(getReq)
        .signatureDuration(Duration.ofMinutes(5)) // duration
        .build();

    // when
    String presignedUrl = s3Presigner.presignGetObject(preReq).url().toString();

    // then
    assertNotNull(presignedUrl);
    assertTrue(presignedUrl.startsWith("https://" + bucket + ".s3."));
  }

  @AfterAll
  void tearDown() {
    if (s3Client != null) {
      s3Client.deleteObject(builder -> builder.bucket(bucket).key(testFileKey));
      System.out.println("Test file deleted: " + testFileKey);
      s3Client.close();
    }
    if (s3Presigner != null) {
      s3Presigner.close();
    }
  }

  private String getConfigValue(String key) throws IOException {
    // First try environment variable
    String value = System.getenv(key);
    if (value != null && !value.isEmpty()) {
      return value;
    }

    // Fall back to .env file if it exists
    try {
      File envFile = new File(".env");
      if (envFile.exists()) {
        Properties properties = new Properties();
        properties.load(new FileInputStream(envFile));
        return properties.getProperty(key);
      }
    } catch (IOException e) {
      // Log warning but don't fail
      System.out.println("Warning: Could not read .env file: " + e.getMessage());
    }

    return null; // Return null instead of throwing exception
  }
}

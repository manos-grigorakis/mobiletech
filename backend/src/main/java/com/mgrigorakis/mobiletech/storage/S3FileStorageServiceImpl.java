package com.mgrigorakis.mobiletech.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3FileStorageServiceImpl implements FileStorageService {
    private final S3Client s3Client;

    @Value("${app.storage.endpoint}")
    private String endpoint;

    @Value("${app.storage.bucket-name}")
    private String bucketName;

    @Override
    public void store(String key, byte[] content, String contentType) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(content));
            log.info("Successfully uploaded file to S3 bucket {}", bucketName);
        } catch (S3Exception e) {
            log.error("S3 error while uploading file to S3 bucket {}", bucketName, e);
            throw new RuntimeException("S3 error while uploading file to S3 bucket " + bucketName, e);
        } catch (SdkClientException e) {
            log.error("SDK Client error while uploading file to S3 bucket {}", bucketName, e);
            throw new RuntimeException("SDK Client error while uploading file to S3 bucket " + bucketName, e);
        } catch (Exception e) {
            log.error("Error while uploading file to S3 bucket {}", bucketName, e);
            throw new RuntimeException("Error while uploading file to S3 bucket "+ bucketName, e);
        }
    }

    @Override
    public String getUrl(String prefix, String key) {
        return endpoint + "/" + bucketName + "/" + prefix + "/" + key;
    }
}

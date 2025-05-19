package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.service.AWSS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSS3ServiceImpl implements AWSS3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        String fileNameExtension = file.getOriginalFilename().substring(file.getOriginalFilename()
                .lastIndexOf(".") + 1);
        String key = folder + "/" + UUID.randomUUID().toString() + fileNameExtension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if (response.sdkHttpResponse().isSuccessful()) {
                return "http://" + bucketName + ".s3.amazonaws.com/" + key;
            }
            else
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    @Override
    public void deleteFile(String imageUrl) {
        String key = extractKeyFromUrl(imageUrl);
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DELETE_FILE_ERROR);
        }
    }

    private String extractKeyFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf(".com/") + 5);
    }

}

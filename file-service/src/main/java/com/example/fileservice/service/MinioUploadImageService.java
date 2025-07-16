package com.example.fileservice.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioUploadImageService {
    final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName = UUID.randomUUID() + " " + file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        // trả về url truy cập ảnh
        return minioClient.getPresignedObjectUrl(
                io.minio.GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .method(io.minio.http.Method.GET)
                        .build()
        );
    }

    public void deleteImage(String url) {
        if (url == null || url.isBlank()) return;
        String objectName = extractObjectName(url);
        try {
            minioClient.removeObject(
                    io.minio.RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Xóa ảnh thất bại");
        }
    }

    private String extractObjectName(String url) {
        // Ví dụ: http://localhost:9000/product-images/abc123.jpg?...
        // Lấy phần sau bucket (product-images/)
        int index = url.indexOf(bucket + "/");
        if (index == -1) throw new RuntimeException("URL không hợp lệ");
        String objectName = url.substring(index + bucket.length() + 1);
        // Nếu có dấu ? (query params), cắt bỏ
        int index2 = objectName.indexOf("?");
        if (index2 != -1) objectName = objectName.substring(0, index2);
        return objectName;
    }
}

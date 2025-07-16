package com.example.fileservice.controller;

import com.example.commonlib.dto.api.ApiResponse;
import com.example.fileservice.service.MinioUploadImageService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("upload")

public class FileUploadController {
    @Autowired
    private MinioUploadImageService minioUploadImageService;


    @PostMapping("/image")
    public ApiResponse<String> uploadImage(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = minioUploadImageService.uploadImage(file);
        return ApiResponse.<String>builder()
                .message("Upload ảnh thành công")
                .data(url)
                .build();
    }
    @DeleteMapping("/url")
    public ApiResponse<String> deleteImage(@RequestParam("url") String url) {
         minioUploadImageService.deleteImage(url);
        return ApiResponse.<String>builder()
                .message("Xóa ảnh thành công")
                .data(url)
                .build();
    }
}

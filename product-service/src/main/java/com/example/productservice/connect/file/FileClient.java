package com.example.productservice.connect.file;

import com.example.commonlib.dto.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service")
public interface FileClient {
    @PostMapping(value = "/upload/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<String> uploadImage(@RequestPart("file") MultipartFile file);


    @DeleteMapping(value = "/upload/url")
    ApiResponse<String> deleteImage(@RequestParam("url") String url);
}

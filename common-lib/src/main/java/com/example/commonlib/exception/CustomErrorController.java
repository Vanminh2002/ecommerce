package com.example.commonlib.exception;

import com.example.commonlib.dto.api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ApiResponse<?>> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");
        if (statusCode == null) statusCode = 500;
        if (message == null) message = "Lỗi hệ thống";

        ApiResponse<?> response = ApiResponse.builder()
                .code(statusCode)
                .message(message)
                .data(null)
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }
}

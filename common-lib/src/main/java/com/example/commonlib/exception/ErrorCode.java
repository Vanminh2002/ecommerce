package com.example.commonlib.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
//    1000 : lỗi product
//    2000 : lỗi category
//    3000 : lỗi order
//    4000 : lỗi user


    NOT_FOUND(HttpStatus.NOT_FOUND, 401, "Không tìm thấy dữ liệu"),
    EXISTED(HttpStatus.BAD_REQUEST, 403, "Dữ liệu đã tồn tại"),
    ERROR_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Lỗi hệ thống");

    ErrorCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private int code;
    private String message;
}

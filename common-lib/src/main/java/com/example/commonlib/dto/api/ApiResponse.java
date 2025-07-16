package com.example.commonlib.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    int code = 200;
    String message;
    T data;
//    data sẽ trả về object trống thay vì null
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, (T) new Object());
    }
}
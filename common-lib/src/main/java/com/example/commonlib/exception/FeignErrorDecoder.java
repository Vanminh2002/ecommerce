//package com.example.commonlib.exception;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class FeignErrorDecoder implements ErrorDecoder {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        try {
//            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
//            JsonNode node = objectMapper.readTree(body);
//
//            int code = node.path("code").asInt(response.status());
//            ErrorCode errorCode = resolveErrorCode(code);
//
//            return new AppException(errorCode);
//
//        } catch (Exception ex) {
//            return new AppException(ErrorCode.ERROR_SERVER);
//        }
//    }
//
//    private ErrorCode resolveErrorCode(int code) {
//        for (ErrorCode ec : ErrorCode.values()) {
//            if (ec.getCode() == code) return ec;
//        }
//        return ErrorCode.ERROR_SERVER;
//    }
//}

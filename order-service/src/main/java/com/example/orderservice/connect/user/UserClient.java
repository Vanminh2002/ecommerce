package com.example.orderservice.connect.user;

import com.example.orderservice.connect.user.dto.UserDto;
import com.example.orderservice.dto.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/get-by/{id}")
    ApiResponse<UserDto> getUserById(@PathVariable("id") Long id);
}

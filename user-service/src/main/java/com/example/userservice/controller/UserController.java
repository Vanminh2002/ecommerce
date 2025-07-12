package com.example.userservice.controller;

import com.example.userservice.dto.api.ApiResponse;
import com.example.userservice.dto.user.request.UserCreateRequestDto;
import com.example.userservice.dto.user.response.UserResponse;
import com.example.userservice.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("create")
    ApiResponse<UserResponse> create(@RequestBody UserCreateRequestDto request) {
        UserResponse response = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .data(response)
                .message("User created")
                .build();
    }

    @GetMapping("get-all")
    ApiResponse<Page<UserResponse>> getAll(@RequestParam(required = false) String name,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> response = userService.getAllUsers(name, page, size);
        return ApiResponse.<Page<UserResponse>>builder()
                .data(response)
                .message("Users list")
                .build();
    }

    @GetMapping("get-by/{id}")
    ApiResponse<UserResponse> getById(@PathVariable Long id) {
        UserResponse response = userService.getById(id);
        return ApiResponse.<UserResponse>builder()
                .data(response)
                .message("Tìm thấy user với id" + " = " + id)
                .build();
    }
}

package com.example.userservice.services;

import com.example.userservice.dto.api.ApiResponse;
import com.example.userservice.dto.user.request.UserCreateRequestDto;
import com.example.userservice.dto.user.response.UserResponse;
import com.example.userservice.entities.User;
import com.example.userservice.mapper.user.UserMapper;
import com.example.userservice.reposiitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;


    public UserResponse createUser(UserCreateRequestDto request) {
        try {
            if (userRepository.existsByName(request.getName())) {
                throw new RuntimeException("User name already exists");
            }
            User user = userMapper.toUser(request);
            user = userRepository.save(user);
            return userMapper.toUserResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating user");
        }
    }

    public Page<UserResponse> getAllUsers(String name, int page, int size) {
        try {

            Pageable pageable = PageRequest.of(page, size);
            Page<User> users = userRepository.searchByName(name, pageable);
//            if (users.isEmpty()) {
//                throw new RuntimeException("không thấy user");
//            }
            return users.map(userMapper::toUserResponse);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy tất cả user");
        }
    }


    public UserResponse getById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào"));
        return userMapper.toUserResponse(user);
    }
}


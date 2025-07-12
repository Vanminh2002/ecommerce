package com.example.userservice.mapper.user;

import com.example.userservice.dto.user.request.UserCreateRequestDto;
import com.example.userservice.dto.user.response.UserResponse;
import com.example.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateRequestDto request);

    UserResponse toUserResponse(User user);
}

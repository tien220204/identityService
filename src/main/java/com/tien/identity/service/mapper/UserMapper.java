package com.tien.identity.service.mapper;

import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.request.UserUpdateRequest;
import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //resquest se anh xa vao target la user ma khong can tao ra 1 doi tuong ban sao moi => k ton bo nho
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
//    @Mapping(target = "firstname", expression = "java(UserReponse.getFirstName()+\" \"+UserReponse.getLastName())")
    UserResponse toUserReponse(User user);
}

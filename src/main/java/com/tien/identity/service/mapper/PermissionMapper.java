package com.tien.identity.service.mapper;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.request.UserUpdateRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.entity.Permission;
import com.tien.identity.service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

//    @Mapping(target = "firstname", expression = "java(UserReponse.getFirstName()+\" \"+UserReponse.getLastName())")
    PermissionResponse toPermissionReponse(Permission permission);
}

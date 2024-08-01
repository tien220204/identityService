package com.tien.identity.service.mapper;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.entity.Permission;
import com.tien.identity.service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

//    @Mapping(target = "firstname", expression = "java(UserReponse.getFirstName()+\" \"+UserReponse.getLastName())")
    RoleResponse toRoleReponse(Role role);
}

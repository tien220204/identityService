package com.tien.identity.service.service;

import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.mapper.RoleMapper;
import com.tien.identity.service.repository.PermissionRepository;
import com.tien.identity.service.repository.RoleResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleResponsitory roleResponsitory;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        roleResponsitory.save(role);
        return  roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleResponsitory.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(@PathVariable String id){
        roleResponsitory.deleteById(id);
    }

}

package com.tien.identity.service.service;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.mapper.PermissionMapper;
import com.tien.identity.service.mapper.RoleMapper;
import com.tien.identity.service.repository.PermissionRepository;
import com.tien.identity.service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper mapper;

    public RoleResponse create (RoleRequest request){
        var role = mapper.toRole(request);
        return mapper.toRoleReponse(roleRepository.save(role)) ;
    }

    public List<RoleResponse> getAll(){
        var permissions = roleRepository.findAll();
        return permissions.stream().map(mapper::toRoleReponse).toList();
    }
    public  void delete(String permissionName){
        roleRepository.deleteById(permissionName);
    }
}

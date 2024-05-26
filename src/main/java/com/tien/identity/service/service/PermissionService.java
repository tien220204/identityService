package com.tien.identity.service.service;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.entity.Permission;
import com.tien.identity.service.mapper.PermissionMapper;
import com.tien.identity.service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return  permissionMapper.toPermissionReponse(permission);
    }
    public List<PermissionResponse> getAll(){
        //truy ra tat ca permission kieu list<entity>
        var permissions = permissionRepository.findAll();
        //chuyen tung phan tu trong list sang response bang stream.map => bo vao list
        return permissions.stream().map(permissionMapper::toPermissionReponse).toList();

    }
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }

}

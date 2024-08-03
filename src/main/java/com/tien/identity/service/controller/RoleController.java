package com.tien.identity.service.controller;

import com.tien.identity.service.dto.request.ApiResponse;
import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.entity.Role;
import com.tien.identity.service.service.PermissionService;
import com.tien.identity.service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PreAuthorize("hasAuthority('CREATE_DATA')")
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        System.out.println(request);
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }
    @GetMapping("findAll")
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("delete/{roleName}")
    ApiResponse<Void> delete(@PathVariable String roleName){
        roleService.delete(roleName);
        return ApiResponse.<Void>builder()
                .build();
    }
}
package com.tien.identity.service.controller;

import com.tien.identity.service.dto.request.ApiResponse;
import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping("/createRole")
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PutMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable String id){
        roleService.delete(id);
        return ApiResponse.<String>builder()
                .result("Xoa thanh cong")
                .build();
    }
}

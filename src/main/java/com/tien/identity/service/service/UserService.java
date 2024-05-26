package com.tien.identity.service.service;

import java.util.HashSet;
import java.util.List;

import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.enums.Roles;
import com.tien.identity.service.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.request.UserUpdateRequest;
import com.tien.identity.service.entity.User;
import com.tien.identity.service.exception.AppException;
import com.tien.identity.service.exception.ErrorCode;
import com.tien.identity.service.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;


    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        var user = userMapper.toUser(request);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
//        user.setRoles(roles);
        return userMapper.toUserReponse(userRepository.save(user));
    }

    //lay info cua tai khoan dang dang nhap trong securityContextHolder
    public  UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserReponse(user);
    }

    public UserResponse updateRequest(String id, UserUpdateRequest request) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user,request);

        return userMapper.toUserReponse(userRepository.save(user));
    }
    //xac thuc truoc khi thuc hien
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return  userRepository.findAll().stream().map(userMapper::toUserReponse).toList();

    }
    //thuc hien -> xac thuc va dua ra du lieu neu xac thuc dung
    //tra ve du lieu neu username nguoi truy xuat(token) = username dang duoc tim kiem
    //return object o day la User va authentication.name là subject trong payload
    @PostAuthorize("returnObject.username  == authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserReponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

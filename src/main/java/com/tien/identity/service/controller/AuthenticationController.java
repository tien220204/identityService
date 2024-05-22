package com.tien.identity.service.controller;

import com.nimbusds.jose.JOSEException;
import com.tien.identity.service.dto.request.ApiResponse;
import com.tien.identity.service.dto.request.AuthenticationRequest;
import com.tien.identity.service.dto.request.IntropectRequest;
import com.tien.identity.service.dto.response.AuthenticationResponse;
import com.tien.identity.service.dto.response.IntrospectResponse;
import com.tien.identity.service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        var result =  authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> intropect(@RequestBody IntropectRequest request) throws ParseException, JOSEException {
        var result =  authenticationService.intropect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

}

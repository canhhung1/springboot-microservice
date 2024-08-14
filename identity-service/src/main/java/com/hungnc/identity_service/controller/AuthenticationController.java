package com.hungnc.identity_service.controller;

import com.hungnc.identity_service.dto.ApiResponse;
import com.hungnc.identity_service.dto.AuthenticationRequest;
import com.hungnc.identity_service.dto.AuthenticationResponse;
import com.hungnc.identity_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        boolean result = authenticationService.authenticate(authenticationRequest);

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        return apiResponse;
    }

}

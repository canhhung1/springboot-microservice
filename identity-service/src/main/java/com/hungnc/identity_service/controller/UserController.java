package com.hungnc.identity_service.controller;

import com.hungnc.identity_service.dto.ApiResponse;
import com.hungnc.identity_service.dto.UserDto;
import com.hungnc.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("users")
    public ApiResponse<UserDto> createUser(@RequestBody UserDto userDto) {
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.createUser(userDto));
        return apiResponse;
    }

    @GetMapping("users")
    public ApiResponse<List<UserDto>> getUsers() {
        ApiResponse<List<UserDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.getUsers());
        return apiResponse;
    }

    @GetMapping("users/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable String id) {
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.getUserById(id));
        return apiResponse;
    }
}

package com.hungnc.identity_service.controller;

import com.hungnc.identity_service.dto.ApiResponse;
import com.hungnc.identity_service.dto.UserDto;
import com.hungnc.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("users")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(this.userService.createUser(userDto), HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(this.userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable String id) {
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.getUserById(id));
        return apiResponse;
    }
}

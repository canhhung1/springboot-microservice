package com.hungnc.identity_service.controller;

import com.hungnc.identity_service.dto.request.UserDto;
import com.hungnc.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

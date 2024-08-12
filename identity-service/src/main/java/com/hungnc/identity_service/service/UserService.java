package com.hungnc.identity_service.service;

import com.hungnc.identity_service.dto.request.UserDto;
import com.hungnc.identity_service.entity.User;
import com.hungnc.identity_service.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User createUser(UserDto user) {
        User newUser = modelMapper.map(user, User.class);
        return this.userRepository.save(newUser);
    }

    public List<UserDto> getUser() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}

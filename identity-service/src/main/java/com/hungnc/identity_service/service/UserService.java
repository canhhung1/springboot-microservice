package com.hungnc.identity_service.service;

import com.hungnc.identity_service.dto.UserDto;
import com.hungnc.identity_service.entity.User;
import com.hungnc.identity_service.exception.AppException;
import com.hungnc.identity_service.exception.ErrorCode;
import com.hungnc.identity_service.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> getUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }
}

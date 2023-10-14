package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.MasterResponse;
import com.example.model.User;
import com.example.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;
    @Override
    public MasterResponse createUser(UserDto userDto) {
        MasterResponse masterResponse = new MasterResponse();
        User user = new User(userDto);
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()){
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("Sign up failed!! Email already registered!!");
        } else {
            masterResponse.setStatus("Success");
            masterResponse.setCode("200");
            masterResponse.setPayload(userRepository.save(user));
        }

        return masterResponse;
    }

    @Override
    public MasterResponse userLogin(LoginDto loginDto) {

        MasterResponse masterResponse = new MasterResponse();

        Optional<User> user = userRepository.userLogin(loginDto.getEmail(),loginDto.getPassword());
        if (user.isPresent()){
//
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(user.get());
        } else {
//            throw new CustomException("Login failed!! Username or password is incorrect.");
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("Login failed!! Username or password is incorrect.");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse getUserById(int id) {

        MasterResponse masterResponse = new MasterResponse();
        User user = userRepository.findUserById(id);

        if (user != null) {
            log.info("GET-USER-BY-ID API RESPONSE {}", user );
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(user);
        } else {
//            throw new CustomException("User with id: "+id+" not found!");
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("User with id: "+id+" not found!");
        }
        return masterResponse;
    }
}

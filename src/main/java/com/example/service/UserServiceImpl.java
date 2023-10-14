package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.exception.CustomException;
import com.example.model.User;
import com.example.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;
    @Override
    public User createUser(UserDto userDto) {
        User user = new User(userDto);
        return userRepository.save(user);
    }

    @Override
    public Object userLogin(LoginDto loginDto) {

        Optional<User> user = userRepository.userLogin(loginDto.getEmail(),loginDto.getPassword());
        if (!user.isPresent()){
            throw new CustomException("Login failed!! Username or password is incorrect.");
        }
        return "Log in successful!!!";
    }

    @Override
    public User getUserById(int id) {
        User user = userRepository.findUserById(id);

        if (user != null) {
            log.info("GET-USER-BY-ID API RESPONSE {}", user );
            return user;
        } else {
            throw new CustomException("User with id: "+id+" not found!");
        }
    }
}

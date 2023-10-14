package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.User;

public interface IUserService {
    User createUser(UserDto userDto);

    Object userLogin(LoginDto loginDto);

    User getUserById(int id);
}

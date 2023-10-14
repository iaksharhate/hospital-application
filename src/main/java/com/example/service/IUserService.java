package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.MasterResponse;
import com.example.model.User;

public interface IUserService {
    MasterResponse createUser(UserDto userDto);

    MasterResponse userLogin(LoginDto loginDto);

    MasterResponse getUserById(int id);
}

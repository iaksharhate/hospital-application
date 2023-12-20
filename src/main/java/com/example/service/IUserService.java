package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.MasterResponse;
import com.example.model.User;
import org.springframework.http.HttpStatus;

public interface IUserService {
    MasterResponse createUser(UserDto userDto);

    MasterResponse userLogin(LoginDto loginDto);

    MasterResponse getUserById(int id);

    MasterResponse getDoctors();

    MasterResponse updateUserById(int id, UserDto userDto);

    MasterResponse getAllPatients();
}

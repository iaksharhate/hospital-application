package com.example.service;

import com.example.model.Doctor;
import com.example.model.UserDto;

public interface IDoctorService {
    Doctor createDoctor(UserDto userDto);
}

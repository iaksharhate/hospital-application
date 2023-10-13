package com.example.service;

import com.example.model.Patient;
import com.example.model.UserDto;

public interface IPatientService {
    Patient createPatient(UserDto userDto);
}

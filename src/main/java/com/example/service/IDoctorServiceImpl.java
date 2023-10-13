package com.example.service;

import com.example.model.Doctor;
import com.example.model.UserDto;
import com.example.repository.IDoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IDoctorServiceImpl implements IDoctorService{

    @Autowired
    private IDoctorRepository doctorRepository;

    @Override
    public Doctor createDoctor(UserDto userDto) {

        Doctor doctor = new Doctor(userDto);
        log.info("USER-SIGNUP-API-DOCTOR : {}", doctor);
        return doctorRepository.save(doctor);
    }
}

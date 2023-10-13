package com.example.service;

import com.example.model.Patient;
import com.example.model.UserDto;
import com.example.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPatientServiceImpl implements IPatientService{

    @Autowired
    private IPatientRepository patientRepository;
    @Override
    public Patient createPatient(UserDto userDto) {

        Patient patient = new Patient(userDto);

        return patientRepository.save(patient);
    }
}

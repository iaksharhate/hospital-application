package com.example.controller;

import com.example.dto.Response;
import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.model.UserDto;
import com.example.service.IDoctorService;
import com.example.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IPatientService patientService;
    @Autowired
    private IDoctorService doctorService;

    @PostMapping("/signup")
    public ResponseEntity<Response> userSignp(@RequestBody UserDto userDto){

        Response response = new Response();

        if ("patient".equalsIgnoreCase(userDto.getUser())){
            Patient patient = patientService.createPatient(userDto);
             response.setMessage("Patient created successfully!");
             response.setData(patient);
        } else if ("doctor".equalsIgnoreCase(userDto.user)) {
            Doctor doctor = doctorService.createDoctor(userDto);
            response.setMessage("Doctor created successfully!");
            response.setData(doctor);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

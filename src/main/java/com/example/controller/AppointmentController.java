package com.example.controller;

import com.example.dto.AppointmentReqDto;
import com.example.dto.Response;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;


    @PostMapping("/createAppointment")
    public ResponseEntity<MasterResponse> createAppointment(@RequestBody AppointmentReqDto appointmentReqDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentReqDto), HttpStatus.OK);
    }

    @GetMapping("/getByUserId")
    public ResponseEntity<MasterResponse> getAppointmentsForPatient(@RequestParam int id, @RequestParam String user) {
        return new ResponseEntity<>(appointmentService.apptsByUserId(id, user), HttpStatus.OK);
    }
}

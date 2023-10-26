package com.example.controller;

import com.example.model.MasterResponse;
import com.example.service.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("slots")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TimeSlotController {

    @Autowired
    private ITimeSlotService timeSlotService;

    @GetMapping("/getByDoctorId/{id}")
    public ResponseEntity<MasterResponse> getByDoctorId(@PathVariable(value = "id") int id ){
        return new ResponseEntity<>(timeSlotService.getByDoctorId(id), HttpStatus.OK);
    }
}

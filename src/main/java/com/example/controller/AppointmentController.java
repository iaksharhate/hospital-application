package com.example.controller;

import com.example.dto.AppointmentReqDto;
import com.example.model.MasterResponse;
import com.example.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;


    @PostMapping("/createAppointment")
    public ResponseEntity<MasterResponse> createAppointment(@RequestBody AppointmentReqDto appointmentReqDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentReqDto), HttpStatus.OK);
    }

    @GetMapping("/getByUserId/{id}/{user}")
    public ResponseEntity<MasterResponse> getAppointmentsForPatient(@PathVariable(value = "id") int id,
                                                                    @PathVariable(value = "user") String user) {
        return new ResponseEntity<>(appointmentService.apptsByUserId(id, user), HttpStatus.OK);
    }

    @PatchMapping("/rescheduleById/{id}")
    public ResponseEntity<MasterResponse> rescheduleByIdAppointment(@PathVariable(value = "id") int id,
                                                            @RequestBody AppointmentReqDto appointmentReqDto){
        return new ResponseEntity<>(appointmentService.rescheduleByIdById(id, appointmentReqDto),HttpStatus.OK);
    }

    @PatchMapping("/cancelById/{id}")
    public ResponseEntity<MasterResponse> cancelAppointment(@PathVariable(value = "id") int id,
                                                                    @RequestBody AppointmentReqDto appointmentReqDto){
        return new ResponseEntity<>(appointmentService.cancelById(id, appointmentReqDto),HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MasterResponse> getById(@PathVariable(value = "id") int id){
        return new ResponseEntity<>(appointmentService.getById(id), HttpStatus.OK);
    }
}

package com.example.service;

import com.example.dto.AppointmentReqDto;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import org.springframework.http.HttpStatus;

public interface IAppointmentService {
    MasterResponse createAppointment(AppointmentReqDto appointment);

    MasterResponse apptsByUserId(int id, String user);

    MasterResponse rescheduleByIdById(int id, AppointmentReqDto appointmentReqDto);

    MasterResponse cancelById(int id, AppointmentReqDto appointmentReqDto);
    MasterResponse doneById(int id, AppointmentReqDto appointmentReqDto);

    MasterResponse getById(int id);

    MasterResponse getAll();


}

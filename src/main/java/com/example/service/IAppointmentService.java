package com.example.service;

import com.example.dto.AppointmentReqDto;
import com.example.model.Appointment;
import com.example.model.MasterResponse;

public interface IAppointmentService {
    MasterResponse createAppointment(AppointmentReqDto appointment);

    MasterResponse apptsByUserId(int id, String user);
}

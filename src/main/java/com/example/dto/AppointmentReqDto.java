package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentReqDto {

    public String dateTime;
    public String status;
    private int patientId;
    private int doctorId;
}

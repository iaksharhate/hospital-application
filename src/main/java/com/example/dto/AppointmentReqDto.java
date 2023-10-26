package com.example.dto;

import lombok.Data;


@Data
public class AppointmentReqDto {

    public String date;
    public String time;
    public String status;
    public String description;
    public int patientId;
    public int doctorId;
}

package com.example.dto;

import lombok.Data;

@Data
public class AppointmentResDto {
    public int id;
    public String date;
    public String time;
    public String status;
    public String description;
    public String patientName;
    public String doctorName;
    public int doctorId;
}

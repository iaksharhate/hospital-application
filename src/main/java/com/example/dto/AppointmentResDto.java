package com.example.dto;

import lombok.Data;

@Data
public class AppointmentResDto {
    public int id;
    public String dateTime;
    public String status;
    public String patientName;
    public String doctorName;
}

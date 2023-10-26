package com.example.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MailRequest {

    private String to;
    private String cc;
    private String subject;
    private Map<String, Object> AppointmentDetails;
}

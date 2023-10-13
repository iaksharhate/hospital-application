package com.example.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorDto {

    public String age;
    public String bloodGroup;
    public String firstName;
    public String lastName;
    public String gender;
    public String email;
    public String password;
    public String city;
    public String state;
    public int pinCode;
    public String photo;
}

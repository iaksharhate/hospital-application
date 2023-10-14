package com.example.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    public String firstName;
    public String lastName;
    public String user;
    public String age;
    public String gender;
    public String email;
    public String password;
    public String bloodGroup;
    public String city;
    public String state;
    public int pinCode;
    public String photo;
    public String specialization;
    public String experience;
    public String fees;
}

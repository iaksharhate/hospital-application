package com.example.dto;

import lombok.Data;

@Data
public class LoginResDto {

    public int id;
    public String token;
    public String user;
    public String email;
    public String firstName;
    public String lastName;
    public String age;
    public String gender;
    public String city;
    public String state;
    public int pinCode;

}

package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String user;
    private String age;
    private String bloodGroup;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String city;
    private String state;
    private int pinCode;
    private String photo;

    public Doctor(UserDto userDto) {
        this.age = userDto.getAge();
        this.user = userDto.getUser();
        this.bloodGroup = userDto.getBloodGroup();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.gender = userDto.getGender();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.city = userDto.getCity();
        this.state = userDto.getState();
        this.pinCode = userDto.getPinCode();
        this.photo = userDto.getPhoto();
    }
}

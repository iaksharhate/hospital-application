package com.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Patient {

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

    public Patient(UserDto userDto) {
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

//    @OneToMany(mappedBy = "patient")
//    @JsonManagedReference
//    private List<Appointment> appointments;
}

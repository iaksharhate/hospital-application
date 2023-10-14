package com.example.model;

import com.example.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
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

    public User(UserDto userDto) {
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
        this.specialization = userDto.getSpecialization();
        this.experience = userDto.getExperience();
        this.fees = userDto.getFees();
    }
}

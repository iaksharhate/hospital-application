package com.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;
    private String date;
    private String time;
    private String status;
    private String description;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;
}

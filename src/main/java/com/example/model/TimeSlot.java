package com.example.model;

import com.example.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "a_date")
    private String date;
    @Column(name = "a_time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToOne
    @JoinColumn(name = "a_id")
    private Appointment appointment;


    public TimeSlot(String date, String time, User doctor, Appointment appointment) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.appointment = appointment;
    }
}

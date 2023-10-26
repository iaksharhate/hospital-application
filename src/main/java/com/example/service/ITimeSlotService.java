package com.example.service;

import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.model.User;

public interface ITimeSlotService {
    void createSlot(User doctor, String date, String time, Appointment appointment);

    MasterResponse getByDoctorId(int id);

    void updateSlot(int id, String date, String time);
}

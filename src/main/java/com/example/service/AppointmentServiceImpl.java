package com.example.service;

import com.example.dto.AppointmentReqDto;
import com.example.dto.Response;
import com.example.exception.CustomException;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.model.User;
import com.example.repository.IAppointmentRepository;
import com.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements IAppointmentService{

    @Autowired
    private IAppointmentRepository appointmentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public MasterResponse createAppointment(AppointmentReqDto appointmentReqDto) {

        MasterResponse masterResponse = new MasterResponse();

        User patient = userRepository.findUserById(appointmentReqDto.getPatientId());
        User doctor = userRepository.findUserById(appointmentReqDto.getDoctorId());

        if (patient == null && doctor == null){
            throw new CustomException("Doctor or patient does is now exist");
        }

        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentReqDto.getDateTime());
        appointment.setStatus(appointmentReqDto.getStatus());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        masterResponse.setCode("200");
        masterResponse.setStatus("success");
        masterResponse.setPayload(appointmentRepository.save(appointment));
        return masterResponse;
    }
}

package com.example.service;

import com.example.dto.AppointmentReqDto;
import com.example.dto.AppointmentResDto;
import com.example.exception.CustomException;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.model.User;
import com.example.repository.IAppointmentRepository;
import com.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        try{
            if (patient == null || doctor == null){
                throw new CustomException("Doctor or patient does is now exist");
            }

            if (!isTimeSlotAvailable(appointmentReqDto.getDoctorId(),appointmentReqDto.getDateTime())){
                masterResponse.setCode("501");
                masterResponse.setStatus("failed");
                masterResponse.setPayload("Mentioned time slot is not available!!\n Please select another time slot.");
            } else {
                Appointment appointment = new Appointment();
                appointment.setDateTime(appointmentReqDto.getDateTime());
                appointment.setStatus(appointmentReqDto.getStatus());
                appointment.setDoctor(doctor);
                appointment.setPatient(patient);

                masterResponse.setCode("200");
                masterResponse.setStatus("success");
                masterResponse.setPayload(appointmentRepository.save(appointment));
            }
        } catch (Exception exception){
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while saving the appointment.");
            return masterResponse;
        }
        return masterResponse;
    }

    private boolean isTimeSlotAvailable(int doctorId, String dateTime) {
        List<Appointment> appointmentList = appointmentRepository.findByDoctorIdAndDateTime(doctorId, dateTime);
        return appointmentList.isEmpty();
    }

    @Override
    public MasterResponse apptsByUserId(int id, String user) {
        MasterResponse masterResponse = new MasterResponse();

        List<Appointment> appointmentList = new ArrayList<>();
        List<AppointmentResDto> appointmentResDtoList = new ArrayList<>();


        if ("doctor".equalsIgnoreCase(user)){

            appointmentList = appointmentRepository.findByDoctorId(id);

            for (Appointment appointment : appointmentList) {
                AppointmentResDto respDto = mapAppointmentToDTO(appointment);
                appointmentResDtoList.add(respDto);
            }
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentResDtoList);
//            return masterResponse;
        } else {

            appointmentList = appointmentRepository.findByPatientId(id);

            for (Appointment appointment : appointmentList) {
                AppointmentResDto respDto = mapAppointmentToDTO(appointment);
                appointmentResDtoList.add(respDto);
            }

            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentResDtoList);
//            return masterResponse;
        }
        return masterResponse;
    }

    public AppointmentResDto mapAppointmentToDTO(Appointment appointment) {
        AppointmentResDto dto = new AppointmentResDto();
        dto.setId(appointment.getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setStatus(appointment.getStatus());
        dto.setPatientName(appointment.getPatient().getFirstName()+" "+ appointment.getPatient().getLastName());
        dto.setDoctorName(appointment.getDoctor().getFirstName()+" "+ appointment.getDoctor().getLastName());
        return dto;
    }
}

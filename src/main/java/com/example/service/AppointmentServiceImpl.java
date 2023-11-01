package com.example.service;

import com.example.dto.AppointmentReqDto;
import com.example.dto.AppointmentResDto;
import com.example.dto.MailRequest;
import com.example.exception.CustomException;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.model.User;
import com.example.repository.IAppointmentRepository;
import com.example.repository.ITimeSlotRepository;
import com.example.repository.IUserRepository;
import com.example.util.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private IAppointmentRepository appointmentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITimeSlotService timeSlotService;
    @Autowired
    private ITimeSlotRepository timeSlotRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public MasterResponse createAppointment(AppointmentReqDto appointmentReqDto) {

        MasterResponse masterResponse = new MasterResponse();

        User patient = userRepository.findUserById(appointmentReqDto.getPatientId());
        User doctor = userRepository.findUserById(appointmentReqDto.getDoctorId());

        try {
            if (patient == null || doctor == null) {
                throw new CustomException("Doctor or patient does is now exist");
            }

            Appointment appointment = new Appointment();
            appointment.setDate(appointmentReqDto.getDate());
            appointment.setTime(appointmentReqDto.getTime());
            appointment.setStatus(appointmentReqDto.getStatus());
            appointment.setDescription(appointmentReqDto.getDescription());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);

            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            Appointment raisedAppointment = appointmentRepository.save(appointment);
            masterResponse.setPayload(raisedAppointment);

            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(patient.getEmail());
            mailRequest.setCc(doctor.getEmail());
            mailRequest.setSubject("Appointment raised successfully!!");
            timeSlotService.createSlot(doctor, appointmentReqDto.getDate(),
                    appointmentReqDto.getTime(), raisedAppointment);
            Map<String, Object> details = getStringObjectMap(raisedAppointment, patient, doctor);
            mailRequest.setAppointmentDetails(details);
            emailService.sendHtmlMail(mailRequest);

        } catch (Exception exception) {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while saving the appointment.");
            return masterResponse;
        }
        return masterResponse;
    }

    private static Map<String, Object> getStringObjectMap(Appointment raisedAppointment, User patient, User doctor) {
        Map<String, Object> details = new HashMap<>();
        details.put("AppointmentID", raisedAppointment.getId());
        details.put("Patient", patient.getFirstName() + " " + patient.getLastName());
        details.put("Doctor", doctor.getFirstName() + " " + doctor.getLastName());
        details.put("Date", raisedAppointment.getDate());
        details.put("Time", raisedAppointment.getTime());
        details.put("Status", raisedAppointment.getStatus());
        details.put("Fees", doctor.getFees());
        return details;
    }


    @Override
    public MasterResponse apptsByUserId(int id, String user) {
        MasterResponse masterResponse = new MasterResponse();

        List<Appointment> appointmentList;
        List<AppointmentResDto> appointmentResDtoList = new ArrayList<>();


        if ("doctor".equalsIgnoreCase(user)) {

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

    @Override
    public MasterResponse getAll() {
        MasterResponse masterResponse = new MasterResponse();
        List<Appointment> appointmentList;
        List<AppointmentResDto> appointmentResDtoList = new ArrayList<>();

        appointmentList = appointmentRepository.findAll();

        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList) {
                AppointmentResDto respDto = mapAppointmentToDTO(appointment);
                appointmentResDtoList.add(respDto);
            }
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentResDtoList);
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while updating the appointment.");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse rescheduleByIdById(int id, AppointmentReqDto appointmentReqDto) {
        MasterResponse masterResponse = new MasterResponse();

        Appointment appointment = appointmentRepository.getById(id);
        if (appointment != null) {
            appointment.setDate(appointmentReqDto.getDate());
            appointment.setTime(appointmentReqDto.getTime());
            appointment.setStatus("rescheduled");
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentRepository.save(appointment));

            timeSlotService.updateSlot(id, appointment.getDate(), appointment.getTime());

            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(appointment.getPatient().getEmail());
            mailRequest.setCc(appointment.getDoctor().getEmail());
            mailRequest.setSubject("Appointment rescheduled successfully!!");
            Map<String, Object> details = getStringObjectMap(appointment, appointment.getPatient(),
                    appointment.getDoctor());
            mailRequest.setAppointmentDetails(details);
            emailService.sendHtmlMail(mailRequest);
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while updating the appointment.");
        }

        return masterResponse;
    }

    @Override
    public MasterResponse cancelById(int id, AppointmentReqDto appointmentReqDto) {
        MasterResponse masterResponse = new MasterResponse();

        Appointment appointment = appointmentRepository.getById(id);
        if (appointment != null) {

            timeSlotService.deleteSlot(appointment.getId());

            log.info("Appointment Id: {}", appointment.getId());

            appointment.setStatus(appointmentReqDto.getStatus());
            masterResponse.setCode("200");
            masterResponse.setStatus("success");

            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(appointment.getPatient().getEmail());
            mailRequest.setCc(appointment.getDoctor().getEmail());
            mailRequest.setSubject("Appointment got cancelled!!");
            Map<String, Object> details = getStringObjectMap(appointment, appointment.getPatient(),
                    appointment.getDoctor());
            mailRequest.setAppointmentDetails(details);


            emailService.sendHtmlMail(mailRequest);

            masterResponse.setPayload(appointmentRepository.save(appointment));

        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while cancelling the appointment.");
        }

        return masterResponse;
    }

    @Override
    public MasterResponse doneById(int id, AppointmentReqDto appointmentReqDto) {
        MasterResponse masterResponse = new MasterResponse();

        Appointment appointment = appointmentRepository.getById(id);
        if (appointment != null) {
            timeSlotService.deleteSlot(appointment.getId());
            appointment.setStatus(appointmentReqDto.getStatus());
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentRepository.save(appointment));
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while updating the appointment.");
        }
        return masterResponse;
    }


    @Override
    public MasterResponse getById(int id) {
        MasterResponse masterResponse = new MasterResponse();

        Appointment appointment = appointmentRepository.getById(id);
        if (appointment != null) {
            AppointmentResDto appointmentResDto = mapAppointmentToDTO(appointment);
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(appointmentResDto);
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("An error occurred while fetching the appointment.");
        }
        return masterResponse;
    }


    public AppointmentResDto mapAppointmentToDTO(Appointment appointment) {
        AppointmentResDto dto = new AppointmentResDto();
        dto.setId(appointment.getId());
        dto.setDate(appointment.getDate());
        dto.setTime(appointment.getTime());
        dto.setStatus(appointment.getStatus());
        dto.setDescription(appointment.getDescription());
        dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
        dto.setDoctorId(appointment.getDoctor().getId());
        return dto;
    }
}

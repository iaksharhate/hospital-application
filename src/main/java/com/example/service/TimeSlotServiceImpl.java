package com.example.service;

import com.example.dto.TimeSlotResDto;
import com.example.model.Appointment;
import com.example.model.MasterResponse;
import com.example.model.TimeSlot;
import com.example.model.User;
import com.example.repository.ITimeSlotRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeSlotServiceImpl implements ITimeSlotService{

    @Autowired
    private ITimeSlotRepository timeSlotRepository;
    @Autowired
    private Gson gson;
    @Override
    public void createSlot(User doctor, String date, String time, Appointment appointment) {

        TimeSlot timeSlot = new TimeSlot(date, time, doctor, appointment);
        timeSlotRepository.save(timeSlot);
    }

    @Override
    public MasterResponse getByDoctorId(int id) {

        MasterResponse masterResponse = new MasterResponse();
        List<TimeSlot> timeSlotList = timeSlotRepository.getByDoctorId(id);
        log.info("Time Slot list {}", gson.toJson(timeSlotList));
        if (!timeSlotList.isEmpty()){

            Map<String, List<String>> slotsMap = timeSlotList.stream()
                            .collect(Collectors.groupingBy(TimeSlot::getDate,
                                    Collectors.mapping(TimeSlot::getTime,Collectors.toList())));
            log.info("Time Slot map {}", gson.toJson(slotsMap));
            List<TimeSlotResDto> slotList = new ArrayList<>();
            slotsMap.forEach((date, time)->{
                TimeSlotResDto timeSlot = new TimeSlotResDto();
                timeSlot.date = date;
                timeSlot.time = time;
                slotList.add(timeSlot);
            });

            log.info("slotList {}", gson.toJson(slotList));
            masterResponse.setStatus("success");
            masterResponse.setCode("200");
            masterResponse.setPayload(slotList);
        } else {
            masterResponse.setStatus("failed");
            masterResponse.setCode("501");
            masterResponse.setPayload("Error while fetching time slot list!");
        }
        return masterResponse;
    }

    @Override
    public void updateSlot(int id, String date, String time) {
        timeSlotRepository.updateByAppointmentId(id, date, time);

    }

    @Override
    public void deleteSlot(int id) {
        timeSlotRepository.deleteByApptId(id);
    }
}

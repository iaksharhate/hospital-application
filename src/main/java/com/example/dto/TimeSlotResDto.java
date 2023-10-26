package com.example.dto;

import lombok.Data;

import javax.persistence.Converter;
import java.util.List;

@Data
public class TimeSlotResDto {

    public String date;
    public List<String> time;
}

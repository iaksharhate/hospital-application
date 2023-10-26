package com.example.repository;

import com.example.dto.TimeSlotResDto;
import com.example.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ITimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    @Query(value ="select * from time_slot t where t.doctor_id = :id", nativeQuery = true)
    List<TimeSlot> getByDoctorId(int id);

    @Modifying
    @Query(value = "update time_slot set a_date = :date, a_time = :time where a_id = :id", nativeQuery = true)
    @Transactional
    void updateByAppointmentId(@Param("id") int id, @Param("date") String date, @Param("time") String time);
}

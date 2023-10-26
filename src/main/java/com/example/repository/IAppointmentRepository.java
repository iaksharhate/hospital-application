package com.example.repository;

import com.example.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(value = "select * from appointment where doctor_id = :id", nativeQuery = true)
    List<Appointment> findByDoctorId(int id);

    @Query(value = "select * from appointment where patient_id = :id", nativeQuery = true)
    List<Appointment> findByPatientId(int id);

    @Query(value = "select * from appointment where doctor_id = :id and date_time = :dateTime", nativeQuery = true)
    List<Appointment> findByDoctorIdAndDateTime(int id, String dateTime);

    @Query(value = "select * from appointment where a_id = :id", nativeQuery = true)
    Appointment getById(int id);


}

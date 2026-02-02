package com.project.back_end.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

   // 1. Retrieve appointments for a doctor within a time range
   @Query("SELECT a FROM Appointment a " +
         "LEFT JOIN FETCH a.doctor d " +
         "WHERE a.doctor.id = :doctorId AND a.appointmentTime BETWEEN :start AND :end")
   List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

   // 2. Filter appointments by doctor ID, partial patient name, and time range
   @Query("SELECT a FROM Appointment a " +
         "LEFT JOIN FETCH a.patient p " +
         "LEFT JOIN FETCH a.doctor d " +
         "WHERE a.doctor.id = :doctorId " +
         "AND LOWER(a.patient.name) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
         "AND a.appointmentTime BETWEEN :start AND :end")
   List<Appointment> findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
         Long doctorId, String patientName, LocalDateTime start, LocalDateTime end);

   // 3. Delete all appointments for a specific doctor
   @Modifying
   @Transactional
   void deleteAllByDoctorId(Long doctorId);

   // 4. Find all appointments for a specific patient
   List<Appointment> findByPatientId(Long patientId);

   // 5. Find appointments by patient ID and status, ordered by appointment time
   List<Appointment> findByPatient_IdAndStatusOrderByAppointmentTimeAsc(Long patientId, int status);

   // 6. Filter by partial doctor name and patient ID
   @Query("SELECT a FROM Appointment a " +
         "LEFT JOIN FETCH a.doctor d " +
         "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :doctorName, '%')) " +
         "AND a.patient.id = :patientId")
   List<Appointment> filterByDoctorNameAndPatientId(String doctorName, Long patientId);

   // 7. Filter by doctor name, patient ID, and status
   @Query("SELECT a FROM Appointment a " +
         "LEFT JOIN FETCH a.doctor d " +
         "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :doctorName, '%')) " +
         "AND a.patient.id = :patientId " +
         "AND a.status = :status")
   List<Appointment> filterByDoctorNameAndPatientIdAndStatus(String doctorName, Long patientId, int status);

   // 8. Update appointment status by ID
   @Modifying
   @Transactional
   @Query("UPDATE Appointment a SET a.status = :status WHERE a.id = :id")
   void updateStatus(int status, long id);

   
}


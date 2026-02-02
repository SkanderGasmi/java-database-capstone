package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final TokenService tokenService;

    public AppointmentService(AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            TokenService tokenService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    // Book a new appointment
    @Transactional
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // Update an existing appointment
    @Transactional
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> existing = appointmentRepository.findById(appointment.getId());

        if (existing.isPresent()) {
            Appointment appt = existing.get();
            appt.setAppointmentTime(appointment.getAppointmentTime());
            appt.setStatus(appointment.getStatus());
            appointmentRepository.save(appt);
            response.put("message", "Appointment updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Appointment not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Cancel an appointment
    @Transactional
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> existing = appointmentRepository.findById(id);

        if (existing.isPresent()) {
            Appointment appt = existing.get();
            // Optional: Validate user using tokenService
            appointmentRepository.delete(appt);
            response.put("message", "Appointment canceled successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Appointment not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Get appointments for a doctor on a specific date, optionally filtered by
    // patient name
    @Transactional(readOnly = true)
    public Map<String, Object> getAppointments(String patientName, LocalDate date, String token) {
        Map<String, Object> response = new HashMap<>();
        // Extract doctorId from token
        Long doctorId = tokenService.extractDoctorId(token); // assume this method exists

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        List<Appointment> appointments;

        if (patientName == null || patientName.isEmpty()) {
            appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);
        } else {
            appointments = appointmentRepository
                    .findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
                            doctorId, patientName, start, end);
        }

        response.put("appointments", appointments);
        return response;
    }

    // Change the status of an appointment
    @Transactional
    public void changeStatus(int status, long id) {
        appointmentRepository.updateStatus(status, id);
    }
}

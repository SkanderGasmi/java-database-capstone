package com.project.back_end.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;

    public PatientService(PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            TokenService tokenService) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
    }

    // 1. Create a new patient
    public boolean createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Get appointments for a specific patient
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {
        Map<String, Object> response = new HashMap<>();
        String email = tokenService.extractIdentifier(token);
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null || !patient.getId().equals(id)) {
            response.put("error", "Unauthorized access");
            return ResponseEntity.status(401).body(response);
        }
        List<AppointmentDTO> appointments = appointmentRepository.findByPatientId(id)
                .stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
        response.put("appointments", appointments);
        return ResponseEntity.ok(response);
    }

    // 3. Filter appointments by condition (past/future)
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> allAppointments = appointmentRepository.findByPatientId(id);

        List<AppointmentDTO> filtered = allAppointments.stream()
                .filter(a -> {
                    if ("past".equalsIgnoreCase(condition))
                        return a.getAppointmentTime().isBefore(LocalDateTime.now());
                    else if ("future".equalsIgnoreCase(condition))
                        return a.getAppointmentTime().isAfter(LocalDateTime.now());
                    return false;
                })
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());

        response.put("appointments", filtered);
        return ResponseEntity.ok(response);
    }

    // 4. Filter appointments by doctor's name
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> filterByDoctor(String name, Long patientId) {
        Map<String, Object> response = new HashMap<>();
        List<AppointmentDTO> filtered = appointmentRepository
                .filterByDoctorNameAndPatientId(name, patientId)
                .stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
        response.put("appointments", filtered);
        return ResponseEntity.ok(response);
    }

    // 5. Filter appointments by doctor's name and condition
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(String condition, String name,
            long patientId) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentRepository
                .filterByDoctorNameAndPatientId(name, patientId);

        List<AppointmentDTO> filtered = appointments.stream()
                .filter(a -> {
                    if ("past".equalsIgnoreCase(condition))
                        return a.getAppointmentTime().isBefore(LocalDateTime.now());
                    else if ("future".equalsIgnoreCase(condition))
                        return a.getAppointmentTime().isAfter(LocalDateTime.now());
                    return false;
                })
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());

        response.put("appointments", filtered);
        return ResponseEntity.ok(response);
    }

    // 6. Get patient details by token
    @Transactional(readOnly = true)
    public Patient getPatientDetails(String token) {
        String email = tokenService.extractIdentifier(token);
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            return null;
        }
        return patient;
    }
}

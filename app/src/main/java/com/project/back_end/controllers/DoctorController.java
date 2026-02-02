package com.project.back_end.controllers;

import com.project.back_end.models.Doctor;
import com.project.back_end.DTO.Login;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.Service_;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final Service_ service;

    // Constructor injection
    public DoctorController(DoctorService doctorService, Service_ service) {
        this.doctorService = doctorService;
        this.service = service;
    }

    // Get doctor availability for a given date (role-based)
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(@PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token) {
        if (!service.validateToken(token, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }

        try {
            LocalDate localDate = LocalDate.parse(date); // convert string to LocalDate
            List<String> availability = doctorService.getDoctorAvailability(doctorId, localDate);
            return ResponseEntity.ok(availability);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid date format, expected yyyy-MM-dd"));
        }
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<?> getDoctors() {
        List<Doctor> doctors = doctorService.getDoctors();
        return ResponseEntity.ok(Map.of("doctors", doctors));
    }

    // Add a new doctor (admin only)
    @PostMapping("/{token}")
    public ResponseEntity<?> saveDoctor(@PathVariable String token, @RequestBody Doctor doctor) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }
        boolean saved = doctorService.saveDoctor(doctor);
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Doctor added to db"));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Doctor already exists"));
    }

    // Doctor login
    @PostMapping("/login")
    public ResponseEntity<?> doctorLogin(@RequestBody Login login) {
        return doctorService.validateDoctor(login);
    }

    // Update doctor details (admin only)
    @PutMapping("/{token}")
    public ResponseEntity<?> updateDoctor(@PathVariable String token, @RequestBody Doctor doctor) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }
        boolean updated = doctorService.updateDoctor(doctor);
        if (updated) {
            return ResponseEntity.ok(Map.of("message", "Doctor updated"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Doctor not found"));
    }

    // Delete doctor (admin only)
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id, @PathVariable String token) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }
        boolean deleted = doctorService.deleteDoctor(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Doctor not found with id"));
    }

    // Filter doctors by name, time, and specialty
    @GetMapping("/filter/{name}/{time}/{speciality}")
    public ResponseEntity<?> filter(@PathVariable String name,
            @PathVariable String time,
            @PathVariable String speciality) {
        Map<String, Object> filtered = service.filterDoctor(name, speciality, time);
        return ResponseEntity.ok(filtered);
    }
}

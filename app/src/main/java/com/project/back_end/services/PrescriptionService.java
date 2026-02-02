package com.project.back_end.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.back_end.models.Prescription;
import com.project.back_end.repo.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    // 1. Save a new prescription
    public boolean savePrescription(Prescription prescription) {
        Map<String, String> response = new HashMap<>();
        try {
            List<Prescription> existing = prescriptionRepository.findByAppointmentId(prescription.getAppointmentId());
            if (!existing.isEmpty()) {
                response.put("message", "Prescription already exists for this appointment");
                return false;
            }
            prescriptionRepository.save(prescription);
            response.put("message", "Prescription saved");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "An error occurred while saving the prescription");
            return false;
        }
    }

    // 2. Retrieve prescription by appointment ID
    public Prescription getPrescription(Long appointmentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Prescription> prescriptions = prescriptionRepository.findByAppointmentId(appointmentId);
            if (prescriptions.isEmpty()) {
                response.put("message", "No prescription found for this appointment");
                return null;
            }
            response.put("prescriptions", prescriptions);
            return prescriptions.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "An error occurred while fetching the prescription");
            return null;
        }
    }
}

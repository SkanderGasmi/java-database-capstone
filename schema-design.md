# Smart Clinic Management System – Database Design

## MySQL Database Design

The MySQL database handles structured and relational data such as patients, doctors, appointments, and admin users. This design ensures data integrity, enforces relationships, and allows for validated queries.

### Table: patients
| Column Name   | Data Type      | Constraints                        |
|---------------|---------------|-----------------------------------|
| patient_id    | INT           | PRIMARY KEY, AUTO_INCREMENT       |
| first_name    | VARCHAR(50)   | NOT NULL                          |
| last_name     | VARCHAR(50)   | NOT NULL                          |
| email         | VARCHAR(100)  | UNIQUE, NOT NULL                  |
| phone_number  | VARCHAR(15)   | NOT NULL                          |
| date_of_birth | DATE          |                                   |
| created_at    | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP         |

### Table: doctors
| Column Name    | Data Type      | Constraints                        |
|----------------|---------------|-----------------------------------|
| doctor_id      | INT           | PRIMARY KEY, AUTO_INCREMENT       |
| first_name     | VARCHAR(50)   | NOT NULL                          |
| last_name      | VARCHAR(50)   | NOT NULL                          |
| email          | VARCHAR(100)  | UNIQUE, NOT NULL                  |
| specialization | VARCHAR(100)  |                                   |
| phone_number   | VARCHAR(15)   |                                   |
| created_at     | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP         |

### Table: appointments
| Column Name       | Data Type                       | Constraints                        |
|------------------|--------------------------------|-----------------------------------|
| appointment_id    | INT                             | PRIMARY KEY, AUTO_INCREMENT       |
| patient_id        | INT                             | FOREIGN KEY REFERENCES patients(patient_id), NOT NULL |
| doctor_id         | INT                             | FOREIGN KEY REFERENCES doctors(doctor_id), NOT NULL |
| appointment_date  | DATETIME                        | NOT NULL                          |
| status            | ENUM('Scheduled','Completed','Cancelled') | DEFAULT 'Scheduled' |
| created_at        | TIMESTAMP                        | DEFAULT CURRENT_TIMESTAMP         |

### Table: admin
| Column Name    | Data Type      | Constraints                        |
|----------------|---------------|-----------------------------------|
| admin_id       | INT           | PRIMARY KEY, AUTO_INCREMENT       |
| username       | VARCHAR(50)   | UNIQUE, NOT NULL                  |
| password_hash  | VARCHAR(255)  | NOT NULL                          |
| email          | VARCHAR(100)  | UNIQUE, NOT NULL                  |
| created_at     | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP         |

> **Comments/Justification:**  
> - Patients, doctors, and appointments are core entities; relationships enforce data integrity.  
> - Appointment status tracks progress, and timestamps allow audit and reporting.  
> - Admin table stores credentials securely using hashed passwords.  

---

## MongoDB Collection Design

MongoDB stores flexible and unstructured data such as prescriptions, logs, or feedback. Collections allow nested objects, arrays, and optional fields.

### Collection: prescriptions
```json
{
  "_id": "64f0c5a2e2b5a3b1c8d0e9f7",
  "appointment_id": 101,
  "patient_id": 15,
  "doctor_id": 7,
  "date_issued": "2026-02-01T10:30:00Z",
  "medications": [
    {
      "name": "Amoxicillin",
      "dosage": "500mg",
      "frequency": "3 times a day",
      "duration": "7 days"
    },
    {
      "name": "Ibuprofen",
      "dosage": "200mg",
      "frequency": "as needed",
      "duration": "5 days"
    }
  ],
  "notes": "Take medications after meals and monitor for allergies",
  "created_at": "2026-02-01T10:35:00Z"
}
## Admin User Stories

### Title: Admin Login
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Admin can access the login page.
2. Valid credentials allow access to the admin dashboard.
3. Invalid credentials show an error message.

**Priority:** High  
**Story Points:** 3  

**Notes:**
- Credentials must be encrypted.

---

### Title: Admin Logout
_As an admin, I want to log out of the portal, so that system access is protected._

**Acceptance Criteria:**
1. Admin can click a logout button.
2. Session is terminated after logout.
3. Admin is redirected to the login page.

**Priority:** Medium  
**Story Points:** 2  

---

### Title: Add Doctor Profile
_As an admin, I want to add doctors to the portal, so that they can receive appointments._

**Acceptance Criteria:**
1. Admin can enter doctor details.
2. Doctor profile is saved successfully.
3. Doctor appears in the doctor list.

**Priority:** High  
**Story Points:** 5  

---

### Title: Delete Doctor Profile
_As an admin, I want to delete a doctor’s profile, so that inactive doctors are removed._

**Acceptance Criteria:**
1. Admin can select a doctor profile.
2. System asks for confirmation.
3. Doctor profile is removed from the portal.

**Priority:** Medium  
**Story Points:** 3  

---

### Title: View Monthly Appointment Statistics
_As an admin, I want to run a stored procedure in MySQL CLI, so that I can track the number of appointments per month._

**Acceptance Criteria:**
1. Stored procedure executes successfully.
2. Monthly appointment count is returned.
3. Data can be used for reporting.

**Priority:** Low  
**Story Points:** 5  

---

## Patient User Stories

### Title: View Doctors Without Login
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Doctor list is publicly accessible.
2. Specialization and availability are visible.
3. No booking actions are allowed without login.

**Priority:** Medium  
**Story Points:** 3  

---

### Title: Patient Sign Up
_As a patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**
1. Email must be unique.
2. Password meets security rules.
3. Account is created successfully.

**Priority:** High  
**Story Points:** 5  

---

### Title: Patient Login
_As a patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**
1. Valid credentials allow login.
2. Patient dashboard is displayed.
3. Invalid login shows an error.

**Priority:** High  
**Story Points:** 3  

---

### Title: Patient Logout
_As a patient, I want to log out of the portal, so that my account remains secure._

**Acceptance Criteria:**
1. Logout button is available.
2. Session is terminated.
3. User is redirected to home page.

**Priority:** Medium  
**Story Points:** 2  

---

### Title: Book Appointment
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**
1. Only available slots are shown.
2. Appointment duration is one hour.
3. Booking confirmation is displayed.

**Priority:** High  
**Story Points:** 5  

---

### Title: View Upcoming Appointments
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**
1. Upcoming appointments are listed.
2. Date, time, and doctor name are visible.
3. Cancel or reschedule options are available.

**Priority:** Medium  
**Story Points:** 3  

---

## Doctor User Stories

### Title: Doctor Login
_As a doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. Doctor can log in with credentials.
2. Dashboard shows appointment overview.
3. Invalid login shows error.

**Priority:** High  
**Story Points:** 3  

---

### Title: Doctor Logout
_As a doctor, I want to log out of the portal, so that my data is protected._

**Acceptance Criteria:**
1. Logout option is available.
2. Session is cleared.
3. Redirected to login page.

**Priority:** Medium  
**Story Points:** 2  

---

### Title: View Appointment Calendar
_As a doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**
1. Calendar shows daily and weekly view.
2. All confirmed appointments are visible.
3. Past appointments are read-only.

**Priority:** High  
**Story Points:** 5  

---

### Title: Mark Unavailability
_As a doctor, I want to mark my unavailability, so that patients can only book available slots._

**Acceptance Criteria:**
1. Doctor can block specific dates or times.
2. Blocked slots are hidden from patients.
3. Changes are saved successfully.

**Priority:** High  
**Story Points:** 5  

---

### Title: Update Doctor Profile
_As a doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date information._

**Acceptance Criteria:**
1. Doctor can edit profile fields.
2. Changes are saved correctly.
3. Updated information is visible to patients.

**Priority:** Medium  
**Story Points:** 3  

---

### Title: View Patient Details
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**
1. Patient name and reason for visit are visible.
2. Only authorized doctors can view details.
3. Data is read-only.

**Priority:** Medium  
**Story Points:** 3  

---


## Notes
- All user stories follow the standard agile template.
- Acceptance criteria use clear, testable conditions.
- Story points are indicative and can be refined during sprint planning.
- These stories align with the INVEST principles.
- Epics can be created later by grouping related stories (e.g., Authentication, Appointment Management).

---

// app/src/main/resources/static/js/doctorDashboard.js

// Import required modules
import { getAllAppointments } from "./services/appointmentRecordService.js";
import { createPatientRow } from "./components/patientRows.js";

// Global variables
const patientTableBody = document.getElementById("patientTableBody");
let selectedDate = new Date().toISOString().split("T")[0]; // YYYY-MM-DD
const token = localStorage.getItem("token");
let patientName = null;

// Search bar functionality
const searchBar = document.getElementById("searchBar");
searchBar.addEventListener("input", () => {
  const value = searchBar.value.trim();
  patientName = value !== "" ? value : "null";
  loadAppointments();
});

// "Today's Appointments" button
const todayButton = document.getElementById("todayButton");
todayButton.addEventListener("click", () => {
  selectedDate = new Date().toISOString().split("T")[0];
  document.getElementById("datePicker").value = selectedDate;
  loadAppointments();
});

// Date picker change event
const datePicker = document.getElementById("datePicker");
datePicker.value = selectedDate;
datePicker.addEventListener("change", () => {
  selectedDate = datePicker.value;
  loadAppointments();
});

// Function to load and render appointments
async function loadAppointments() {
  try {
    const appointments = await getAllAppointments(selectedDate, patientName, token);
    patientTableBody.innerHTML = "";

    if (!appointments || appointments.length === 0) {
      const row = document.createElement("tr");
      row.innerHTML = `<td colspan="4" class="text-center">No Appointments found for today.</td>`;
      patientTableBody.appendChild(row);
      return;
    }

    appointments.forEach(appointment => {
      const patient = {
        id: appointment.patientId,
        name: appointment.patientName,
        phone: appointment.patientPhone,
        email: appointment.patientEmail
      };
      const row = createPatientRow(patient, appointment);
      patientTableBody.appendChild(row);
    });

  } catch (error) {
    console.error("Error loading appointments:", error);
    patientTableBody.innerHTML = `<tr><td colspan="4" class="text-center">Error loading appointments. Try again later.</td></tr>`;
  }
}

// Initial render on page load
window.addEventListener("DOMContentLoaded", () => {
  // Optional: renderContent() if it exists to setup UI
  if (typeof renderContent === "function") renderContent();
  loadAppointments();
});

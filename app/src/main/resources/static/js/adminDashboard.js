// app/src/main/resources/static/js/adminDashboard.js

// Import required modules
import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors, saveDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "./components/doctorCard.js";

// Open "Add Doctor" modal on button click
document.getElementById('addDocBtn').addEventListener('click', () => {
  openModal('addDoctor');
});

// Load doctor cards when DOM is ready
window.addEventListener('DOMContentLoaded', loadDoctorCards);

// Function to load all doctor cards
async function loadDoctorCards() {
  try {
    const doctors = await getDoctors();
    renderDoctorCards(doctors);
  } catch (error) {
    console.error("Error loading doctors:", error);
    alert("Failed to load doctors. Please try again later.");
  }
}

// Function to render a list of doctor cards
function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  if (!doctors || doctors.length === 0) {
    contentDiv.innerHTML = "<p>No doctors found.</p>";
    return;
  }

  doctors.forEach(doctor => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

// Setup search and filter event listeners
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

// Filter doctors when search/filter inputs change
async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value || null;
  const time = document.getElementById("filterTime").value || null;
  const specialty = document.getElementById("filterSpecialty").value || null;

  try {
    const filteredDoctors = await filterDoctors(name, time, specialty);
    if (filteredDoctors && filteredDoctors.length > 0) {
      renderDoctorCards(filteredDoctors);
    } else {
      document.getElementById("content").innerHTML = "<p>No doctors found with the given filters.</p>";
    }
  } catch (error) {
    console.error("Error filtering doctors:", error);
    alert("Failed to filter doctors. Please try again.");
  }
}

// Handle adding a new doctor via the modal form
export async function adminAddDoctor() {
  try {
    // Collect form values
    const name = document.getElementById("doctorName").value;
    const email = document.getElementById("doctorEmail").value;
    const phone = document.getElementById("doctorPhone").value;
    const password = document.getElementById("doctorPassword").value;
    const specialty = document.getElementById("doctorSpecialty").value;

    // Collect availability checkboxes
    const availabilityElements = document.querySelectorAll("input[name='availability']:checked");
    const availability = Array.from(availabilityElements).map(el => el.value);

    // Get admin token
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Admin not authenticated.");
      return;
    }

    // Build doctor object
    const doctor = { name, email, phone, password, specialty, availability };

    // Save doctor via service
    const result = await saveDoctor(doctor, token);

    if (result.success) {
      alert("Doctor added successfully!");
      // Close modal (assumes modal library supports close function)
      document.getElementById("addDoctorModalClose")?.click();
      loadDoctorCards(); // Refresh list
    } else {
      alert("Failed to add doctor: " + result.message);
    }
  } catch (error) {
    console.error("Error adding doctor:", error);
    alert("An error occurred while adding the doctor.");
  }
}

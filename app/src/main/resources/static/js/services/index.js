// app/src/main/resources/static/js/services/index.js

// Import the openModal function for showing login popups
import { openModal } from '../components/modals.js';

// Import API base URL from configuration
import { API_BASE_URL } from '../config/config.js';

// Define endpoint constants
const ADMIN_API = API_BASE_URL + '/admin';
const DOCTOR_API = API_BASE_URL + '/doctor/login';

// Run after DOM is fully loaded
window.onload = function () {
  // Select Admin and Doctor login buttons
  const adminBtn = document.getElementById('adminLogin');
  const doctorBtn = document.getElementById('doctorLogin');

  if (adminBtn) {
    adminBtn.addEventListener('click', () => {
      openModal('adminLogin');
    });
  }

  if (doctorBtn) {
    doctorBtn.addEventListener('click', () => {
      openModal('doctorLogin');
    });
  }
};

// Admin login handler
window.adminLoginHandler = async function () {
  try {
    // Get input values
    const username = document.getElementById('adminUsername').value;
    const password = document.getElementById('adminPassword').value;

    const admin = { username, password };

    // Send POST request
    const response = await fetch(ADMIN_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(admin)
    });

    if (!response.ok) {
      alert('Invalid credentials!');
      return;
    }

    const data = await response.json();

    // Store token in localStorage
    localStorage.setItem('token', data.token);
    // Set role
    selectRole('admin');

  } catch (error) {
    console.error('Admin login error:', error);
    alert('An error occurred during login. Please try again.');
  }
};

// Doctor login handler
window.doctorLoginHandler = async function () {
  try {
    // Get input values
    const email = document.getElementById('doctorEmail').value;
    const password = document.getElementById('doctorPassword').value;

    const doctor = { email, password };

    // Send POST request
    const response = await fetch(DOCTOR_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(doctor)
    });

    if (!response.ok) {
      alert('Invalid credentials!');
      return;
    }

    const data = await response.json();

    // Store token in localStorage
    localStorage.setItem('token', data.token);
    // Set role
    selectRole('doctor');

  } catch (error) {
    console.error('Doctor login error:', error);
    alert('An error occurred during login. Please try again.');
  }
};

// Helper function to set role in localStorage and handle page rendering
function selectRole(role) {
  localStorage.setItem('userRole', role);

  // Redirect based on role
  switch (role) {
    case 'admin':
      window.location.href = '/pages/adminDashboard.html';
      break;
    case 'doctor':
      window.location.href = '/pages/doctorDashboard.html';
      break;
    default:
      window.location.href = '/';
  }
}

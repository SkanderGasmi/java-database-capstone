// app/src/main/resources/static/js/services/doctorServices.js

// Import the base API URL from the configuration
import { API_BASE_URL } from "../config/config.js";

// Doctor-related API endpoint
const DOCTOR_API = API_BASE_URL + '/doctor';

/**
 * Fetch the list of all doctors
 * @returns {Array} Array of doctor objects or empty array if error occurs
 */
export async function getDoctors() {
  try {
    const response = await fetch(DOCTOR_API);
    if (!response.ok) {
      console.error('Failed to fetch doctors');
      return [];
    }
    const data = await response.json();
    return data.doctors || [];
  } catch (error) {
    console.error('Error fetching doctors:', error);
    return [];
  }
}

/**
 * Delete a doctor by ID (Admin only)
 * @param {string} id - Doctor ID
 * @param {string} token - Admin authentication token
 * @returns {Object} { success: boolean, message: string }
 */
export async function deleteDoctor(id, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/delete/${id}?token=${token}`, {
      method: 'DELETE'
    });

    const data = await response.json();

    return {
      success: response.ok,
      message: data.message || (response.ok ? 'Doctor deleted successfully' : 'Failed to delete doctor')
    };
  } catch (error) {
    console.error('Error deleting doctor:', error);
    return { success: false, message: 'An error occurred while deleting the doctor' };
  }
}

/**
 * Save (add) a new doctor (Admin only)
 * @param {Object} doctor - Doctor object containing name, email, specialty, availability
 * @param {string} token - Admin authentication token
 * @returns {Object} { success: boolean, message: string }
 */
export async function saveDoctor(doctor, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/save?token=${token}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(doctor)
    });

    const data = await response.json();

    return {
      success: response.ok,
      message: data.message || (response.ok ? 'Doctor added successfully' : 'Failed to add doctor')
    };
  } catch (error) {
    console.error('Error saving doctor:', error);
    return { success: false, message: 'An error occurred while adding the doctor' };
  }
}

/**
 * Filter doctors by name, time, or specialty
 * @param {string} name - Doctor name filter
 * @param {string} time - Availability filter
 * @param {string} specialty - Specialty filter
 * @returns {Array} Array of matching doctor objects or empty array if none found
 */
export async function filterDoctors(name = '', time = '', specialty = '') {
  try {
    // Build query string
    const queryParams = new URLSearchParams({
      name: name || '',
      time: time || '',
      specialty: specialty || ''
    });

    const response = await fetch(`${DOCTOR_API}/filter?${queryParams.toString()}`);

    if (!response.ok) {
      console.error('Failed to filter doctors');
      return { doctors: [] };
    }

    const data = await response.json();
    return data.doctors || [];
  } catch (error) {
    console.error('Error filtering doctors:', error);
    alert('An error occurred while filtering doctors');
    return { doctors: [] };
  }
}

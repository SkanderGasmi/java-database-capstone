// app/src/main/resources/static/js/services/patientServices.js

// Import the base API URL
import { API_BASE_URL } from "../config/config.js";

// Base endpoint for all patient-related requests
const PATIENT_API = API_BASE_URL + '/patient';

/**
 * Register a new patient
 * @param {Object} data - Patient details (name, email, password, etc.)
 * @returns {Object} { success: boolean, message: string }
 */
export async function patientSignup(data) {
  try {
    const response = await fetch(`${PATIENT_API}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    const result = await response.json();

    if (!response.ok) {
      throw new Error(result.message);
    }

    return { success: response.ok, message: result.message };
  } catch (error) {
    console.error("Error :: patientSignup ::", error);
    return { success: false, message: error.message };
  }
}

/**
 * Login patient
 * @param {Object} data - Login credentials (email, password)
 * @returns {Response} Fetch response object
 */
export async function patientLogin(data) {
  console.log("patientLogin ::", data);
  return await fetch(`${PATIENT_API}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });
}

/**
 * Get patient details using token
 * @param {string} token - Patient authentication token
 * @returns {Object|null} Patient data object or null on failure
 */
export async function getPatientData(token) {
  try {
    const response = await fetch(`${PATIENT_API}/${token}`);
    const data = await response.json();

    if (response.ok) return data.patient;
    return null;
  } catch (error) {
    console.error("Error fetching patient details:", error);
    return null;
  }
}

/**
 * Get patient appointments
 * @param {string} id - Patient ID
 * @param {string} token - Authentication token
 * @param {string} user - "patient" or "doctor" to differentiate request
 * @returns {Array|null} Appointments array or null if failed
 */
export async function getPatientAppointments(id, token, user) {
  try {
    const response = await fetch(`${PATIENT_API}/${id}/${user}/${token}`);
    const data = await response.json();
    if (response.ok) {
      return data.appointments;
    }
    return null;
  } catch (error) {
    console.error("Error fetching patient appointments:", error);
    return null;
  }
}

/**
 * Filter appointments by condition and name
 * @param {string} condition - Status filter (e.g., "pending", "consulted")
 * @param {string} name - Patient or doctor name filter
 * @param {string} token - Authentication token
 * @returns {Object} { appointments: Array } or empty array on failure
 */
export async function filterAppointments(condition, name, token) {
  try {
    const response = await fetch(`${PATIENT_API}/filter/${condition}/${name}/${token}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      console.error("Failed to fetch appointments:", response.statusText);
      return { appointments: [] };
    }
  } catch (error) {
    console.error("Error filtering appointments:", error);
    alert("Something went wrong!");
    return { appointments: [] };
  }
}

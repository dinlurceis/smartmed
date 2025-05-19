import api from "./api";

export const DoctorService = {
  // Public operations
  getAllDoctors: (filters = {}) => {
    return api.get("/doctors", { params: filters });
  },

  getDoctorById: (id) => {
    return api.get(`/doctors/${id}`);
  },

  getAvailableSlots: (doctorId, date) => {
    return api.get(`/doctors/${doctorId}/slots`, { params: { date } });
  },

  bookAppointment: (doctorId, appointmentData) => {
    return api.post(`/appointments/book/${doctorId}`, appointmentData);
  },

  getMyAppointments: () => {
    return api.get("/appointments/my");
  },

  // Doctor portal operations
  getDoctorAppointments: () => {
    return api.get("/doctors/appointments");
  },

  updateAppointmentStatus: (appointmentId, status) => {
    return api.put(`/appointments/${appointmentId}/status`, { status });
  },

  createPrescription: (appointmentId, prescriptionData) => {
    return api.post(
      `/appointments/${appointmentId}/prescription`,
      prescriptionData
    );
  },

  getPrescription: (appointmentId) => {
    return api.get(`/appointments/${appointmentId}/prescription`);
  },

  updatePrescription: (prescriptionId, prescriptionData) => {
    return api.put(`/prescriptions/${prescriptionId}`, prescriptionData);
  },

  // Admin operations
  createDoctor: (doctorData) => {
    return api.post("/admin/doctors", doctorData);
  },

  updateDoctor: (id, doctorData) => {
    return api.put(`/admin/doctors/${id}`, doctorData);
  },

  deleteDoctor: (id) => {
    return api.delete(`/admin/doctors/${id}`);
  },

  updateDoctorStatus: (id, isAvailable) => {
    return api.put(`/admin/doctors/${id}/status`, { isAvailable });
  },

  getDoctorStats: (id) => {
    return api.get(`/admin/doctors/${id}/stats`);
  },
};

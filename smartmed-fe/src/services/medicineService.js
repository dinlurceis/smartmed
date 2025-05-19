import api from "./api";

export const MedicineService = {
  getAllMedicines: (page = 1, size = 10) => {
    return api.get("/medicine/list-all", { params: { page, size } });
  },

  getMedicineById: (id) => {
    return api.get(`/medicine/${id}`);
  },

  getMedicinesByCategory: (categoryId, page = 1, size = 10) => {
    return api.get(`/medicine/category/${categoryId}`, { params: { page, size } });
  },

  searchMedicines: (searchRequest) => {
    return api.get("/medicine/search", { data: searchRequest });
  },

  // Admin operations
  createMedicine: (medicineData, imageFile) => {
    const formData = new FormData();
    formData.append('file', imageFile);
    formData.append('request', new Blob([JSON.stringify(medicineData)], {
      type: 'application/json',
    }));
    
    return api.post("/medicine/upload", formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  updateMedicine: (id, medicineData, imageFile) => {
    const formData = new FormData();
    if (imageFile) {
      formData.append('file', imageFile);
    }
    formData.append('request', new Blob([JSON.stringify(medicineData)], {
      type: 'application/json',
    }));
    
    return api.put(`/medicine/update/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  deleteMedicine: (id) => {
    return api.delete(`/medicine/delete/${id}`);
  },
};

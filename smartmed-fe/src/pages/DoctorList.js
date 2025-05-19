import { useState, useEffect } from "react";
import { DoctorService } from "../services/doctorService";
import DoctorCard from "../components/DoctorCard";

export default function DoctorList() {
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [filters, setFilters] = useState({
    specialization: "",
    search: "",
    minFee: "",
    maxFee: "",
  });

  const [specializations, setSpecializations] = useState([
    "General Physician",
    "Cardiologist",
    "Dermatologist",
    "Pediatrician",
    "Neurologist",
    "Psychiatrist",
    "Orthopedist",
    "Dentist",
  ]);

  useEffect(() => {
    const fetchDoctors = async () => {
      try {
        const response = await DoctorService.getAllDoctors(filters);
        setDoctors(response.data);
      } catch (err) {
        setError("Failed to load doctors. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchDoctors();
  }, [filters]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[50vh]">
        <div className="text-xl text-gray-600">Loading...</div>
      </div>
    );
  }

  if (error) {
    return <div className="text-center text-red-600 p-4">{error}</div>;
  }

  return (
    <div className="space-y-6">
      {/* Filters */}
      <div className="bg-white p-4 rounded-lg shadow">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div>
            <label
              htmlFor="search"
              className="block text-sm font-medium text-gray-700"
            >
              Search
            </label>
            <input
              type="text"
              id="search"
              name="search"
              value={filters.search}
              onChange={handleFilterChange}
              placeholder="Search doctors..."
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>

          <div>
            <label
              htmlFor="specialization"
              className="block text-sm font-medium text-gray-700"
            >
              Specialization
            </label>
            <select
              id="specialization"
              name="specialization"
              value={filters.specialization}
              onChange={handleFilterChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            >
              <option value="">All Specializations</option>
              {specializations.map((spec) => (
                <option key={spec} value={spec}>
                  {spec}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label
              htmlFor="minFee"
              className="block text-sm font-medium text-gray-700"
            >
              Min Fee
            </label>
            <input
              type="number"
              id="minFee"
              name="minFee"
              value={filters.minFee}
              onChange={handleFilterChange}
              placeholder="Min fee"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>

          <div>
            <label
              htmlFor="maxFee"
              className="block text-sm font-medium text-gray-700"
            >
              Max Fee
            </label>
            <input
              type="number"
              id="maxFee"
              name="maxFee"
              value={filters.maxFee}
              onChange={handleFilterChange}
              placeholder="Max fee"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>
        </div>
      </div>

      {/* Doctor Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {doctors.map((doctor) => (
          <DoctorCard key={doctor.id} doctor={doctor} />
        ))}
      </div>

      {doctors.length === 0 && (
        <div className="text-center text-gray-500 py-10">
          No doctors found matching your criteria.
        </div>
      )}
    </div>
  );
}

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { MedicineService } from '../services/medicineService';
import MedicineCard from '../components/MedicineCard';

export default function Home() {
  const [featuredMedicines, setFeaturedMedicines] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFeaturedMedicines = async () => {
      try {
        const response = await MedicineService.getAllMedicines({ featured: true, limit: 4 });
        setFeaturedMedicines(response.data);
      } catch (error) {
        console.error('Failed to load featured medicines:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFeaturedMedicines();
  }, []);

  return (
    <div className="space-y-16">
      {/* Hero Section */}
      <section className="relative bg-gradient-to-r from-primary to-primary-dark">
        <div className="absolute inset-0">
          <img
            src="/hero-bg.jpg"
            alt="Medical background"
            className="w-full h-full object-cover opacity-10"
          />
        </div>
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24">
          <div className="text-center">
            <h1 className="text-4xl tracking-tight font-extrabold text-white sm:text-5xl md:text-6xl">
              <span className="block">Your Health, Our Priority</span>
              <span className="block text-secondary">Online Medicine & Healthcare</span>
            </h1>
            <p className="mt-3 max-w-md mx-auto text-lg text-gray-200 sm:text-xl md:mt-5 md:max-w-3xl">
              Get your medicines delivered at your doorstep and book appointments with top doctors online.
            </p>
            <div className="mt-10 flex justify-center gap-4">
              <Link
                to="/medicines"
                className="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-secondary hover:bg-blue-600"
              >
                Browse Medicines
              </Link>
              <Link
                to="/doctors"
                className="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-primary bg-white hover:bg-gray-50"
              >
                Find Doctors
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Featured Medicines */}
      <section>
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h2 className="text-3xl font-extrabold text-gray-900 sm:text-4xl">
              Featured Medicines
            </h2>
            <p className="mt-3 max-w-2xl mx-auto text-xl text-gray-500 sm:mt-4">
              Check out our top-selling and featured medicines
            </p>
          </div>

          {loading ? (
            <div className="mt-12 flex justify-center">
              <div className="text-xl text-gray-600">Loading...</div>
            </div>
          ) : (
            <div className="mt-12 grid grid-cols-1 gap-y-10 gap-x-6 sm:grid-cols-2 lg:grid-cols-4">
              {featuredMedicines.map((medicine) => (
                <MedicineCard key={medicine.id} medicine={medicine} />
              ))}
            </div>
          )}

          <div className="mt-12 text-center">
            <Link
              to="/medicines"
              className="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-primary hover:bg-primary-dark"
            >
              View All Medicines
            </Link>
          </div>
        </div>
      </section>

      {/* Services Section */}
      <section className="bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
          <div className="text-center">
            <h2 className="text-3xl font-extrabold text-gray-900 sm:text-4xl">
              Our Services
            </h2>
            <p className="mt-3 max-w-2xl mx-auto text-xl text-gray-500 sm:mt-4">
              Comprehensive healthcare solutions at your fingertips
            </p>
          </div>

          <div className="mt-12 grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
            {/* Online Medicine */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="p-6">
                <div className="text-center">
                  <svg
                    className="mx-auto h-12 w-12 text-primary"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                    />
                  </svg>
                  <h3 className="mt-4 text-lg font-medium text-gray-900">Online Medicine</h3>
                  <p className="mt-2 text-gray-500">
                    Order medicines online and get them delivered to your doorstep
                  </p>
                </div>
              </div>
            </div>

            {/* Doctor Consultation */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="p-6">
                <div className="text-center">
                  <svg
                    className="mx-auto h-12 w-12 text-primary"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                    />
                  </svg>
                  <h3 className="mt-4 text-lg font-medium text-gray-900">Doctor Consultation</h3>
                  <p className="mt-2 text-gray-500">
                    Book appointments with experienced doctors online
                  </p>
                </div>
              </div>
            </div>

            {/* Health Records */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="p-6">
                <div className="text-center">
                  <svg
                    className="mx-auto h-12 w-12 text-primary"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                    />
                  </svg>
                  <h3 className="mt-4 text-lg font-medium text-gray-900">Health Records</h3>
                  <p className="mt-2 text-gray-500">
                    Keep track of your prescriptions and medical history
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

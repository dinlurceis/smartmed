import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { DoctorService } from '../../../services/doctorService';

const SPECIALIZATIONS = [
  'General Physician',
  'Cardiologist',
  'Dermatologist',
  'Pediatrician',
  'Neurologist',
  'Psychiatrist',
  'Orthopedist',
  'Dentist',
];

export default function DoctorForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    specialization: '',
    education: '',
    experience: '',
    consultationFee: '',
    imageUrl: '',
    expertise: [],
    availableSlots: [],
  });

  useEffect(() => {
    const fetchDoctor = async () => {
      if (!id) return;

      try {
        const response = await DoctorService.getDoctorById(id);
        setFormData(response.data);
      } catch (err) {
        setError('Failed to load doctor data. Please try again later.');
      }
    };

    fetchDoctor();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleExpertiseChange = (e) => {
    const value = e.target.value;
    setFormData((prev) => ({
      ...prev,
      expertise: prev.expertise.includes(value)
        ? prev.expertise.filter((item) => item !== value)
        : [...prev.expertise, value],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (id) {
        await DoctorService.updateDoctor(id, formData);
      } else {
        await DoctorService.createDoctor(formData);
      }
      navigate('/admin/doctors');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save doctor.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">
        {id ? 'Edit Doctor' : 'Add New Doctor'}
      </h1>

      {error && (
        <div className="bg-red-50 p-4 rounded-md mb-6">
          <div className="text-red-700">{error}</div>
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label
              htmlFor="name"
              className="block text-sm font-medium text-gray-700"
            >
              Full Name *
            </label>
            <input
              type="text"
              id="name"
              name="name"
              required
              value={formData.name}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>

          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700"
            >
              Email *
            </label>
            <input
              type="email"
              id="email"
              name="email"
              required
              value={formData.email}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label
              htmlFor="phone"
              className="block text-sm font-medium text-gray-700"
            >
              Phone Number *
            </label>
            <input
              type="tel"
              id="phone"
              name="phone"
              required
              value={formData.phone}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>

          <div>
            <label
              htmlFor="specialization"
              className="block text-sm font-medium text-gray-700"
            >
              Specialization *
            </label>
            <select
              id="specialization"
              name="specialization"
              required
              value={formData.specialization}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            >
              <option value="">Select a specialization</option>
              {SPECIALIZATIONS.map((spec) => (
                <option key={spec} value={spec}>
                  {spec}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label
              htmlFor="experience"
              className="block text-sm font-medium text-gray-700"
            >
              Experience (years) *
            </label>
            <input
              type="number"
              id="experience"
              name="experience"
              required
              min="0"
              max="50"
              value={formData.experience}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>

          <div>
            <label
              htmlFor="consultationFee"
              className="block text-sm font-medium text-gray-700"
            >
              Consultation Fee *
            </label>
            <div className="mt-1 relative rounded-md shadow-sm">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <span className="text-gray-500 sm:text-sm">$</span>
              </div>
              <input
                type="number"
                id="consultationFee"
                name="consultationFee"
                required
                min="0"
                step="0.01"
                value={formData.consultationFee}
                onChange={handleChange}
                className="pl-7 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
              />
            </div>
          </div>
        </div>

        <div>
          <label
            htmlFor="education"
            className="block text-sm font-medium text-gray-700"
          >
            Education *
          </label>
          <textarea
            id="education"
            name="education"
            required
            rows={3}
            value={formData.education}
            onChange={handleChange}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            placeholder="Enter educational qualifications..."
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Areas of Expertise
          </label>
          <div className="mt-2 space-y-2">
            {SPECIALIZATIONS.map((expertise) => (
              <div key={expertise} className="flex items-center">
                <input
                  type="checkbox"
                  id={`expertise-${expertise}`}
                  value={expertise}
                  checked={formData.expertise.includes(expertise)}
                  onChange={handleExpertiseChange}
                  className="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded"
                />
                <label
                  htmlFor={`expertise-${expertise}`}
                  className="ml-2 block text-sm text-gray-700"
                >
                  {expertise}
                </label>
              </div>
            ))}
          </div>
        </div>

        <div>
          <label
            htmlFor="imageUrl"
            className="block text-sm font-medium text-gray-700"
          >
            Profile Image URL
          </label>
          <input
            type="url"
            id="imageUrl"
            name="imageUrl"
            value={formData.imageUrl}
            onChange={handleChange}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
          />
        </div>

        <div className="flex justify-end space-x-4">
          <button
            type="button"
            onClick={() => navigate('/admin/doctors')}
            className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={loading}
            className="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-primary hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50"
          >
            {loading ? 'Saving...' : id ? 'Update Doctor' : 'Create Doctor'}
          </button>
        </div>
      </form>
    </div>
  );
}

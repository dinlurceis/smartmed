import { Link } from "react-router-dom";

export default function DoctorCard({ doctor }) {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <img
        src={doctor.imageUrl || "/placeholder-doctor.jpg"}
        alt={doctor.name}
        className="w-full h-48 object-cover object-center"
      />
      <div className="p-4">
        <h3 className="text-xl font-semibold text-gray-900">{doctor.name}</h3>
        <p className="text-sm text-gray-500">{doctor.specialization}</p>

        <div className="mt-2">
          <p className="text-sm text-gray-600">{doctor.education}</p>
          <p className="text-sm text-gray-600">
            Experience: {doctor.experience} years
          </p>
        </div>

        <div className="mt-4 space-y-2">
          {doctor.expertise && (
            <div className="flex flex-wrap gap-2">
              {doctor.expertise.map((skill, index) => (
                <span
                  key={index}
                  className="px-2 py-1 text-xs font-medium bg-blue-100 text-blue-800 rounded-full"
                >
                  {skill}
                </span>
              ))}
            </div>
          )}
        </div>

        <div className="mt-4 flex items-center justify-between">
          <div>
            <span className="text-sm text-gray-500">Consultation Fee</span>
            <p className="text-lg font-semibold text-primary">
              ${doctor.consultationFee}
            </p>
          </div>
          <Link
            to={`/doctors/${doctor.id}`}
            className="px-4 py-2 bg-primary text-white rounded-md hover:bg-primary-dark transition-colors"
          >
            Book Appointment
          </Link>
        </div>
      </div>
    </div>
  );
}

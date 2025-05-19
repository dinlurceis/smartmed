import { Link } from "react-router-dom";

export default function MedicineCard({ medicine }) {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <img
        src={medicine.imageUrl || "/placeholder-medicine.jpg"}
        alt={medicine.name}
        className="w-full h-48 object-cover"
      />
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-900">{medicine.name}</h3>
        <p className="text-sm text-gray-500">{medicine.category}</p>
        <p className="mt-2 text-gray-600 line-clamp-2">
          {medicine.description}
        </p>
        <div className="mt-4 flex items-center justify-between">
          <span className="text-lg font-bold text-primary">
            ${medicine.price}
          </span>
          <Link
            to={`/medicines/${medicine.id}`}
            className="px-4 py-2 bg-primary text-white rounded-md hover:bg-primary-dark transition-colors"
          >
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
}

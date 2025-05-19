import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { MedicineService } from "../services/medicineService";
import { useCart } from "../contexts/CartContext";

export default function MedicineDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { addToCart } = useCart();
  const [medicine, setMedicine] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [addingToCart, setAddingToCart] = useState(false);

  useEffect(() => {
    const fetchMedicine = async () => {
      try {
        const response = await MedicineService.getMedicineById(id);
        setMedicine(response.data);
      } catch (err) {
        setError("Failed to load medicine details. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchMedicine();
  }, [id]);

  const handleAddToCart = async () => {
    setAddingToCart(true);
    const result = await addToCart(id, quantity);
    if (result.success) {
      navigate("/cart");
    } else {
      setError(result.error);
    }
    setAddingToCart(false);
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

  if (!medicine) {
    return (
      <div className="text-center text-gray-600 p-4">Medicine not found</div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="bg-white rounded-lg shadow-lg overflow-hidden">
        <div className="md:flex">
          <div className="md:flex-shrink-0 md:w-1/2">
            <img
              className="h-96 w-full object-cover md:h-full"
              src={medicine.imageUrl || "/placeholder-medicine.jpg"}
              alt={medicine.name}
            />
          </div>
          <div className="p-8 md:w-1/2">
            <div className="flex justify-between items-start">
              <div>
                <h2 className="text-2xl font-bold text-gray-900">
                  {medicine.name}
                </h2>
                <p className="mt-1 text-sm text-gray-500">
                  {medicine.category}
                </p>
              </div>
              <p className="text-3xl font-bold text-primary">
                ${medicine.price}
              </p>
            </div>

            <div className="mt-6">
              <h3 className="text-lg font-medium text-gray-900">Description</h3>
              <p className="mt-2 text-gray-600">{medicine.description}</p>
            </div>

            {medicine.usage && (
              <div className="mt-6">
                <h3 className="text-lg font-medium text-gray-900">Usage</h3>
                <p className="mt-2 text-gray-600">{medicine.usage}</p>
              </div>
            )}

            {medicine.sideEffects && (
              <div className="mt-6">
                <h3 className="text-lg font-medium text-gray-900">
                  Side Effects
                </h3>
                <p className="mt-2 text-gray-600">{medicine.sideEffects}</p>
              </div>
            )}

            <div className="mt-8">
              <div className="flex items-center space-x-4">
                <div className="flex-1">
                  <label
                    htmlFor="quantity"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Quantity
                  </label>
                  <select
                    id="quantity"
                    value={quantity}
                    onChange={(e) => setQuantity(Number(e.target.value))}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
                  >
                    {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((num) => (
                      <option key={num} value={num}>
                        {num}
                      </option>
                    ))}
                  </select>
                </div>

                <button
                  onClick={handleAddToCart}
                  disabled={addingToCart}
                  className="flex-1 bg-primary py-3 px-8 rounded-md text-white font-medium hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50"
                >
                  {addingToCart ? "Adding..." : "Add to Cart"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

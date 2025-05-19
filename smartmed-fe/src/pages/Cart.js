import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useCart } from "../contexts/CartContext";
import { useAuth } from "../contexts/AuthContext";

export default function Cart() {
  const {
    cart,
    loading,
    removeFromCart,
    updateCartItem,
    clearCart,
    totalItems,
    totalAmount,
  } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [updating, setUpdating] = useState(false);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[50vh]">
        <div className="text-xl text-gray-600">Loading...</div>
      </div>
    );
  }

  if (cart.length === 0) {
    return (
      <div className="text-center py-12">
        <h2 className="text-2xl font-bold text-gray-900 mb-4">
          Your cart is empty
        </h2>
        <p className="text-gray-600 mb-8">
          Add some medicines to your cart and they will appear here.
        </p>
        <Link
          to="/medicines"
          className="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-primary hover:bg-primary-dark"
        >
          Browse Medicines
        </Link>
      </div>
    );
  }

  const handleUpdateQuantity = async (medicineId, newQuantity) => {
    setUpdating(true);
    await updateCartItem(medicineId, newQuantity);
    setUpdating(false);
  };

  const handleRemoveItem = async (medicineId) => {
    setUpdating(true);
    await removeFromCart(medicineId);
    setUpdating(false);
  };

  const handleCheckout = () => {
    if (!user) {
      navigate("/login?redirect=/checkout");
      return;
    }
    navigate("/checkout");
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">Shopping Cart</h1>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="divide-y divide-gray-200">
          {cart.map((item) => (
            <div key={item.id} className="p-6 flex items-center">
              <img
                src={item.imageUrl || "/placeholder-medicine.jpg"}
                alt={item.name}
                className="h-20 w-20 object-cover rounded-md"
              />

              <div className="ml-6 flex-1">
                <div className="flex items-center justify-between">
                  <div>
                    <h3 className="text-lg font-medium text-gray-900">
                      <Link
                        to={"/medicines/${item.id}"}
                        className="hover:text-primary"
                      >
                        {item.name}
                      </Link>
                    </h3>
                    <p className="mt-1 text-sm text-gray-500">
                      {item.category}
                    </p>
                  </div>
                  <p className="text-lg font-medium text-gray-900">
                    ${item.price}
                  </p>
                </div>

                <div className="mt-4 flex items-center justify-between">
                  <div className="flex items-center">
                    <label htmlFor={"quantity-" + item.id} className="sr-only">
                      Quantity
                    </label>
                    <select
                      id={"quantity-" + item.id}
                      value={item.quantity}
                      onChange={(e) =>
                        handleUpdateQuantity(item.id, Number(e.target.value))
                      }
                      disabled={updating}
                      className="rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
                    >
                      {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((num) => (
                        <option key={num} value={num}>
                          {num}
                        </option>
                      ))}
                    </select>

                    <button
                      onClick={() => handleRemoveItem(item.id)}
                      disabled={updating}
                      className="ml-4 text-sm font-medium text-red-600 hover:text-red-500 disabled:opacity-50"
                    >
                      Remove
                    </button>
                  </div>
                  <p className="text-lg font-medium text-gray-900">
                    ${(item.price * item.quantity).toFixed(2)}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="bg-gray-50 p-6">
          <div className="flex justify-between text-base font-medium text-gray-900">
            <p>Subtotal ({totalItems} items)</p>
            <p>${totalAmount.toFixed(2)}</p>
          </div>
          <p className="mt-0.5 text-sm text-gray-500">
            Shipping and taxes calculated at checkout.
          </p>
          <div className="mt-6">
            <button
              onClick={handleCheckout}
              className="w-full bg-primary py-3 px-4 rounded-md shadow-sm text-white font-medium hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary"
            >
              Proceed to Checkout
            </button>
          </div>
          <div className="mt-4 text-center">
            <button
              onClick={clearCart}
              className="text-sm text-gray-500 hover:text-gray-700"
            >
              Clear Cart
            </button>
          </div>
          <div className="mt-6 flex justify-center text-center text-sm text-gray-500">
            <p>
              or{" "}
              <Link
                to="/medicines"
                className="font-medium text-primary hover:text-primary-dark"
              >
                Continue Shopping
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

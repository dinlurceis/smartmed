import { createContext, useContext, useState, useEffect } from "react";
import { MedicineService } from "../services/medicineService";

const CartContext = createContext(null);

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = async () => {
    try {
      const response = await MedicineService.getCart();
      setCart(response.data);
    } catch (error) {
      console.error("Failed to load cart:", error);
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (medicineId, quantity) => {
    try {
      await MedicineService.addToCart(medicineId, quantity);
      await loadCart();
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || "Failed to add item to cart",
      };
    }
  };

  const removeFromCart = async (medicineId) => {
    try {
      await MedicineService.removeFromCart(medicineId);
      await loadCart();
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error:
          error.response?.data?.message || "Failed to remove item from cart",
      };
    }
  };

  const updateCartItem = async (medicineId, quantity) => {
    try {
      await MedicineService.updateCartItem(medicineId, quantity);
      await loadCart();
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || "Failed to update cart item",
      };
    }
  };

  const clearCart = async () => {
    try {
      await MedicineService.clearCart();
      setCart([]);
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || "Failed to clear cart",
      };
    }
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        loading,
        addToCart,
        removeFromCart,
        updateCartItem,
        clearCart,
        totalItems: cart.reduce((sum, item) => sum + item.quantity, 0),
        totalAmount: cart.reduce(
          (sum, item) => sum + item.price * item.quantity,
          0
        ),
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart must be used within a CartProvider");
  }
  return context;
};

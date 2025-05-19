import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import { AuthProvider } from "./contexts/AuthContext";
import { CartProvider } from "./contexts/CartContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import MedicineList from "./pages/MedicineList";
import MedicineDetail from "./pages/MedicineDetail";
import Cart from "./pages/Cart";
import DoctorList from "./pages/DoctorList";
import AdminLayout from "./layouts/AdminLayout";
import AdminDashboard from "./pages/admin/Dashboard";
import MedicineManagement from "./pages/admin/medicines/MedicineManagement";
import MedicineForm from "./pages/admin/medicines/MedicineForm";
import DoctorManagement from "./pages/admin/doctors/DoctorManagement";
import DoctorForm from "./pages/admin/doctors/DoctorForm";

function App() {
  return (
    <Router>
      <AuthProvider>
        <CartProvider>
          <Routes>
            {/* Admin Routes */}
            <Route path="/admin" element={<AdminLayout />}>
              <Route index element={<AdminDashboard />} />
              <Route path="medicines" element={<MedicineManagement />} />
              <Route path="medicines/new" element={<MedicineForm />} />
              <Route path="medicines/:id/edit" element={<MedicineForm />} />
              <Route path="doctors" element={<DoctorManagement />} />
              <Route path="doctors/new" element={<DoctorForm />} />
              <Route path="doctors/:id/edit" element={<DoctorForm />} />
            </Route>

            {/* Main Routes */}
            <Route element={<MainLayout />}>
              <Route path="/" element={<Home />} />
              <Route path="/medicines" element={<MedicineList />} />
              <Route path="/medicines/:id" element={<MedicineDetail />} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/doctors" element={<DoctorList />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Route>
          </Routes>
        </CartProvider>
      </AuthProvider>
    </Router>
  );
}

export default App;

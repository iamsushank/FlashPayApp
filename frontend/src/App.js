import { Route, Routes } from "react-router-dom";
import Nav from "./component/Nav/Nav";
import Dashboard from "./pages/Dashboard";
import ErrorPage from "./pages/ErrorPage";
import Login from "./pages/Login";
import PaymentHistory from "./pages/PaymentHistory";
import Profile from "./pages/Profile";
import Signup from "./pages/Signup";
import PrivateRoute from "./privateRoute/PrivateRoute";

function App() {
  return (
    <>

      <Nav />

      <Routes>

        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/profile" element={
          <PrivateRoute>
            <Profile />
          </PrivateRoute>
        } />
        <Route path="/dashboard" element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        } />
        <Route path="/payment" element={
          <PrivateRoute>
            <PaymentHistory />
          </PrivateRoute>
        } />
        <Route path="*" element={<ErrorPage />} />

      </Routes>

    </>
  );
}

export default App;

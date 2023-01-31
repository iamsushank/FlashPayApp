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
        <Route
          path="/profile"
          element={
            <Profile />
            // <PrivateRoute>
            // </PrivateRoute>
          }
        />
        <Route
          path="/dashboard"
          element={
            <Dashboard />
            //   <PrivateRoute>
            //   </PrivateRoute>
          }
        />
        <Route
          path="/payment"
          element={
            <PaymentHistory />
            //   <PrivateRoute>
            //   </PrivateRoute>
          }
        />
        <Route path="*" element={<ErrorPage />} />
      </Routes>
    </>
  );
}

export default App;

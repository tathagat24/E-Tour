import { Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import { Slide, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { LOGGEDIN, ROLE } from "./constants/cache.key";
import { useDispatch } from "react-redux";
import { setLoggedInUser } from "./store/features/user/loggedInUseSlice";
import HomePage from "./pages/HomePage";
import NavBar from "./components/NavBar";
import NotFound from "./components/NotFound";
import Password from "./components/profile/Password";
import Profile from "./components/profile/Profile";
import User from "./components/profile/User";
import Users from "./components/users/Users";
import RestrictedRoute from "./components/RestrictedRoute";
import ProtectedRoute from "./components/ProtectedRoute";
import VerifyAccount from "./components/VerifyAccount";
import VerifyPassword from "./components/VerifyPassword";
import ResetPassword from "./components/ResetPassword";
import Register from "./components/Register";
import Login from "./components/Login";
import ContactUs from "./pages/ContactUs";
import MyBookings from "./pages/MyBookings";
import Footer from "./components/Footer";
import TourSubCategories from "./pages/TourSubCategories";
import Tours from "./pages/Tours";
import TourDetails from "./pages/TourDetails";
import TourBooking from "./pages/TourBooking";
import BookingDetails from "./pages/BookingDetails";
import ToursPage from "./pages/ToursPage";
import SuccessPage from "./pages/SuccessPage";
import CancelPage from "./pages/CancelPage";

function App() {
  const isLoggedIn = JSON.parse(localStorage.getItem(LOGGEDIN)) || false;
  const dispatch = useDispatch();

  if (isLoggedIn) {
    dispatch(
      setLoggedInUser({ userRole: JSON.parse(localStorage.getItem(ROLE)) })
    );
  }

  return (
    <>
      <NavBar />
      <Routes>
        <Route index path="/" element={<Navigate to="/home" />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route index path="/home" element={<HomePage />} />
        <Route path={"login"} element={<Login />} />
        <Route path={"register"} element={<Register />} />
        <Route path={"resetpassword"} element={<ResetPassword />} />
        <Route path={"verify/password"} element={<VerifyPassword />} />
        <Route path={"verify/account"} element={<VerifyAccount />} />

        <Route
          path={"/sub-tour-categories/:tourCategoryId"}
          element={<TourSubCategories />}
        />

        <Route
          path={"/tours"}
          element={<ToursPage />}
        />

        <Route
          path={"/sub-tour-categories/tours/:tourSubCategoryId"}
          element={<Tours />}
        />

        <Route path={"/tours/tour-details/:tourId"} element={<TourDetails />} />

        <Route element={<ProtectedRoute />}>
          <Route element={<RestrictedRoute />}>
            <Route path={"users"} element={<Users />} />
          </Route>

          <Route path={"/tours/booking/:tourId"} element={<TourBooking />} />
          <Route path={"/booking/booking-details/:bookingId"} element={<BookingDetails />} />

          <Route path={"/payment/success/:bookingReferenceId"} element={<SuccessPage />} />
          <Route path={"/payment/cancel/:bookingReferenceId"} element={<CancelPage />} />

          <Route path={"/user"} element={<User />}>
            <Route path={"/user"} element={<Navigate to={"/user/profile"} />} />
            <Route path={"profile"} element={<Profile />} />
            <Route path={"password"} element={<Password />} />
            <Route path={"mybookings"} element={<MyBookings />} />
          </Route>
        </Route>
        <Route path={"*"} element={<NotFound />} />
      </Routes>
      <Footer />
      <ToastContainer transition={Slide} />
    </>
  );
}

export default App;
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { LOGGEDIN } from "../constants/cache.key";

const ProtectedRoute = () => {

  const location = useLocation();
  const isLoggedIn = JSON.parse(localStorage.getItem(LOGGEDIN)) || false;

  if (isLoggedIn) {
    return <Outlet />;
  } else {
    return <Navigate to={"/login"} state={{ from: location }} replace />;
  }
};

export default ProtectedRoute;

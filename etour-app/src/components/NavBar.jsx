import { NavLink } from "react-router-dom";
import LoggedInUserDropdown from "./users/LoggedInUserDropdown";
import { useSelector } from "react-redux";

const NavBar = () => {
  const { loggedIn, userRole } = useSelector(
    (state) => state.loggedInUserSlice
  );

  return (
    <>
      <nav
        className="navbar navbar-expand-lg bg-light"
        data-bs-theme="light"
        style={{ marginBottom: "auto" }}
      >
        <div className="container-fluid">
          <NavLink to="/" end className="navbar-brand">
            E-Tour
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <NavLink to="/" end className="nav-link" aria-current="page">
                  Home
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink to="/tours" end className="nav-link" aria-current="page">
                  Tours
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  to="/contact-us"
                  end
                  className="nav-link"
                  aria-current="page"
                >
                  Contact Us
                </NavLink>
              </li>
              {userRole !== "ROLE_USER" && userRole !== "" && (
                <li className="nav-item">
                  <NavLink to="/users" end className="nav-link">
                    Users
                  </NavLink>
                </li>
              )}
            </ul>
            {loggedIn ? (
              <LoggedInUserDropdown />
            ) : (
              <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <NavLink to="/login" end className="nav-link">
                    Login
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink to="/register" end className="nav-link">
                    Register
                  </NavLink>
                </li>
              </ul>
            )}
          </div>
        </div>
      </nav>
    </>
  );
};

export default NavBar;
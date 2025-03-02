import { NavLink, useNavigate } from "react-router-dom";
import { userAPI } from "../../services/UserService";
import { LOGGEDIN, ROLE, USER_ID } from "../../constants/cache.key";
import { useDispatch } from "react-redux";
import { setLoggedOutUser } from "../../store/features/user/loggedInUseSlice";

const LoggedInUserDropdown = () => {
  const { data: user, isLoading } = userAPI.useFetchUserQuery();
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const [logout] = userAPI.useLogoutMutation();

  const onLogout = async () => {
    localStorage.removeItem(LOGGEDIN);
    localStorage.removeItem(ROLE);
    localStorage.removeItem(USER_ID);
    await logout();
    dispatch(setLoggedOutUser({ userRole: "" }));
    navigate("/home");
  };

  return (
    <div className="flex-shrink-0 dropdown">
      <a
        className="d-block link-body-emphasis text-decoration-none dropdown-toggle profile-dropdown"
        style={{ cursor: "pointer" }}
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        <img
          src={
            isLoading
              ? "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExODF4MTlob2VueGN5YTk4dTFhZTVleGplZGRhNndlYjVpeTkwaHNpdCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/3o7bu3XilJ5BOiSGic/giphy.gif"
              : user?.data.user.imageUrl
          }
          alt="mdo"
          width="32"
          height="32"
          className="rounded-circle"
        />
      </a>
      <ul
        className="dropdown-menu dropdown-menu-end"
        style={{ paddingInline: "10px" }}
      >
        <li>
          <NavLink
            to="/user/profile"
            end
            className="rounded-2 dropdown-item d-flex gap-2 align-items-center"
          >
            <img
              src={
                isLoading
                  ? "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExODF4MTlob2VueGN5YTk4dTFhZTVleGplZGRhNndlYjVpeTkwaHNpdCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/3o7bu3XilJ5BOiSGic/giphy.gif"
                  : user?.data.user.imageUrl
              }
              alt={user?.data.user.firstName}
              width="35"
              height="35"
              className="rounded-circle"
            />
            <div className="">
              <p
                style={{
                  display: "block",
                  margin: 0,
                  padding: 0,
                  color: "#000 !important",
                }}
              >
                {user?.data.user.firstName} {user?.data.user.lastName}
              </p>
              <p
                style={{
                  display: "block",
                  margin: 0,
                  padding: 0,
                  fontSize: "12px",
                  fontWeight: 600,
                }}
              >
                {user?.data.user.email}
              </p>
            </div>
          </NavLink>
        </li>
        <hr className="dropdown-divider" />
        <li>
          <NavLink
            to="/user/password"
            end
            className="rounded-2 dropdown-item d-flex gap-2 align-items-center rounded-2"
          >
            <i className="bi bi-key"></i>
            Password
          </NavLink>
        </li>
        <hr className="dropdown-divider" />
        <li>
          <NavLink
            to="/user/mybookings"
            end
            className="rounded-2 dropdown-item d-flex gap-2 align-items-center rounded-2"
          >
            <i className="bi bi-journal-bookmark"></i>
            My Bookings
          </NavLink>
        </li>
        <hr className="dropdown-divider" />
        <li>
          <a
            onClick={onLogout}
            className="rounded-2 dropdown-item dropdown-item-danger d-flex gap-2 align-items-center"
            style={{ cursor: "pointer" }}
          >
            <i className="bi bi-box-arrow-right"></i>
            Logout
          </a>
        </li>
      </ul>
    </div>
  );
};

export default LoggedInUserDropdown;
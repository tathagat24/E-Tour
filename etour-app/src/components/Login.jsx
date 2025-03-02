import { Link, useNavigate } from "react-router-dom";
import { z } from "zod";
import { LOGGEDIN, ROLE, USER_ID } from "../constants/cache.key";
import { userAPI } from "../services/UserService";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useDispatch } from "react-redux";
import { setLoggedInUser } from "../store/features/user/loggedInUseSlice";
import { useEffect } from "react";

const loginSchema = z.object({
  email: z.string().min(3, "Email is required").email("Invalid email address"),
  password: z.string().min(5, "Password is required"),
});

const Login = () => {
  const isLoggedIn = JSON.parse(localStorage.getItem(LOGGEDIN)) || false;
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [loginUser, { error, isLoading, isSuccess, data }] =
    userAPI.useLoginUserMutation();

  const {
    register,
    handleSubmit,
    formState: form,
    getFieldState,
  } = useForm({ resolver: zodResolver(loginSchema), mode: "onTouched" });

  const isFieldValid = (fieldName) =>
    getFieldState(fieldName, form).isTouched &&
    !getFieldState(fieldName, form).invalid;

  const onLogin = (credentials) => loginUser(credentials);

  if (isSuccess) {
    localStorage.setItem(LOGGEDIN, "true");
    localStorage.setItem(ROLE, JSON.stringify(data?.data?.user.role));
    localStorage.setItem(USER_ID, JSON.stringify(data?.data?.user.id));
    dispatch(setLoggedInUser({ userRole: data?.data?.user.role }));
    navigate("/home");
  }

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/home");
    }
  })

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div
          className="col-lg-6 col-md-6 col-sm-12"
          style={{ marginTop: "150px" }}
        >
          <div className="card">
            <div className="card-body">
              <h4 className="mb-3">Login</h4>
              {error && (
                <div className="alert alert-dismissible alert-danger">
                  {"data" in error
                    ? error.data.message
                    : "An error has occurred"}
                </div>
              )}
              <hr />
              <form
                onSubmit={handleSubmit(onLogin)}
                className="needs-validation"
                noValidate
              >
                <div className="row g-3">
                  <div className="col-12">
                    <label htmlFor="email" className="form-label">
                      Email Address
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-envelope"></i>
                      </span>
                      <input
                        type="text"
                        {...register("email")}
                        name="email"
                        autoComplete="on"
                        className={`form-control ${
                          form.errors.email ? "is-invalid" : ""
                        } ${isFieldValid("email") ? "is-valid" : ""}`}
                        id="email"
                        placeholder="Email Address"
                        disabled={false}
                      />
                      <div className="invalid-feedback">
                        {form.errors.email?.message}
                      </div>
                    </div>
                  </div>
                  <div className="col-12">
                    <label htmlFor="password" className="form-label">
                      Password
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-key"></i>
                      </span>
                      <input
                        type="password"
                        {...register("password")}
                        name="password"
                        autoComplete="on"
                        className={`form-control ${
                          form.errors.password ? "is-invalid" : ""
                        } ${isFieldValid("password") ? "is-valid" : ""}`}
                        id="password"
                        placeholder="Password"
                        disabled={false}
                      />
                      <div className="invalid-feedback">
                        {form.errors.password?.message}
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col mt-3">
                  <button
                    disabled={form.isSubmitting || isLoading}
                    className="btn btn-primary btn-block "
                    type={"submit"}
                  >
                    {(form.isSubmitting || isLoading) && (
                      <span
                        className="spinner-border spinner-border-sm"
                        aria-hidden={"true"}
                      ></span>
                    )}
                    <span role={"status"}>
                      {form.isSubmitting || isLoading ? "Loading..." : "Login"}
                    </span>
                  </button>
                </div>
              </form>
              <hr className="my-3" />
              <div className="row mb-3">
                <div className="col d-flex justify-content-start">
                  <div className="btn btn-outline-light">
                    <Link to="/register" style={{ textDecoration: "none" }}>
                      Create an Account
                    </Link>
                  </div>
                </div>
                <div className="col d-flex justify-content-end ">
                  <div className="link-dark">
                    <Link to="/resetpassword">Forgot password?</Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

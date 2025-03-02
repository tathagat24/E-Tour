import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { userAPI } from "../services/UserService";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LOGGEDIN } from "../constants/cache.key";

const schema = z.object({
  email: z.string().min(3, "Email is required").email("Invalid email address"),
  firstName: z.string().min(1, "First name is required"),
  middleName: z.string().min(1, "Middle name is required"),
  lastName: z.string().min(1, "Last name is required"),
  password: z.string().min(5, "Password is required"),
});

const Register = () => {
  const isLoggedIn = JSON.parse(localStorage.getItem(LOGGEDIN)) || false;
  const navigate = useNavigate();

  const { register, handleSubmit, formState, reset, getFieldState } = useForm({
    resolver: zodResolver(schema),
    mode: "onTouched",
  });

  const [registerUser, { data, error, isLoading, isSuccess }] =
    userAPI.useRegisterUserMutation();

  const isFieldValid = (fieldName) =>
    getFieldState(fieldName, formState).isTouched &&
    !getFieldState(fieldName, formState).invalid;

  const handleRegister = async (registerRequest) =>
    await registerUser(registerRequest);

  useEffect(() => reset(), [isSuccess, reset]);

  useEffect(() => {
    if (isLoggedIn) { 
      navigate("/home"); 
    }
  });

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div
          className="col-lg-6 col-md-6 col-sm-12"
          style={{ marginTop: "100px" }}
        >
          <div className="card">
            <div className="card-body">
              <h4 className="mb-3">Register</h4>
              {error && (
                <div className="alert alert-dismissible alert-danger">
                  {"data" in error ? error.data.message : "An error occurred"}
                </div>
              )}
              {isSuccess && (
                <div className="alert alert-dismissible alert-success">
                  {data.message}
                </div>
              )}
              <hr />
              <form
                onSubmit={handleSubmit(handleRegister)}
                className="needs-validation"
                noValidate
              >
                <div className="row g-3">
                  <div className="col-12">
                    <label htmlFor="firstName" className="form-label">
                      First name
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-person-vcard"></i>
                      </span>
                      <input
                        type="text"
                        {...register("firstName")}
                        name="firstName"
                        className={`form-control ' ${
                          formState.errors.firstName ? "is-invalid" : ""
                        } ${isFieldValid("firstName") ? "is-valid" : ""}`}
                        id="firstName"
                        placeholder="First name"
                        disabled={isLoading}
                        required
                      />
                      <div className="invalid-feedback">
                        {formState.errors.firstName?.message}
                      </div>
                    </div>
                  </div>
                  <div className="col-12">
                    <label htmlFor="middleName" className="form-label">
                      Middle name
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-person-vcard"></i>
                      </span>
                      <input
                        type="text"
                        {...register("middleName")}
                        name="middleName"
                        className={`form-control ' ${
                          formState.errors.middleName ? "is-invalid" : ""
                        } ${isFieldValid("middleName") ? "is-valid" : ""}`}
                        id="middleName"
                        placeholder="Middle name"
                        disabled={isLoading}
                        required
                      />
                      <div className="invalid-feedback">
                        {formState.errors.middleName?.message}
                      </div>
                    </div>
                  </div>
                  <div className="col-12">
                    <label htmlFor="lastName" className="form-label">
                      Last name
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-person-vcard"></i>
                      </span>
                      <input
                        type="text"
                        {...register("lastName")}
                        name="lastName"
                        className={`form-control ' ${
                          formState.errors.lastName ? "is-invalid" : ""
                        } ${isFieldValid("lastName") ? "is-valid" : ""}`}
                        id="lastName"
                        placeholder="Last Name"
                        disabled={isLoading}
                        required
                      />
                      <div className="invalid-feedback">
                        {formState.errors.lastName?.message}
                      </div>
                    </div>
                  </div>
                  <div className="col-12">
                    <label htmlFor="email" className="form-label">
                      Email address
                    </label>
                    <div className="input-group has-validation">
                      <span className="input-group-text">
                        <i className="bi bi-envelope"></i>
                      </span>
                      <input
                        type="text"
                        {...register("email")}
                        name="email"
                        className={`form-control ' ${
                          formState.errors.email ? "is-invalid" : ""
                        } ${isFieldValid("email") ? "is-valid" : ""}`}
                        id="email"
                        placeholder="Email address"
                        disabled={isLoading}
                        required
                      />
                      <div className="invalid-feedback">
                        {formState.errors.email?.message}
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
                        className={`form-control ' ${
                          formState.errors.password ? "is-invalid" : ""
                        } ${isFieldValid("password") ? "is-valid" : ""}`}
                        placeholder="Password"
                        disabled={isLoading}
                        required
                      />
                      <div className="invalid-feedback">
                        {formState.errors.password?.message}
                      </div>
                    </div>
                  </div>
                </div>
                <hr className="my-4" />
                <div className="col">
                  <button
                    disabled={formState.isSubmitting || isLoading}
                    className="btn btn-primary"
                    type="submit"
                  >
                    {(formState.isSubmitting || isLoading) && (
                      <span
                        className="spinner-border spinner-border-sm"
                        aria-hidden="true"
                      ></span>
                    )}
                    <span role="status">
                      {formState.isSubmitting || isLoading
                        ? "Loading..."
                        : "Register"}
                    </span>
                  </button>
                </div>
              </form>
              <hr className="my-3" />
              <div className="row mb-3">
                <div className="col d-flex justify-content-start">
                  <div className="btn btn-outline-light">
                    <Link to="/login" style={{ textDecoration: "none" }}>
                      Go to login
                    </Link>
                  </div>
                </div>
                <div className="col d-flex justify-content-end">
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

export default Register;

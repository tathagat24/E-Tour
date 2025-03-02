import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { userAPI } from "../../services/UserService";
import Loader from "./Loader.jsx";

const schema = z.object({
  email: z.string().min(3, "Email is required").email("Invalid email address"),
  firstName: z.string().min(1, "First name is required"),
  middleName: z.string().min(1, "Middle name is required"),
  lastName: z.string().min(1, "Last name is required"),
  bio: z.string().min(5, "Bio is required"),
  phone: z.string().min(5, "Phone is required"),
  addressLine: z.string().min(5, "Address is required"),
  city: z.string().min(1, "City is required"),
  state: z.string().min(1, "State is required"),
  country: z.string().min(1, "Country is required"),
  zipCode: z.string().min(1, "Zip code is required"),
});

const Profile = () => {
  const {
    register,
    handleSubmit,
    formState: form,
    getFieldState,
  } = useForm({ resolver: zodResolver(schema), mode: "onTouched" });

  const { data: user, isSuccess, isLoading } = userAPI.useFetchUserQuery();

  const [update, { isLoading: updateLoading }] =
    userAPI.useUpdateUserMutation();

  const updateUser = async (user) => await update(user);

  const isFieldValid = (fieldName) =>
    getFieldState(fieldName, form).isTouched &&
    !getFieldState(fieldName, form).invalid;

  return (
    <>
      {isLoading && <Loader />}
      {isSuccess && (
        <>
          <h4 className="mb-3">Profile</h4>
          <hr />
          <form
            onSubmit={handleSubmit(updateUser)}
            className="needs-validation"
            noValidate
          >
            <div className="row g-3">
              <div className="col-sm-6">
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
                    className={`form-control ' ${
                      form.errors.firstName ? "is-invalid" : ""
                    } ${isFieldValid("firstName") ? "is-valid" : ""}`}
                    placeholder="First name"
                    defaultValue={user?.data.user.firstName}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.firstName?.message}
                  </div>
                </div>
              </div>
              <div className="col-sm-6">
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
                    className={`form-control ' ${
                      form.errors.middleName ? "is-invalid" : ""
                    } ${isFieldValid("middleName") ? "is-valid" : ""}`}
                    placeholder="Middle name"
                    defaultValue={user?.data.user.middleName}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.middleName?.message}
                  </div>
                </div>
              </div>
              <div className="col-sm-6">
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
                    className={`form-control ' ${
                      form.errors.lastName ? "is-invalid" : ""
                    } ${isFieldValid("lastName") ? "is-valid" : ""}`}
                    placeholder="Last name"
                    defaultValue={user?.data.user.lastName}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.lastName?.message}
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
                    className={`form-control ' ${
                      form.errors.email ? "is-invalid" : ""
                    } ${isFieldValid("email") ? "is-valid" : ""}`}
                    placeholder="Email"
                    defaultValue={user?.data.user.email}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.email?.message}
                  </div>
                </div>
              </div>
              <div className="col-12">
                <label htmlFor="phone" className="form-label">
                  Phone number
                </label>
                <div className="input-group has-validation">
                  <span className="input-group-text">
                    <i className="bi bi-telephone"></i>
                  </span>
                  <input
                    type="text"
                    {...register("phone")}
                    className={`form-control ' ${
                      form.errors.phone ? "is-invalid" : ""
                    } ${isFieldValid("phone") ? "is-valid" : ""}`}
                    placeholder="123-456-7890"
                    defaultValue={user?.data.user.phone}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.phone?.message}
                  </div>
                </div>
              </div>
              <div className="col-12">
                <label htmlFor="bio" className="form-label">
                  Bio
                </label>
                <textarea
                  className={`form-control ' ${
                    form.errors.bio ? "is-invalid" : ""
                  } ${isFieldValid("bio") ? "is-valid" : ""}`}
                  {...register("bio")}
                  placeholder="Something about yourself here"
                  defaultValue={user?.data.user.bio}
                  disabled={user?.data.user.role === "MANAGER"}
                  rows={3}
                  required
                ></textarea>
                <div className="invalid-feedback">
                  {form.errors.bio?.message}
                </div>
              </div>
              <div className="col-12">
                <label htmlFor="addressLine" className="form-label">
                  Address
                </label>
                <textarea
                  className={`form-control ' ${
                    form.errors.addressLine ? "is-invalid" : ""
                  } ${isFieldValid("addressLine") ? "is-valid" : ""}`}
                  {...register("addressLine")}
                  placeholder="Enter your address here"
                  defaultValue={user?.data.user.addressLine}
                  disabled={user?.data.user.role === "MANAGER"}
                  rows={3}
                  required
                ></textarea>
                <div className="invalid-feedback">
                  {form.errors.addressLine?.message}
                </div>
              </div>
              <div className="col-sm-6">
                <label htmlFor="city" className="form-label">
                  City
                </label>
                <div className="input-group has-validation">
                  <span className="input-group-text">
                    <i className="bi bi-geo-alt"></i>
                  </span>
                  <input
                    type="text"
                    {...register("city")}
                    className={`form-control ' ${
                      form.errors.city ? "is-invalid" : ""
                    } ${isFieldValid("city") ? "is-valid" : ""}`}
                    placeholder="Enter your city"
                    defaultValue={user?.data.user.city}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.city?.message}
                  </div>
                </div>
              </div>
              <div className="col-sm-6">
                <label htmlFor="state" className="form-label">
                  State
                </label>
                <div className="input-group has-validation">
                  <span className="input-group-text">
                    <i className="bi bi-geo-alt"></i>
                  </span>
                  <input
                    type="text"
                    {...register("state")}
                    className={`form-control ' ${
                      form.errors.state ? "is-invalid" : ""
                    } ${isFieldValid("state") ? "is-valid" : ""}`}
                    placeholder="Enter your state"
                    defaultValue={user?.data.user.state}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.state?.message}
                  </div>
                </div>
              </div>
              <div className="col-sm-6">
                <label htmlFor="country" className="form-label">
                  Country
                </label>
                <div className="input-group has-validation">
                  <span className="input-group-text">
                    <i className="bi bi-geo-alt"></i>
                  </span>
                  <input
                    type="text"
                    {...register("country")}
                    className={`form-control ' ${
                      form.errors.country ? "is-invalid" : ""
                    } ${isFieldValid("country") ? "is-valid" : ""}`}
                    placeholder="Enter your country"
                    defaultValue={user?.data.user.country}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.country?.message}
                  </div>
                </div>
              </div>
              <div className="col-sm-6">
                <label htmlFor="zipCode" className="form-label">
                  Zip Code
                </label>
                <div className="input-group has-validation">
                  <span className="input-group-text">
                    <i className="bi bi-geo-alt"></i>
                  </span>
                  <input
                    type="text"
                    {...register("zipCode")}
                    className={`form-control ' ${
                      form.errors.zipCode ? "is-invalid" : ""
                    } ${isFieldValid("zipCode") ? "is-valid" : ""}`}
                    placeholder="Enter your zip code"
                    defaultValue={user?.data.user.zipCode}
                    disabled={user?.data.user.role === "MANAGER"}
                    required
                  />
                  <div className="invalid-feedback">
                    {form.errors.zipCode?.message}
                  </div>
                </div>
              </div>
            </div>
            <hr className="my-4" />
            <div className="col">
              <button
                disabled={
                  !form.isValid ||
                  form.isSubmitting ||
                  isLoading ||
                  updateLoading ||
                  user?.data.user.role === "MANAGER"
                }
                className="btn btn-primary btn-block"
                type="submit"
              >
                {(form.isSubmitting || isLoading || updateLoading) && (
                  <span
                    className="spinner-border spinner-border-sm"
                    aria-hidden="true"
                  ></span>
                )}
                <span role="status">
                  {form.isSubmitting || isLoading || updateLoading
                    ? "Loading..."
                    : "Update"}
                </span>
              </button>
            </div>
          </form>
        </>
      )}
    </>
  );
};

export default Profile;
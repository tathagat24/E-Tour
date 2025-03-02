import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import {
  FaUser,
  FaEnvelope,
  FaPhone,
  FaBirthdayCake,
  FaMoneyBillWave,
  FaUndo,
} from "react-icons/fa";
import { useState } from "react";

const passengerSchema = z.object({
  firstName: z.string().min(1, "First name is required"),
  middleName: z.string().min(1, "Middle name is required"),
  lastName: z.string().min(1, "Last name is required"),
  email: z.string(),
  phone: z.string(),
  dateOfBirth: z.string().min(1, "Date of birth is required"),
  age: z.number().min(1, "Age is required"),
  gender: z.string().min(2, "Gender is required"),
  passengerType: z.string().min(1, "Passenger Type is required"),
  passengerCost: z.number().min(1, "Passenger cost is required"),
});

const PassengerForm = ({ handlePassengerSubmit, tour }) => {
  const [passengerTypes, setPassengerTypes] = useState([]);

  const { register, handleSubmit, formState, reset, getFieldState, setValue } =
    useForm({
      resolver: zodResolver(passengerSchema),
      mode: "onTouched",
    });

  const tourPrice = {
    singlePersonPrice: tour?.tourPrice?.singlePersonPrice,
    twinSharingPrice: tour?.tourPrice?.twinSharingPrice,
    extraPersonPrice: tour?.tourPrice?.extraPersonPrice,
    childWithBedPrice: tour?.tourPrice?.childWithBedPrice,
    childWithoutBedPrice: tour?.tourPrice?.childWithoutBedPrice,
  };

  const isFieldValid = (fieldName) =>
    getFieldState(fieldName, formState).isTouched &&
    !getFieldState(fieldName, formState).invalid;

  const handlePassengerSubmitForm = (passenger) => {
    handlePassengerSubmit(passenger);
    reset();
    setPassengerTypes([]);
  };

  const handlePassengerTypeChange = (passengerType) => {
    let cost;

    switch (passengerType) {
      case "SINGLE_PERSON":
        cost = tourPrice.singlePersonPrice;
        break;
      case "TWIN_SHARING":
        cost = tourPrice.twinSharingPrice;
        break;
      case "EXTRA_PERSON":
        cost = tourPrice.extraPersonPrice;
        break;
      case "CHILD_WITH_BED":
        cost = tourPrice.childWithBedPrice;
        break;
      case "CHILD_WITHOUT_BED":
        cost = tourPrice.childWithoutBedPrice;
        break;
      default:
        cost = 0;
        break;
    }

    // Update the passengerCost field with the correct cost value
    setValue("passengerCost", cost);
  };

  // Function to calculate the age based on date of birth
  const calculateAge = (dob) => {
    const birthDate = new Date(dob);
    const currentDate = new Date();
    let age = currentDate.getFullYear() - birthDate.getFullYear();
    const month = currentDate.getMonth();
    if (
      month < birthDate.getMonth() ||
      (month === birthDate.getMonth() &&
        currentDate.getDate() < birthDate.getDate())
    ) {
      age--;
    }
    return age;
  };

  const handleReset = () => {
    reset();
    setPassengerTypes([]);
  }

  // Update the age field when date of birth changes
  const handleDateOfBirthChange = (e) => {
    let passengersType = [];

    const dob = e.target.value;
    const age = calculateAge(dob);
    setValue("age", age);

    if (age <= 8) {
      passengersType = [
        {
          name: "Child without Bed",
          value: "CHILD_WITHOUT_BED",
        },
      ];
    } else if (age > 8 && age <= 16) {
      passengersType = [
        {
          name: "Child with Bed",
          value: "CHILD_WITH_BED",
        },
      ];
    } else {
      passengersType = [
        {
          name: "Single Person",
          value: "SINGLE_PERSON",
        },
        {
          name: "Twin Sharing",
          value: "TWIN_SHARING",
        },
        {
          name: "Extra Person",
          value: "EXTRA_PERSON",
        },
      ];
    }
    setPassengerTypes(passengersType);
  };

  return (
    <div className="card shadow-lg mb-4">
      <div className="card-header bg-primary text-white">
        <h4 className="mb-0">
          <FaUser className="me-2" />
          Add Passenger
        </h4>
      </div>
      <div className="card-body">
        <form onSubmit={handleSubmit(handlePassengerSubmitForm)}>
          <div className="row g-4">
            {/* Name Section */}
            <div className="col-12">
              <h5 className="text-secondary mb-3">Personal Information</h5>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <input
                  type="text"
                  {...register("firstName")}
                  className={`form-control ' ${
                    formState.errors.firstName ? "is-invalid" : ""
                  } ${isFieldValid("firstName") ? "is-valid" : ""}`}
                  id="firstName"
                  placeholder="First name"
                />
                <label htmlFor="firstName">First Name</label>
                <div className="invalid-feedback">
                  {formState.errors.firstName?.message}
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <input
                  type="text"
                  {...register("middleName")}
                  className={`form-control ' ${
                    formState.errors.middleName ? "is-invalid" : ""
                  } ${isFieldValid("middleName") ? "is-valid" : ""}`}
                  id="middleName"
                  placeholder="Middle name"
                />
                <label htmlFor="middleName">Middle Name</label>
                <div className="invalid-feedback">
                  {formState.errors.middleName?.message}
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <input
                  type="text"
                  {...register("lastName")}
                  className={`form-control ' ${
                    formState.errors.lastName ? "is-invalid" : ""
                  } ${isFieldValid("lastName") ? "is-valid" : ""}`}
                  id="lastName"
                  placeholder="Last name"
                />
                <label htmlFor="lastName">Last Name</label>
                <div className="invalid-feedback">
                  {formState.errors.lastName?.message}
                </div>
              </div>
            </div>

            {/* Contact Section */}
            <div className="col-md-6">
              <div className="form-floating">
                <input
                  type="email"
                  {...register("email")}
                  className={`form-control ' ${
                    formState.errors.email ? "is-invalid" : ""
                  } ${isFieldValid("email") ? "is-valid" : ""}`}
                  id="email"
                  placeholder="name@example.com"
                />
                <label htmlFor="email">
                  <FaEnvelope className="me-2" />
                  Email Address
                </label>
                <div className="invalid-feedback">
                  {formState.errors.email?.message}
                </div>
              </div>
            </div>
            <div className="col-md-6">
              <div className="form-floating">
                <input
                  type="tel"
                  {...register("phone")}
                  className={`form-control ' ${
                    formState.errors.phone ? "is-invalid" : ""
                  } ${isFieldValid("phone") ? "is-valid" : ""}`}
                  id="phone"
                  placeholder="Phone number"
                />
                <label htmlFor="phone">
                  <FaPhone className="me-2" />
                  Phone Number
                </label>
                <div className="invalid-feedback">
                  {formState.errors.phone?.message}
                </div>
              </div>
            </div>

            {/* Birth Info Section */}
            <div className="col-md-6">
              <div className="form-floating">
                <input
                  type="date"
                  {...register("dateOfBirth")}
                  name="dateOfBirth"
                  className={`form-control ' ${
                    formState.errors.dateOfBirth ? "is-invalid" : ""
                  } ${isFieldValid("dateOfBirth") ? "is-valid" : ""}`}
                  id="dateOfBirth"
                  placeholder="Date of Birth"
                  required
                  onChange={handleDateOfBirthChange}
                />
                <label htmlFor="dateOfBirth">
                  <FaBirthdayCake className="me-2" />
                  Date of Birth
                </label>
                <div className="invalid-feedback">
                  {formState.errors.dateOfBirth?.message}
                </div>
              </div>
            </div>
            <div className="col-md-6">
              <div className="form-floating">
                <input
                  type="number"
                  {...register("age", { valueAsNumber: true })}
                  name="age"
                  className={`form-control ' ${
                    formState.errors.age ? "is-invalid" : ""
                  } ${isFieldValid("age") ? "is-valid" : ""}`}
                  id="age"
                  placeholder="Age"
                  required
                  disabled
                />
                <label htmlFor="age">Age</label>
                <div className="invalid-feedback">
                  {formState.errors.age?.message}
                </div>
              </div>
            </div>

            {/* Passenger Details Section */}
            <div className="col-12">
              <h5 className="text-secondary mb-3">Passenger Details</h5>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <select
                  {...register("gender")}
                  className={`form-control ' ${
                    formState.errors.gender ? "is-invalid" : ""
                  } ${isFieldValid("gender") ? "is-valid" : ""}`}
                  id="gender"
                >
                  <option value="">Select Gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="OTHER">Other</option>
                </select>
                <label htmlFor="gender">Gender</label>
                <div className="invalid-feedback">
                  {formState.errors.gender?.message}
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <select
                  {...register("passengerType")}
                  className={`form-control ' ${
                    formState.errors.passengerType ? "is-invalid" : ""
                  } ${isFieldValid("passengerType") ? "is-valid" : ""}`}
                  id="passengerType"
                  onChange={(e) => handlePassengerTypeChange(e.target.value)}
                >
                  <option value="">Select Type</option>
                  {passengerTypes?.map((type, index) => (
                    <option key={index} value={type.value}>
                      {type.name}
                    </option>
                  ))}
                </select>
                <label htmlFor="passengerType">Passenger Type</label>
                <div className="invalid-feedback">
                  {formState.errors.passengerType?.message}
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="form-floating">
                <input
                  type="number"
                  {...register("passengerCost", { valueAsNumber: true })}
                  className={`form-control ' ${
                    formState.errors.passengerCost ? "is-invalid" : ""
                  } ${isFieldValid("passengerCost") ? "is-valid" : ""}`}
                  id="passengerCost"
                  disabled
                />
                <label htmlFor="passengerCost">
                  <FaMoneyBillWave className="me-2" />
                  Cost
                </label>
                <div className="invalid-feedback">
                  {formState.errors.passengerCost?.message}
                </div>
              </div>
            </div>

            {/* Form Actions */}
            <div className="col-12 d-flex justify-content-end gap-3">
              <button
                type="button"
                className="btn btn-outline-secondary"
                onClick={handleReset}
              >
                <FaUndo className="me-2" />
                Reset
              </button>
              <button type="submit" className="btn btn-primary">
                Add Passenger
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default PassengerForm;

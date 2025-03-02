import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import PassengerForm from "../components/tours/PassengerForm";
import { FaCheckCircle, FaRupeeSign, FaUsers, FaTimes } from "react-icons/fa";
import { USER_ID } from "../constants/cache.key";
import { tourAPI } from "../services/TourService";
import { CiCalendarDate } from "react-icons/ci";
import { toastWarning } from "../services/ToastService";

const TourBooking = () => {
  const { tourId } = useParams();
  const navigate = useNavigate();
  const tours = useSelector((store) => store.toursSlice);
  const tour = tours.find((tour) => tour.id === +tourId);

  const [passengers, setPassengers] = useState([]);
  const [departureId, setDepartureId] = useState(0);

  const handlePassengerSubmit = (passenger) => {
    console.log("Passenger data:", passenger);
    setPassengers([...passengers, passenger]);
  };

  const totalCost = passengers.reduce(
    (total, passenger) => total + passenger.passengerCost,
    0
  );

  const [createTourBooking, { data, isLoading, isSuccess }] =
    tourAPI.useCreateTourBookingMutation();

  const handleConfirmBooking = async () => {
    let childrens = 0;
    let adults = 0;

    passengers.forEach((passenger) => {
      if (passenger.age <= 16) {
        childrens++;
      } else {
        adults++;
      }
    });

    if (childrens > adults && adults === 0) {
      toastWarning("Please add adult person");
      return;
    }

    if (adults >= childrens || adults * 2 >= childrens) {
      const booking = {
        totalPrice: totalCost,
        tourId: +tourId,
        departureId: +departureId,
        userId: JSON.parse(localStorage.getItem(USER_ID)) || 0,
        passengers: passengers,
      };
      console.log("Booking data:", booking);
      await createTourBooking(booking);
    } else {
      toastWarning("Please add adult person");
    }
  };

  const handleDeletePassenger = (index) => {
    const updatedPassengers = passengers.filter((_, i) => i !== index);
    setPassengers(updatedPassengers);
  };

  if (isSuccess) {
    navigate(`/booking/booking-details/${data?.data?.tourBooking?.id}`);
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-10">
          <div className="card border-0 shadow-lg">
            <div className="card-header bg-light text-dark">
              <h2 className="mb-0">
                Booking for {tour ? tour.tourName : "Selected Tour"}
              </h2>
            </div>

            <div className="card-body">
              <div className="mt-3">
                <h4 className="mb-4">
                  <CiCalendarDate className="me-2" />
                  Select Departure Date:
                </h4>
                <select
                  name="departureId"
                  id="departureId"
                  className="form-select"
                  onChange={(e) => setDepartureId(e.target.value)}
                >
                  <option value="">Select Date</option>
                  {tour?.departures?.map((departure) => (
                    <option key={departure.id} value={departure.id}>
                      {departure.startDate} - {departure.endDate}
                    </option>
                  ))}
                </select>
              </div>

              <br />

              <PassengerForm
                handlePassengerSubmit={handlePassengerSubmit}
                tour={tour}
              />

              {/* Passengers Table */}
              <div className="mt-5">
                <h4 className="mb-4">
                  <FaUsers className="me-2" />
                  Passengers List
                </h4>
                <div className="table-responsive">
                  <table className="table table-hover align-middle">
                    <thead className="table-light">
                      <tr>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Date of Birth</th>
                        <th>Type</th>
                        <th className="text-end">Price</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {passengers.map((passenger, index) => (
                        <tr key={index}>
                          <td>
                            {`${passenger.firstName} ${passenger.middleName} ${passenger.lastName}`}
                          </td>
                          <td>{passenger.age}</td>
                          <td>
                            {new Date(
                              passenger.dateOfBirth
                            ).toLocaleDateString()}
                          </td>
                          <td>
                            <span className="badge bg-info">
                              {passenger.passengerType.replace(/_/g, " ")}
                            </span>
                          </td>
                          <td className="text-end">
                            <FaRupeeSign className="me-1" />
                            {passenger.passengerCost.toFixed(2)}
                          </td>
                          <td>
                            <button
                              className="btn btn-danger btn-sm"
                              onClick={() => handleDeletePassenger(index)}
                            >
                              <FaTimes />
                            </button>
                          </td>
                        </tr>
                      ))}
                      {passengers.length === 0 && (
                        <tr>
                          <td
                            colSpan="6"
                            className="text-center text-muted py-4"
                          >
                            No passengers added yet
                          </td>
                        </tr>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>

              {/* Total Price and Confirmation */}
              <div className="row mt-5">
                <div className="col-md-6">
                  <div className="alert alert-success h-100">
                    <h5 className="d-flex align-items-center gap-2">
                      <FaRupeeSign />
                      Total Price: {totalCost.toFixed(2)}
                    </h5>
                    <small className="text-muted">
                      * Includes all applicable charges
                    </small>
                  </div>
                </div>
                <div className="col-md-6 d-flex align-items-center justify-content-end">
                  <button
                    className="btn btn-success btn-lg"
                    onClick={handleConfirmBooking}
                    disabled={passengers.length === 0 || isLoading}
                  >
                    {isLoading ? (
                      <span
                        className="spinner-border spinner-border-sm"
                        aria-hidden={"true"}
                      ></span>
                    ) : (
                      <FaCheckCircle className="me-2" />
                    )}
                    Confirm Booking
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TourBooking;

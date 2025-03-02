import { useParams } from "react-router-dom";
import { tourAPI } from "../services/TourService";
import TourLoader from "../components/tours/TourLoader";
import {
  FaCalendar,
  FaUser,
  FaHotel,
  FaIdBadge,
  FaRupeeSign,
  FaUsers,
} from "react-icons/fa";
import { GiMoneyStack } from "react-icons/gi";
import { httpPost } from "../constants/http";
import { useState } from "react";

const BookingDetails = () => {
  const { bookingId } = useParams();
  const [loading, setLoading] = useState(false);
  const { data, isLoading, isSuccess } =
    tourAPI.useFetchTourBookingByIdQuery(bookingId);

  if (isLoading) return <TourLoader />;

  if (!isSuccess || !data) return <div>Error loading booking details</div>;

  const getStatusBadge = (status) => {
    const statusColors = {
      PENDING: "warning",
      CONFIRMED: "success",
      CANCELLED: "danger",
      COMPLETED: "primary",
    };
    return <span className={`badge bg-${statusColors[status]}`}>{status}</span>;
  };

  const handlePayment = () => {
    setLoading(true);
    console.log("Bookind Data", { data });
    fetch("http://localhost:8085/api/v1/tour-service/payment/checkout/hosted", {
      credentials: "include",
      method: httpPost,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data?.data?.tourBooking),
    })
      .then((r) => r.text())
      .then((r) => {
        window.location.href = r;
      });
  };

  return (
    <div className="container py-5">
      <div className="card shadow-lg">
        <div className="card-header bg-light">
          <h2 className="mb-0 d-flex align-items-center gap-3">
            <FaIdBadge /> Booking ID: {data?.data?.tourBooking?.referenceId}
          </h2>
        </div>

        <div className="card-body">
          {/* Booking Summary */}
          <div className="row mb-5">
            <div className="col-md-6">
              <div className="card mb-3">
                <div className="card-body">
                  <h5 className="card-title">
                    <FaCalendar /> Booking Details
                  </h5>
                  <ul className="list-unstyled">
                    <li>
                      <strong>Booking Date:</strong>{" "}
                      {new Date(
                        data?.data?.tourBooking?.bookingDate
                      ).toLocaleDateString()}
                    </li>
                    <li>
                      <strong>Status:</strong>{" "}
                      {getStatusBadge(data?.data?.tourBooking?.bookingStatus)}
                    </li>
                    <li>
                      <strong>Total Price:</strong> <FaRupeeSign />{" "}
                      {data?.data?.tourBooking?.totalPrice.toFixed(2)}
                    </li>
                  </ul>
                </div>
              </div>
            </div>

            <div className="col-md-6">
              <div className="card mb-3">
                <div className="card-body">
                  <h5 className="card-title">
                    <FaHotel /> Tour Information
                  </h5>
                  <ul className="list-unstyled">
                    <li>
                      <strong>Tour:</strong> {data?.data?.tourBooking?.tourName}
                    </li>
                    <li>
                      <strong>Duration:</strong>{" "}
                      {data?.data?.tourBooking?.duration}
                    </li>
                    <li>
                      <strong>Dates:</strong>{" "}
                      {data?.data?.tourBooking?.startDate} to{"   "}
                      {data?.data?.tourBooking?.endDate}
                    </li>
                    <li>
                      <strong>Category:</strong>{" "}
                      {data?.data?.tourBooking?.categoryName} (
                      {data?.data?.tourBooking?.subCategoryName})
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

          {/* User Details */}
          <div className="card mb-5">
            <div className="card-body">
              <h5 className="card-title">
                <FaUser /> User Information
              </h5>
              <div className="row">
                <div className="col-md-6">
                  <ul className="list-unstyled">
                    <li>
                      <strong>Name:</strong>{" "}
                      {data?.data?.tourBooking?.user.firstName}{" "}
                      {data?.data?.tourBooking?.user.middleName}{" "}
                      {data?.data?.tourBooking?.user.lastName}
                    </li>
                    <li>
                      <strong>Email:</strong>{" "}
                      {data?.data?.tourBooking?.user.email}
                    </li>
                    <li>
                      <strong>Phone:</strong>{" "}
                      {data?.data?.tourBooking?.user.phone}
                    </li>
                  </ul>
                </div>
                <div className="col-md-6">
                  <ul className="list-unstyled">
                    <li>
                      <strong>Address:</strong>{" "}
                      {data?.data?.tourBooking?.user.addressLine}
                    </li>
                    <li>
                      <strong>City:</strong>{" "}
                      {data?.data?.tourBooking?.user.city},{" "}
                      {data?.data?.tourBooking?.user.state}
                    </li>
                    <li>
                      <strong>Country:</strong>{" "}
                      {data?.data?.tourBooking?.user.country} -{" "}
                      {data?.data?.tourBooking?.user.zipCode}
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

          {/* Passengers List */}
          <div className="card mb-5">
            <div className="card-body">
              <h5 className="card-title">
                <FaUsers /> Passengers
              </h5>
              <div className="table-responsive">
                <table className="table table-hover">
                  <thead className="table-light">
                    <tr>
                      <th>Name</th>
                      <th>Gender</th>
                      <th>Age</th>
                      <th>Date of Birth</th>
                      <th>Type</th>
                      <th>Cost</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data?.data?.tourBooking?.passengers.map(
                      (passenger, index) => (
                        <tr key={index}>
                          <td>
                            {passenger.firstName} {passenger.middleName}{" "}
                            {passenger.lastName}
                          </td>
                          <td>{passenger.gender}</td>
                          <td>{passenger.age}</td>
                          <td>
                            {new Date(
                              passenger.dateOfBirth
                            ).toLocaleDateString()}
                          </td>
                          <td>{passenger.passengerType.replace(/_/g, " ")}</td>
                          <td>
                            <FaRupeeSign /> {passenger.passengerCost.toFixed(2)}
                          </td>
                        </tr>
                      )
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          {/* Payment Section */}
          {data?.data?.tourBooking?.bookingStatus === "PENDING" && (
            <div className="text-center mt-4">
              <button
                className="btn btn-success btn-lg px-5 py-3"
                onClick={handlePayment}
                disabled={loading}
              >
                {loading ? (
                  <span
                    className="spinner-border spinner-border-sm"
                    aria-hidden={"true"}
                  ></span>
                ) : (
                  <GiMoneyStack className="me-2" />
                )}
                Pay Now
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default BookingDetails;

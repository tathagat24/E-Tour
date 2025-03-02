import { Link } from "react-router-dom";
import { tourAPI } from "../services/TourService";
import { USER_ID } from "../constants/cache.key";

const MyBookings = () => {
  const userId = JSON.parse(localStorage.getItem(USER_ID)) || 0;
  const { data, isLoading } = tourAPI.useFetchTourBookingsByUserIdQuery(userId);

  const getStatusBadge = (status) => {
    const statusColors = {
      PENDING: "warning",
      CONFIRMED: "success",
      CANCELLED: "danger",
      COMPLETED: "primary",
    };
    return <span className={`badge bg-${statusColors[status]}`}>{status}</span>;
  };

  if (isLoading) {
    return (
      <div className="container mtb">
        <div className="row">
          <div className="col-xl-12">
            <div className="card">
              <div className="card-body">
                <h5 className="header-title pb-3 mt-0">Bookings</h5>
                <p className="card-text placeholder-glow">
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                  <span className="placeholder col-12"></span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container mtb">
      <div className="row">
        <div className="col-xl-12">
          <div className="card">
            <div className="card-body">
              <h5 className="header-title pb-3 mt-0">Bookings</h5>
              <div className="table-responsive">
                <table className="table table-striped table-hover mb-0">
                  <thead>
                    <tr>
                      <th>Booking Id</th>
                      <th>Booking Date</th>
                      <th>Booking Status</th>
                      <th>Total Price</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data?.data?.tourBookings.map((booking) => (
                      <tr key={booking.id}>
                        <td>{booking.referenceId}</td>
                        <td>
                          {new Date(
                            booking.bookingDate
                          ).toLocaleDateString()}
                        </td>
                        <td>{getStatusBadge(booking.bookingStatus)}</td>
                        <td>{booking.totalPrice}</td>
                        <td>
                          <Link
                            to={`/booking/booking-details/${booking.id}`}
                            className="btn btn-sm btn-primary"
                          >
                            View Details
                          </Link>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyBookings;
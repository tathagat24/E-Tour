import { Link } from "react-router-dom";

const TourCard = ({ tour }) => {
  return (
    <div className="col-lg-4 col-md-6 mb-4" key={tour.id}>
      <div className="card h-100 border-0 shadow-sm">
        <div className="position-relative">
          <img
            src={tour.imageUrl}
            className="card-img-top"
            alt={tour.tourName}
            style={{
              height: "250px",
              objectFit: "cover",
            }}
          />
        </div>
        <div className="card-body p-4">
          <h5 className="card-title fw-semibold fs-5 mb-3">{tour.tourName}</h5>
          <p className="card-text text-muted">{tour.description}</p>
          <p className="text-muted">
            <strong>Duration:</strong> {tour.duration}
          </p>
        </div>
        <div className="card-footer bg-transparent border-0 p-4 pt-0">
          <Link to={`/tours/tour-details/${tour.id}`} className="btn btn-outline-primary w-100">
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
};

export default TourCard;
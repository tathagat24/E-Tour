import { Link } from "react-router-dom";

const TourCategoryCard = ({ tour }) => {
  return (
    <div className="col-lg-4 col-md-6 mb-4" key={tour.id}>
      <div className="tour-card card h-100 border-0 overflow-hidden shadow-sm">
        <div className="position-relative">
          <img
            src={tour.imageUrl}
            className="card-img-top"
            alt={tour.categoryName}
            style={{
              height: "280px",
              objectFit: "cover",
              transition: "transform 0.3s ease",
            }}
          />
          {/* Overlay effect when hovering over the image */}
          <div className="overlay position-absolute top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center bg-dark bg-opacity-50 opacity-0 hover-opacity-100 transition-opacity">
            <h6 className="text-white text-center fs-4">{tour.categoryName}</h6>
          </div>
        </div>
        <div className="card-body p-4">
          <h5 className="card-title fw-semibold fs-5 mb-3">{tour.categoryName}</h5>
        </div>
        <div className="card-footer bg-transparent border-0 p-4 pt-0">
          <Link to={`/sub-tour-categories/${tour.id}`} className="btn btn-outline-primary w-100">
            View Tours
          </Link>
        </div>
      </div>
    </div>
  );
};

export default TourCategoryCard;
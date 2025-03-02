import { Link } from "react-router-dom";

const TourSubcategoryCard = ({ subcategory }) => {
  return (
    <div className="col-lg-3 col-md-4 col-sm-6 mb-4" key={subcategory.id}>
      <div className="card h-100 border-0 shadow-sm">
        <div className="position-relative">
          <img
            src={subcategory.imageUrl}
            className="card-img-top"
            alt={subcategory.subCategoryName}
            style={{
              height: "220px",
              objectFit: "cover",
              transition: "transform 0.3s ease",
            }}
          />
          <div className="overlay position-absolute top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center bg-dark bg-opacity-50 opacity-0 hover-opacity-100 transition-opacity">
            <h6 className="text-white text-center fs-4">
              {subcategory.subCategoryName}
            </h6>
          </div>
        </div>
        <div className="card-body p-4">
          <h5 className="card-title fw-semibold fs-5 mb-3">
            {subcategory.subCategoryName}
          </h5>
        </div>
        <div className="card-footer bg-transparent border-0 p-4 pt-0">
          <Link
            to={`/sub-tour-categories/tours/${subcategory.id}`}
            className="btn btn-outline-primary w-100"
          >
            View Tours
          </Link>
        </div>
      </div>
    </div>
  );
};

export default TourSubcategoryCard;
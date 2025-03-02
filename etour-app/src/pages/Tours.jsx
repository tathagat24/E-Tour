import { useParams } from "react-router-dom";
import TourCard from "../components/tours/TourCard";
import { useDispatch, useSelector } from "react-redux";
import { tourAPI } from "../services/TourService";
import TourLoader from "../components/tours/TourLoader";
import { addInitialTours } from "../store/features/tour/toursSlice";

const Tours = () => {
  const { tourSubCategoryId } = useParams();
  const dispatch = useDispatch();
  const tourSubCategories = useSelector((store) => store.tourSubCategorySlice);

  const tourSubCategory = tourSubCategories.find(
    (tour) => tour.id === +tourSubCategoryId
  );

  const {data, isLoading, isSuccess} = tourAPI.useFetchAllToursByTourSubCategoryIdQuery(tourSubCategoryId);

  if (isSuccess) {
    dispatch(addInitialTours(data?.data?.tours));
  }

  if(isLoading) {
    return (<TourLoader />);
  }

  return (
    <div className="tour-subcategory-details">
      {tourSubCategory && (
        <div className="hero-section position-relative overflow-hidden mb-5">
          <img
            src={tourSubCategory.imageUrl}
            alt={tourSubCategory.subCategoryName}
            className="img-fluid w-100 hero-image"
            style={{ height: "400px", objectFit: "cover" }}
          />
          <div className="hero-overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center">
            <div className="container text-white">
              <h1 className="display-4 fw-bold mb-3">
                {tourSubCategory.subCategoryName}
              </h1>
              <p className="mt-3">Explore our exciting tours</p>{" "}
            </div>
          </div>
        </div>
      )}

      <div className="container py-6">
        <div className="text-center mb-5">
          <h2 className="display-5 fw-semibold mb-3">
            Tours in this Subcategory
          </h2>
          <p className="text-muted">
            Explore our exciting tour options in this subcategory.
          </p>
        </div>

        <div className="row g-4">
          {data?.data?.tours.length > 0 ? (
            data?.data?.tours?.map((tour) => <TourCard tour={tour} key={tour.id} />)
          ) : (
            <div className="col-12 text-center">
              <p>No tours available for this subcategory.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Tours;
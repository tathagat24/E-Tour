import { useParams } from "react-router-dom";
import TourSubcategoryCard from "../components/tours/TourSubcategoryCard";
import { tourSubCategoryAPI } from "../services/TourSubCategoryService";
import TourLoader from "../components/tours/TourLoader";
import { useSelector } from "react-redux";

const TourSubCategories = () => {
  const { tourCategoryId } = useParams();

  const { data, isLoading } =
    tourSubCategoryAPI.useFetchTourSubCategoriesByTourCategoryIdQuery(
      tourCategoryId
    );

  const tourCategories = useSelector((store) => store.tourCategorySlice);

  const tourCategory = tourCategories.find(
    (tour) => tour.id === +tourCategoryId
  );

  if (isLoading) {
    return <TourLoader />;
  }

  return (
    <div className="tour-subcategories">
      {tourCategory && (
        <div className="hero-section position-relative overflow-hidden mb-5">
          <img
            src={tourCategory.imageUrl}
            alt={tourCategory.categoryName}
            className="img-fluid w-100 hero-image"
            style={{ height: "400px", objectFit: "cover" }}
          />
          <div className="hero-overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center">
            <div className="container text-white">
              <h1 className="display-4 fw-bold mb-3">
                {tourCategory.categoryName} TOURS
              </h1>
              <p className="mt-3">Explore our exciting tour subcategories.</p>{" "}
            </div>
          </div>
        </div>
      )}

      <div className="container py-6">
        <div className="text-center mb-5">
          <h2 className="display-5 fw-semibold mb-3">Tour Subcategories</h2>
        </div>

        <div className="row g-4">
          {data?.data?.tourSubcategories?.length > 0 ? (
            data?.data?.tourSubcategories.map((subcategory) => (
              <TourSubcategoryCard
                subcategory={subcategory}
                key={subcategory.id}
              />
            ))
          ) : (
            <div className="col-12 text-center">
              <p>No subcategories available for this tour category.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TourSubCategories;
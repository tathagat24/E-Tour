import { useDispatch } from "react-redux";
import TourCategoryCard from "../components/tours/TourCategoryCard";
import TourLoader from "../components/tours/TourLoader";
import { tourCategoryAPI } from "../services/TourCategoryService";
import { addInitialTourCategories } from "../store/features/tour/tourCategorySlice";
import { tourSubCategoryAPI } from "../services/TourSubCategoryService";
import { addInitialTourSubCategories } from "../store/features/tour/tourSubCategorySlice";
import { tourAPI } from "../services/TourService";
import TourCard from "../components/tours/TourCard";
import { addInitialTours } from "../store/features/tour/toursSlice";
import HomeHero from "../components/tours/HomeHero";

const HomePage = () => {
  const { data, isLoading, isSuccess } =
    tourCategoryAPI.useFetchTourCategoriesQuery();

  const {
    data: subTourData,
    isLoading: subTourLoading,
    isSuccess: subTourSuccess,
  } = tourSubCategoryAPI.useFetchAllTourSubCategoriesQuery();

  const {
    data: popularToursData,
    isLoading: popularToursLoading,
    isSuccess: popularToursSuccess,
  } = tourAPI.useFetchAllPopularToursQuery();

  const {
    data: toursData,
    isLoading: toursLoading,
    isSuccess: toursSuccess,
  } = tourAPI.useFetchAllToursQuery();

  const dispatch = useDispatch();

  if (isSuccess && subTourSuccess && popularToursSuccess && toursSuccess) {
    dispatch(addInitialTourCategories(data?.data?.tourCategories));
    dispatch(addInitialTours(toursData?.data?.tours));
    dispatch(addInitialTourSubCategories(subTourData?.data?.tourSubcategories));
  }

  if (isLoading || subTourLoading || popularToursLoading || toursLoading) {
    return <TourLoader />;
  }

  return (
    <div className="home-page">
      <HomeHero />

      <section className="py-6 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-semibold mb-3 py-4">Tour Categories</h2>
            <h5 className="text-muted">Explore our tours</h5>
          </div>

          <div className="row g-4">
            {data?.data?.tourCategories?.map((tour) => (
              <TourCategoryCard tour={tour} key={tour.id} />
            ))}
          </div>
        </div>
      </section>

      <section className="py-6 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-semibold mb-3 py-4">Popular Tours</h2>
            <h5 className="text-muted">Explore our popular tours</h5>
          </div>

          <div className="row g-4">
            {popularToursData?.data?.tours.length > 0 ? (
              popularToursData?.data?.tours?.map((tour) => (
                <TourCard tour={tour} key={tour.id} />
              ))
            ) : (
              <div className="col-12 text-center">
                <p>No tours popular available.</p>
              </div>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;

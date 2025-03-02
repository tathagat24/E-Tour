import { useState } from "react";
import TourCard from "../components/tours/TourCard";
import { useSelector } from "react-redux";
import { toastWarning } from "../services/ToastService";

const ToursPage = () => {
  const [filteredTours, setFilteredTours] = useState([]);
  const [subCategories, setSubCategories] = useState([]);
  const [searchParams, setSearchParams] = useState({
    tourCategory: "",
    tourSubCategory: "",
    startDate: "",
    endDate: "",
  });

  const tourCategories = useSelector((store) => store.tourCategorySlice);
  const tourSubCategories = useSelector((store) => store.tourSubCategorySlice);
  const tours = useSelector((store) => store.toursSlice);

  const handleSearch = () => {
    let filtered = tours;
  
    // Check if all search fields are provided
    if (
      searchParams.tourCategory.length === 0 ||
      searchParams.tourSubCategory.length === 0 ||
      searchParams.startDate.length === 0 ||
      searchParams.endDate.length === 0
    ) {
      toastWarning("Please provide all fields");
      return;
    }
  
    const searchData = {
      tourCategoryId: +searchParams.tourCategory,
      tourSubCategoryId: +searchParams.tourSubCategory,
      startDate: searchParams.startDate,
      endDate: searchParams.endDate,
    };
  
    // Filter by Tour Category and SubCategory
    filtered = filtered.filter(
      (tour) =>
        tour.tourSubcategoryId === searchData.tourSubCategoryId
    );
  
    // Filter by Departure Date Range
    filtered = filtered.filter((tour) => {

      // Check if any of the tour's departures match the date range
      return tour.departures.some((departure) => {
        const departureStartDate = new Date(departure.startDate); // Departure start date
        const departureEndDate = new Date(departure.endDate); // Departure end date
        const startDate = new Date(searchData.startDate); // User's search start date
        const endDate = new Date(searchData.endDate); // User's search end date
        // Check if the tour's departure dates overlap with the search date range
        return (
          (departureStartDate <= endDate && departureEndDate >= startDate) // Check for overlapping date ranges
        );
      });
    });
  
    // Set the filtered tours
    setFilteredTours(filtered);
  
    // Reset search params after filtering
    setSearchParams({
      tourCategory: "",
      tourSubCategory: "",
      startDate: "",
      endDate: "",
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "tourCategory") {
      const subTouCategories = tourSubCategories.filter(
        (subCategory) => subCategory.tourCategoryId === +value
      );
      setSubCategories(subTouCategories);
    }

    setSearchParams({
      ...searchParams,
      [name]: value,
    });
  };

  return (
    <div className="home-page">
      <section className="hero-section position-relative vh-100">
        <div
          className="hero-bg position-absolute w-100 h-100"
          style={{
            backgroundImage:
              "url('https://images.unsplash.com/photo-1503220317375-aaad61436b1b?ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80')",
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        >
          <div className="hero-overlay position-absolute w-100 h-100 bg-dark opacity-50"></div>
        </div>

        <div className="container position-relative h-100 d-flex align-items-center">
          <div className="text-white text-center w-100">
            <h1 className="display-3 fw-bold mb-4">
              Discover Amazing Tour Destinations
            </h1>
            <p className="lead fs-4 mb-5">
              Explore the world&apos;s most fascinating locations with expert
              guides
            </p>

            {/* Search Form */}
            <div
              className="tour-search bg-white rounded-pill shadow-lg p-4 mx-auto"
              style={{ maxWidth: "1400px" }}
            >
              <div className="row g-3 align-items-center">
                <div className="col-md-3">
                  <label htmlFor="tourCategory" className="text-dark">
                    Tour Category
                  </label>
                  <select
                    name="tourCategory"
                    value={searchParams.tourCategory}
                    onChange={handleChange}
                    className="form-select"
                  >
                    <option value="">select</option>
                    {tourCategories?.map((category) => (
                      <option key={category.id} value={category.id}>
                        {category.categoryName}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="col-md-3">
                  <label htmlFor="tourSubCategory" className="text-dark">
                    Tour Sub Category
                  </label>
                  <select
                    name="tourSubCategory"
                    value={searchParams.tourSubCategory}
                    onChange={handleChange}
                    className="form-select"
                  >
                    <option value="">select</option>
                    {subCategories?.map((subCategory) => (
                      <option key={subCategory.id} value={subCategory.id}>
                        {subCategory.subCategoryName}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="col-md-2">
                  <label htmlFor="startDate" className="text-dark">
                    Start Date
                  </label>
                  <input
                    type="date"
                    name="startDate"
                    value={searchParams.startDate}
                    onChange={handleChange}
                    className="form-control"
                  />
                </div>
                <div className="col-md-2">
                  <label htmlFor="endDate" className="text-dark">
                    End Date
                  </label>
                  <input
                    type="date"
                    name="endDate"
                    value={searchParams.endDate}
                    onChange={handleChange}
                    className="form-control"
                  />
                </div>
                <div className="col-md-2">
                  <button
                    className="btn btn-primary w-100 rounded-pill py-3"
                    onClick={handleSearch}
                  >
                    Search Now
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="py-6 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-semibold mb-3 py-4">Searched Tours</h2>
            <h5 className="text-muted">Explore our tours</h5>
          </div>

          <div className="row g-4">
            {filteredTours.length > 0 ? (
              filteredTours.map((tour) => (
                <TourCard tour={tour} key={tour.id} />
              ))
            ) : (
              <div className="col-12 text-center">
                <p>No tours found. Try adjusting your search criteria.</p>
              </div>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default ToursPage;

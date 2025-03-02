import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import {
  FaClock,
  FaRegStar,
  FaRupeeSign,
  FaStar,
  FaThumbsUp,
  FaUserCircle,
} from "react-icons/fa";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { z } from "zod";
import { LOGGEDIN, USER_ID } from "../constants/cache.key";
import { toastError, toastWarning } from "../services/ToastService";
import { tourAPI } from "../services/TourService";

const reviewSchema = z.object({
  reviewText: z.string().min(1, "Review is required"),
});

const TourDetails = () => {
  const isLoggedIn = JSON.parse(localStorage.getItem(LOGGEDIN)) || false;
  const { tourId } = useParams();
  const navigate = useNavigate();

  const tours = useSelector((store) => store.toursSlice);

  const tour = tours.find((tour) => tour.id === +tourId);

  console.log("tour", tour);

  const tourPrice = {
    singlePersonPrice: tour?.tourPrice?.singlePersonPrice,
    twinSharingPrice: tour?.tourPrice?.twinSharingPrice,
    extraPersonPrice: tour?.tourPrice?.extraPersonPrice,
    childWithBedPrice: tour?.tourPrice?.childWithBedPrice,
    childWithoutBedPrice: tour?.tourPrice?.childWithoutBedPrice,
  };

  const [rating, setRating] = useState(0);
  const [reviews, setReviews] = useState(tour?.tourReviews);

  const { register, handleSubmit, formState, reset, getFieldState } = useForm({
    resolver: zodResolver(reviewSchema),
    mode: "onTouched",
  });

  const isFieldValid = (fieldName) =>
    getFieldState(fieldName, formState).isTouched &&
    !getFieldState(fieldName, formState).invalid;

  const handleBookNow = () => {
    navigate(`/tours/booking/${tourId}`);
  };

  const [ addTourReview ] = tourAPI.useAddTourReviewMutation();

  const handleSubmitReview = async (userReview) => {
    if (!isLoggedIn) {
      toastError("You are not logged in. Please log in to submit review");
      return;
    }

    if (rating < 1 || rating > 5) {
      toastWarning("Please provide a valid rating (1-5)");
      return;
    }
    const newReview = {
      rating,
      review: userReview.reviewText,
      tourId: +tourId,
      userId: JSON.parse(localStorage.getItem(USER_ID)) || 0,
    };

    await addTourReview(newReview);

    reset();
    setReviews([...reviews, newReview]);
    setRating(0);
  };

  const StarRating = ({ rating, setRating }) => {
    return (
      <div className="star-rating mb-3">
        {[...Array(5)].map((_, index) => (
          <button
            type="button"
            key={index}
            className="star-btn"
            onClick={() => setRating(index + 1)}
            onMouseEnter={() =>
              document
                .querySelectorAll(".star-btn")
                .forEach((btn, i) =>
                  i <= index ? btn.classList.add("hover") : null
                )
            }
            onMouseLeave={() =>
              document
                .querySelectorAll(".star-btn")
                .forEach((btn) => btn.classList.remove("hover"))
            }
          >
            {index < rating ? (
              <FaStar className="text-warning" size={28} />
            ) : (
              <FaRegStar className="text-muted" size={28} />
            )}
          </button>
        ))}
      </div>
    );
  };

  return (
    <div className="tour-details bg-light">
      {/* Hero Section */}
      <div className="hero-section position-relative overflow-hidden mb-5">
        <img
          src={tour.imageUrl}
          alt={tour.tourName}
          className="img-fluid w-100 hero-image"
          style={{ height: "400px", objectFit: "cover" }}
        />
        <div className="hero-overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center">
          <div className="container text-white">
            <h1 className="display-4 fw-bold mb-3">{tour.tourName}</h1>
            <div className="d-flex gap-4">
              <span className="d-flex align-items-center gap-2">
                <FaClock /> {tour.duration}
              </span>
            </div>
            <p className="mt-3">{tour.description}</p>{" "}
            {/* Added description below dates */}
          </div>
        </div>
      </div>

      <div className="container">
        {/* Pricing Section */}
        <section className="pricing-section mb-5">
          <h2 className="mb-4 fw-bold border-bottom pb-2">Tour Pricing</h2>
          <div className="row g-4">
            {Object.entries(tourPrice).map(([key, value]) => (
              <div key={key} className="col-md-4">
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title text-capitalize">
                      {key.replace(/([A-Z])/g, " $1").trim()}
                    </h5>
                    <div className="d-flex align-items-center gap-2">
                      <FaRupeeSign />
                      <span className="h4 mb-0">{value}</span>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          <button
            className="btn btn-primary btn-lg mt-4 px-5 py-3 d-flex align-items-center gap-2"
            onClick={handleBookNow}
          >
            <FaThumbsUp /> Book Now
          </button>
        </section>

        {/* Itinerary Section */}
        <section className="itinerary-section mb-5">
          <h2 className="mb-4 fw-bold border-bottom pb-2">Tour Itinerary</h2>
          <div className="timeline">
            {tour.itineraries.map((itinerary) => (
              <div key={itinerary.day} className="timeline-item">
                <div className="timeline-badge">{itinerary.day}</div>
                <div className="timeline-card card shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title">{itinerary.itineraryName}</h5>
                    <p className="card-text text-muted">
                      {itinerary.description}
                    </p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Departure Section */}
        <section className="itinerary-section mb-5">
          <h2 className="mb-4 fw-bold border-bottom pb-2">Tour Departure dates: </h2>
          <div className="timeline">
            {tour.departures.map((departure) => (
              <div key={departure.id} className="timeline-item">
                <div className="timeline-card card shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title">Start Date: {departure.startDate} To End Date: {departure.endDate}</h5>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Reviews Section */}
        <section className="reviews-section mb-5">
          <h2 className="mb-4 fw-bold border-bottom pb-2">Customer Reviews</h2>
          <div className="row g-4">
            {reviews.map((review, index) => (
              <div key={index} className="col-md-6">
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <div className="d-flex align-items-center gap-3 mb-3">
                      <FaUserCircle size={40} className="text-secondary" />
                      <div>
                        <h6 className="mb-0">User {index + 1}</h6>
                        <small className="text-muted">Verified Traveler</small>
                      </div>
                    </div>
                    <div className="star-rating small">
                      {[...Array(5)].map((_, i) =>
                        i < review.rating ? (
                          <FaStar key={i} className="text-warning" />
                        ) : (
                          <FaRegStar key={i} className="text-muted" />
                        )
                      )}
                    </div>
                    <p className="mt-2 mb-0">{review.review}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Review Form */}
        <section className="review-form mb-5">
          <h2 className="mb-4 fw-bold border-bottom pb-2">Write a Review</h2>
          <div className="card shadow-sm">
            <div className="card-body">
              <form onSubmit={handleSubmit(handleSubmitReview)}>
                <div className="mb-4">
                  <label className="form-label fw-bold">Your Rating</label>
                  <StarRating rating={rating} setRating={setRating} />
                </div>

                <div className="mb-4">
                  <label htmlFor="reviewText" className="form-label fw-bold">
                    Review Details
                  </label>
                  <textarea
                    className={`form-control ' ${
                      formState.errors.reviewText ? "is-invalid" : ""
                    } ${isFieldValid("reviewText") ? "is-valid" : ""}`}
                    {...register("reviewText")}
                    rows="4"
                    placeholder="Share your experience with this tour..."
                    required
                  />
                  <div className="invalid-feedback">
                    {formState.errors.reviewText?.message}
                  </div>
                </div>

                <button type="submit" className="btn btn-success px-5 py-2">
                  Submit Review
                </button>
              </form>
            </div>
          </div>
        </section>
      </div>

      <style>{`
        .hero-overlay {
          background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.7));
        }
        
        .timeline {
          position: relative;
          padding-left: 40px;
        }

        .timeline-item {
          position: relative;
          margin-bottom: 30px;
        }

        .timeline-badge {
          position: absolute;
          left: -56px;
          top: 15px;
          width: 40px;
          height: 40px;
          border-radius: 50%;
          background: #0d6efd;
          color: white;
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: bold;
        }

        .timeline-card {
          transition: transform 0.3s ease;
        }

        .timeline-card:hover {
          transform: translateX(10px);
        }

        .star-btn {
          background: none;
          border: none;
          padding: 5px;
        }

        .star-btn.hover .fa-star {
          color: #ffc107 !important;
        }
      `}</style>
    </div>
  );
};

export default TourDetails;
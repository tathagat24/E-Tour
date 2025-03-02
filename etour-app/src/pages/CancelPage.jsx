import { Link, useParams } from "react-router-dom";

const CancelPage = () => {
  const { bookingReferenceId } = useParams();

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div
          className="col-lg-6 col-md-6 col-sm-12"
          style={{ marginTop: "100px", marginBottom: "50px" }}
        >
          <div className="card">
            <div className="card-body">
              <div className="alert alert-dismissible alert-danger">
                Payment Cancelled
              </div>
              <hr className="my-3" />
              <div className="row mb-3">
                <div className="col d-flex justify-content-start">
                  <div className="btn btn-outline-light">
                    <Link to="/home" style={{ textDecoration: "none" }}>
                      Go to Home
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CancelPage;

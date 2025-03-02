const Hero = () => {
    
  return (
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
            className="tour-search bg-white rounded-pill shadow-lg p-3 mx-auto"
            style={{ maxWidth: "800px" }}
          >
            <div className="row g-2 align-items-center">
              <div className="col-md-4">
                <input
                  type="text"
                  className="form-control border-0"
                  placeholder="Destination..."
                />
              </div>
              <div className="col-md-3">
                <select className="form-select border-0">
                  <option>Tour Type</option>
                  <option>Adventure</option>
                  <option>Cultural</option>
                  <option>Beach</option>
                </select>
              </div>
              <div className="col-md-3">
                <select className="form-select border-0">
                  <option>Duration</option>
                  <option>3-5 Days</option>
                  <option>1-2 Weeks</option>
                  <option>Month+</option>
                </select>
              </div>
              <div className="col-md-2">
                <button className="btn btn-primary w-100 rounded-pill py-3">
                  Search Now
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Hero;

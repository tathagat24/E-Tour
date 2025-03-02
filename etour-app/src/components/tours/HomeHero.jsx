const HomeHero = () => {
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
        </div>
      </div>
    </section>
  );
};

export default HomeHero;

import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";

const Footer = () => {
  return (
    <footer className="bg-dark-subtle text-white py-5">
      <div className="container">
        <div className="row g-4">
          <div className="col-lg-4 col-md-6">
            <h5 className="text-uppercase mb-4 fw-bold">E-Tour</h5>
            <p className="text-white">
              Creating unforgettable travel experiences through personalized
              service and expert guidance.
            </p>
          </div>

          <div className="col-lg-4 col-md-6">
            <h5 className="text-uppercase mb-4 fw-bold">Contact Info</h5>
            <ul className="list-unstyled text-white">
              <li className="mb-2">
                <i className="bi bi-geo-alt-fill"></i> 123 Travel Street, Tour
                City
              </li>
              <li className="mb-2">
                <i className="bi bi-telephone-fill"></i> +1 (555) 123-4567
              </li>
              <li>
                <i className="bi bi-envelope-fill"></i> etour@gmail.com
              </li>
            </ul>
          </div>

          <div className="col-lg-4 col-md-12">
            <h5 className="text-uppercase mb-4 fw-bold">Follow Us</h5>
            <div className="d-flex gap-3 justify-content-start">
              <a href="#facebook" className="text-white fs-4 social-icon">
                <FaFacebook />
              </a>
              <a href="#twitter" className="text-white fs-4 social-icon">
                <FaTwitter />
              </a>
              <a href="#instagram" className="text-white fs-4 social-icon">
                <FaInstagram />
              </a>
              <a href="#linkedin" className="text-white fs-4 social-icon">
                <FaLinkedin />
              </a>
            </div>
          </div>
        </div>

        <hr className="my-5 opacity-150" />

        <div className="text-center text-white small">
          <p>&copy; 2025 E-Tour. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

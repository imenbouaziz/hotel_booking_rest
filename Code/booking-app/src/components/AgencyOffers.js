import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { AgencyOfferServiceClient } from '../grpc/AvailabilityService_grpc_web_pb'; // gRPC-Web client
import { AgencyOfferRequest } from '../grpc/AvailabilityService_pb'; // gRPC message classes

const grpcClient = new AgencyOfferServiceClient('http://localhost:8083'); // Replace with your gRPC-Web server URL

function AgencyOffers({ onAgencySelect }) {
  const [agencyId, setAgencyId] = useState('');
  const [agencyLogin, setAgencyLogin] = useState('');
  const [agencyPassword, setAgencyPassword] = useState('');
  const [criteria, setCriteria] = useState({
    city: 'Paris',
    stars: 5,
    category: 'Luxury',
    capacity: 2,
    startDate: '2024-11-13',
    endDate: '2024-11-15',
    maxPrice: 500,
  });
  const [offers, setOffers] = useState([]);
  const [error, setError] = useState('');

  const agencyCredentials = {
    1: { login: 'hello', password: 'hello' },
    2: { login: 'hello2', password: 'hello2' },
    3: { login: 'premium', password: 'premiumpass' },
  };

  const handleAgencySelect = (e) => {
    const selectedAgencyId = e.target.value;
    setAgencyId(selectedAgencyId);

    if (selectedAgencyId === '') {
      setAgencyLogin('');
      setAgencyPassword('');
    } else {
      const { login, password } = agencyCredentials[selectedAgencyId] || {};
      setAgencyLogin(login);
      setAgencyPassword(password);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCriteria((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const fetchOffers = async () => {
    try {
      setOffers([]);
      setError('');

      // Create a new request using the generated AgencyOfferRequest class
      const request = new AgencyOfferRequest();

      // Set the request parameters
      request.setAgencyId(parseInt(agencyId, 10));
      request.setAgencyLogin(agencyLogin);
      request.setAgencyPassword(agencyPassword);
      request.setCity(criteria.city);
      request.setStars(criteria.stars);
      request.setCategory(criteria.category);
      request.setCapacity(criteria.capacity);
      request.setStart(criteria.startDate);
      request.setEnd(criteria.endDate);
      request.setMaxPrice(criteria.maxPrice);

      // Use the gRPC-Web client to send the request
      grpcClient.getOffersForAgency(request, {}, (err, response) => {
        if (err) {
          setError(err.message || 'Failed to fetch offers');
          console.error('Error fetching offers:', err); // Debug: Log any error
          return;
        }

        // Log the response to debug the server's return
        console.log('Response received:', response);

        // Map the response to a more usable format
        const offersList = response.getOffersList().map((offer) => ({
          id: offer.getId(),
          hotelId: offer.getHotelId(),
          hotelName: offer.getHotelName(),
          hotelStars: offer.getHotelStars(),
          roomId: offer.getRoomId(),
          agencyId: offer.getAgencyId(),
          agencyName: offer.getAgencyName(),
          capacity: offer.getCapacity(),
          percentage: offer.getPercentage(),
          newPrice: offer.getNewPrice(),
          roomImageUrls: offer.getRoomImageUrlsList(),
        }));

        setOffers(offersList);

        if (offersList.length === 0) {
          setError('No offers found for the selected criteria');
        }
      });
    } catch (err) {
      setError('Failed to fetch offers');
      console.error('Request error:', err); // Debug: Log any unexpected error
    }
  };

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4">Find the Perfect Offers</h2>

      <div className="row mb-4">
        <div className="col-md-4">
          <label className="form-label">Choose an Agency:</label>
          <select
            className="form-select"
            value={agencyId}
            onChange={handleAgencySelect}
          >
            <option value="">All Agencies</option>
            <option value="1">Agency 1</option>
            <option value="2">Agency 2</option>
            <option value="3">Agency 3</option>
          </select>
        </div>
      </div>

      <form
        className="row g-3"
        onSubmit={(e) => {
          e.preventDefault();
          fetchOffers();
        }}
      >
        {/* Add form fields for search criteria */}
        <div className="col-md-3">
          <label className="form-label">City:</label>
          <input
            type="text"
            className="form-control"
            name="city"
            value={criteria.city}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <label className="form-label">Stars:</label>
          <input
            type="number"
            className="form-control"
            name="stars"
            value={criteria.stars}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-3">
          <label className="form-label">Category:</label>
          <input
            type="text"
            className="form-control"
            name="category"
            value={criteria.category}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <label className="form-label">Capacity:</label>
          <input
            type="number"
            className="form-control"
            name="capacity"
            value={criteria.capacity}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <label className="form-label">Start Date:</label>
          <input
            type="date"
            className="form-control"
            name="startDate"
            value={criteria.startDate}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <label className="form-label">End Date:</label>
          <input
            type="date"
            className="form-control"
            name="endDate"
            value={criteria.endDate}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <label className="form-label">Max Price:</label>
          <input
            type="number"
            className="form-control"
            name="maxPrice"
            value={criteria.maxPrice}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2 d-flex align-items-end">
          <button type="submit" className="btn btn-primary w-100">
            Fetch Offers
          </button>
        </div>
      </form>

      {error && <p className="text-danger text-center my-4">{error}</p>}

      <div className="row mt-5">
        {offers.length > 0 ? (
          offers.map((offer) => (
            <div className="col-md-4 mb-4" key={offer.id}>
              <div className="card shadow-sm">
                <img
                  src={offer.roomImageUrls[0]}
                  className="card-img-top"
                  alt="Room"
                  style={{ height: '200px', objectFit: 'cover' }}
                />
                <div className="card-body">
                  <h5 className="card-title">{offer.hotelName}</h5>
                  <p className="card-text">
                    <strong>Offer ID:</strong> {offer.id}
                    <br />
                    <strong>Agency Name:</strong> {offer.agencyName}
                    <br />
                    <strong>Stars:</strong> {offer.hotelStars}
                    <br />
                    <strong>New Price:</strong> ${offer.newPrice}
                    <br />
                    <strong>Discount:</strong> {offer.percentage}%
                  </p>
                  <button
                    onClick={() => onAgencySelect(agencyId)}
                    className="btn btn-primary w-100"
                  >
                    Select Offer
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <p className="col-12 text-center">No offers available for the selected criteria.</p>
        )}
      </div>
    </div>
  );
}

export default AgencyOffers;

import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

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

      const agencyIds = agencyId ? [agencyId] : [1, 2, 3];
      const allOffers = [];

      for (const id of agencyIds) {
        const query = id
          ? `city=${criteria.city}&stars=${criteria.stars}&category=${criteria.category}&capacity=${criteria.capacity}&start=${criteria.startDate}&end=${criteria.endDate}&maxPrice=${criteria.maxPrice}&agencyLogin=${agencyCredentials[id]?.login}&agencyPassword=${agencyCredentials[id]?.password}`
          : `city=${criteria.city}&stars=${criteria.stars}&category=${criteria.category}&capacity=${criteria.capacity}&start=${criteria.startDate}&end=${criteria.endDate}&maxPrice=${criteria.maxPrice}`;

        try {
          const response = await fetch(
            `http://localhost:8081/api/agency/${id}/offers?${query}`
          );
          if (!response.ok) throw new Error(`No offers found for Agency ${id}`);
          
          const data = await response.json();
          
          if (data && data.length > 0) {
            allOffers.push(...data);
          }
        } catch (err) {
          console.log(`No offers for Agency ${id}:`, err.message);
        }
      }

      setOffers(allOffers);

      if (allOffers.length === 0) {
        setError('No offers found for the selected criteria');
      }
    } catch (err) {
      setError('Failed to fetch offers');
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
          <label className="form-label">Number of people:</label>
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

      <div className="row mt-5"> {}
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

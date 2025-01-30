import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { jsPDF } from 'jspdf'; 

function BookingForm({ agencyId }) {
  const [formData, setFormData] = useState({
    offerId: '',
    clientId: '',
    clientFname: '',
    clientLname: '',
    clientAge: '',
    startDate: '',
    endDate: '',
    cardNumber: '',
    cardHolder: '',
    expirationDate: '',
    cvv: '',
  });
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [showModal, setShowModal] = useState(false); 
  const [bookingDetails, setBookingDetails] = useState({});

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
    
      const queryParams = new URLSearchParams(formData).toString();

      const response = await fetch(
        `http://localhost:8081/api/agencies/${agencyId}/booking/create?${queryParams}`,
        { method: 'POST' }
      );

      if (!response.ok) throw new Error('Failed to create booking');
      
      setSuccessMessage('Booking created successfully!');
      setErrorMessage('');
      setBookingDetails(formData); 
      setShowModal(true);

      downloadBookingPDF(formData);

    } catch (err) {
      setErrorMessage(err.message);
      setSuccessMessage('');
    }
  };

  const downloadBookingPDF = (bookingData) => {
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text('Thank you for booking with us!', 20, 20);
    doc.setFontSize(12);
    
    // Add booking details
    doc.text(`Offer ID: ${bookingData.offerId}`, 20, 40);
    doc.text(`Client ID: ${bookingData.clientId}`, 20, 50);
    doc.text(`Client Name: ${bookingData.clientFname} ${bookingData.clientLname}`, 20, 60);
    doc.text(`Client Age: ${bookingData.clientAge}`, 20, 70);
    doc.text(`Booking Start Date: ${bookingData.startDate}`, 20, 80);
    doc.text(`Booking End Date: ${bookingData.endDate}`, 20, 90);

    doc.save(`booking-details-${Date.now()}.pdf`);
  };

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4">Make a Booking</h2>
      <form onSubmit={handleSubmit} className="row g-3">
        <div className="col-md-4">
          <label className="form-label">Offer ID:</label>
          <input
            type="text"
            name="offerId"
            className="form-control"
            value={formData.offerId}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Client ID:</label>
          <input
            type="text"
            name="clientId"
            className="form-control"
            value={formData.clientId}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">First Name:</label>
          <input
            type="text"
            name="clientFname"
            className="form-control"
            value={formData.clientFname}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Last Name:</label>
          <input
            type="text"
            name="clientLname"
            className="form-control"
            value={formData.clientLname}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Age:</label>
          <input
            type="number"
            name="clientAge"
            className="form-control"
            value={formData.clientAge}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Start Date:</label>
          <input
            type="date"
            name="startDate"
            className="form-control"
            value={formData.startDate}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">End Date:</label>
          <input
            type="date"
            name="endDate"
            className="form-control"
            value={formData.endDate}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Card Number:</label>
          <input
            type="text"
            name="cardNumber"
            className="form-control"
            value={formData.cardNumber}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Card Holder:</label>
          <input
            type="text"
            name="cardHolder"
            className="form-control"
            value={formData.cardHolder}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">Expiration Date:</label>
          <input
            type="text"
            name="expirationDate"
            className="form-control"
            value={formData.expirationDate}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-4">
          <label className="form-label">CVV:</label>
          <input
            type="text"
            name="cvv"
            className="form-control"
            value={formData.cvv}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-12 d-flex justify-content-center">
          <button type="submit" className="btn btn-primary w-50">
            Submit Booking
          </button>
        </div>
      </form>

      {successMessage && (
        <p className="text-center text-success mt-4">{successMessage}</p>
      )}
      {errorMessage && (
        <p className="text-center text-danger mt-4">{errorMessage}</p>
      )}

      {/* Modal for booking success */}
      {showModal && (
        <div className="modal fade show" tabIndex="-1" style={{ display: 'block' }} aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Booking Successful!</h5>
                <button type="button" className="btn-close" onClick={() => setShowModal(false)}></button>
              </div>
              <div className="modal-body">
                <h6>Thank you for booking with us, {bookingDetails.clientFname}!</h6>
                <p><strong>Booking Details:</strong></p>
                <ul>
                  <li>Offer ID: {bookingDetails.offerId}</li>
                  <li>Client ID: {bookingDetails.clientId}</li>
                  <li>Client Name: {bookingDetails.clientFname} {bookingDetails.clientLname}</li>
                  <li>Age: {bookingDetails.clientAge}</li>
                  <li>Start Date: {bookingDetails.startDate}</li>
                  <li>End Date: {bookingDetails.endDate}</li>
                </ul>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default BookingForm;

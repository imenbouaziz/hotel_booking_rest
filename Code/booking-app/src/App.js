import React, { useState } from 'react';
import AgencyOffers from './components/AgencyOffers';
import BookingForm from './components/BookingForm';

function App() {
  const [selectedAgencyId, setSelectedAgencyId] = useState(null);

  return (
    <div style={{ padding: '20px' }}>
      <h1>Trivago dupe (TP03 REST)</h1>
      <hr />
      <AgencyOffers onAgencySelect={setSelectedAgencyId} />
      {selectedAgencyId && (
        <>
          <h2>Booking Form for Agency ID: {selectedAgencyId}</h2>
          <BookingForm agencyId={selectedAgencyId} />
        </>
      )}
    </div>
  );
}

export default App;

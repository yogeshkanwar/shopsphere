import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';

const root = ReactDOM.createRoot(document.getElementById('root'));
const stripePromise = loadStripe('pk_test_TYooMQauvdEDq54NiTphI7jx');

root.render(
  // <React.StrictMode>
    <Elements stripe={stripePromise}>
      <App />
    </Elements>
  // </React.StrictMode>
);

reportWebVitals();

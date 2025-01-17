// src/components/CheckoutButton.js
import React from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { apiService } from '../service/ApiService';

// Make sure to use your own public Stripe API key here
const stripePromise = loadStripe('pk_test_51Pt4dJIFqGjST8kIklJ0XBPJFYbM0SpJBShhI7ahnFkOMVK9tBgK7cpkb8IQ8XkylJ62b3RlRCOFpGkfoGY7qU1z00rMtdTcdb');

const CheckoutButton = () => {

  const handleClick = async () => {
    const stripe = await stripePromise;
    const response = await apiService.checkout();
    console.log("this is response :: " ,response)
     
    const sessionId =  response.data.sessionID;
    const { error } = await stripe.redirectToCheckout({ sessionId });
    if (error) {
      console.error('Error:', error);
    }
  };

  const handleConnect = async () => {
    const response = await apiService.connect();
    console.log("this is response :: " ,response)
     
    const url =  response.data.url;
    if(url){
      window.location.href = url;
    }
  
  };

 

  return (
    <div>
      <button role="link" onClick={handleClick}>
         Checkout
      </button>

      <button role="link" onClick={handleConnect}>
        Connect
      </button>
    </div>
  );
};

export default CheckoutButton;

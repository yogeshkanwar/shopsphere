import React, { useEffect, useState, useCallback } from 'react';
import { apiService } from '../service/ApiService';
import { loadStripe } from '@stripe/stripe-js';
import Navbar from '../common/Navbar';
import { throttle } from 'lodash';

const stripePromise = loadStripe('pk_test_51Pt4dJIFqGjST8kIklJ0XBPJFYbM0SpJBShhI7ahnFkOMVK9tBgK7cpkb8IQ8XkylJ62b3RlRCOFpGkfoGY7qU1z00rMtdTcdb');

const Cart = () => {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetchCart();
    }, []);

    const fetchCart = async () => {
        const response = await apiService.myCart();
        console.log("cart product ",response);
        if (response.status === 200) {
        setProducts(response.data.data.list);
        }
    };

    const handlePayment = async () => {
        const stripe = await stripePromise;
        const response = await apiService.checkout();
        console.log("this is response :: " ,response)
         
        const sessionId =  response.data.sessionID;
        const { error } = await stripe.redirectToCheckout({ sessionId });
        if (error) {
          console.error('Error:', error);
        }
    }  

    const updateQuantity = async (productId, quantity,oldQuantity) => {
        const newQuantity = oldQuantity + quantity;
        const res = await apiService.addToCart(productId,newQuantity)
        if(res.status === 200){
            fetchCart();
            console.log("ok");
        }
      };

    const calculateTotal = () => {
            return products.reduce((total, product) => {
              return total + (product.product.price * product.quantity);
            }, 0).toFixed(2);
    };
    

      return (
        <div className="cart-container">
          <div className="cart-content">
            <Navbar />
            <h2 className="cart-title">My Cart</h2>
            {products.length === 0 ? (
              <p className="empty-cart">Your cart is empty.</p>
            ) : (
              products.map((product) => (
                <div key={product.id} className="cart-item">
                  <img
                    src={`http://localhost:8081/${product.product.imagePath}`}
                    alt={product.product.name}
                    className="cart-item-image"
                  />
                  <div className="cart-item-details">
                    <h3 className="cart-item-name">{product.product.name}</h3>
                    <p className="cart-item-description">{product.product.description}</p>
                    <p className="cart-item-price">₹{product.product.price.toFixed(2)}</p>
                    <div className="quantity-container">
                      <button onClick={() => updateQuantity(product.product.id, -1,product.quantity)} className="quantity-button">-</button>
                      <span className="quantity-display">{product.quantity}</span>
                      <button onClick={() => updateQuantity(product.product.id, 1,product.quantity)} className="quantity-button">+</button>
                    </div>
                  </div>
                </div>
              ))
            )}
            {products.length > 0 && (
              <div className="total-amount">
                Total: ₹{calculateTotal()}
              </div>
            )}
            <button className="pay-button" 
               onClick={handlePayment}
               disabled = {products.length == 0}
            >
              Pay Now
            </button>
          </div>
        </div>
      );
}      

export default Cart;

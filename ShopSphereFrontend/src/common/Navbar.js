import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const handleSignOut = () => {
    localStorage.clear();
    window.location.href = "/";
  };


  return (
    <nav className="navbar">
      <div className="navbar-container">
        <h1 className="navbar-logo">Shop Sphere</h1>
        <ul className="navbar-menu">
          <li className="navbar-item">
            <Link to="/home" className="navbar-link">Home</Link>
          </li>
          <li className="navbar-item">
            <Link to="/wishlist" className="navbar-link">Wishlist</Link>
          </li>
          <li className="navbar-item">
            <Link to="/cart" className="navbar-link">Cart</Link>
          </li>
          <li className="navbar-item">
            <div className="navbar-link" onClick={toggleDropdown}>
              Profile
            </div>
            {dropdownOpen && (
              <div className="dropdown-menu">
                <Link to="/profile" className="dropdown-item">My Profile</Link>
                <Link to="/orders" className="dropdown-item">My Orders</Link>
                <Link to="/address" className="dropdown-item">My Addresses</Link>
                <div onClick={handleSignOut} className="dropdown-item">
                  Sign Out
                </div>              
              </div>
            )}
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Chat from "../component/Chat";
import CheckoutButton from "../component/Stripe";
import Signup from "../component/Signup";
import Login from "../component/Login";
import Home from "../component/Home";
import Cart from "../component/Cart";
import Address from "../component/Address/Address";
import ChatBot from "../component/ChatBot";

const Routing = () => {
  

      return (
        <Router>
          <Routes>
            <Route
              path="/"
              element={<Login />}
            ></Route>
            <Route
              path="/signup"
              element={<Signup />}
            ></Route>
            <Route
              path="/home"
              element={<Home />}
            ></Route>
            <Route
              path="/chat"
              element={<Chat />}
            ></Route>
            <Route
              path="/payment"
              element={<CheckoutButton />}
            ></Route>
            <Route
              path="/cart"
              element={<Cart />}
            ></Route>
            <Route
              path="/address"
              element={<Address />}
            ></Route>
            <Route
              path="/chatbot"
              element={<ChatBot />}
            ></Route>
          </Routes>
        </Router>
      );
    };
  
export default Routing;
  
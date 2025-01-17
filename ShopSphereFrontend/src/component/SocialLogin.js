/* global chrome */

// To generate auth token in frontend we use import { GoogleLogin } from "@react-oauth/google";
// we have to add client id in app.js file







import React from "react";
import { Col, Container, Row, Form, Button } from "react-bootstrap";
import * as Yup from 'yup';
import { Form as FForm, Field, Formik, ErrorMessage } from "formik";
import { useState } from "react";
import { apiService } from "../service/ApiService";
import Swal from 'sweetalert2';
import { useNavigate } from "react-router-dom";
import { webUrl } from "../config/config";
import { GoogleLogin } from "@react-oauth/google";


const Login = () => {

  const initialValues = { email: "", password: "", role: "USER" };
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const navigate = useNavigate();

  const togglePasswordVisibility = () => {
    setIsPasswordVisible(!isPasswordVisible);
  };


  const validationSchema = Yup.object({
    email: Yup.string()
      .email('Email address is not valid')
      .required('Email is required'),
    password: Yup.string().required('Password is required'),
  });

  const handleSubmit = async(values) => {
    const res = await apiService.login(values);
    console.log("response " , res)
    if(res.status === 200){
      
      Swal.fire('Success', 'Login successful!', 'success').then(() => {
      navigate('/home'); 
      });
     } 
  };

   // Function to handle Google OAuth login using chrome.identity
   const handleGoogleLogin = () => {
    console.log('Starting Google Login...');

    // Use the chrome.identity API to get an OAuth token
    chrome.identity.getAuthToken({ interactive: true }, function (token) {
      if (chrome.runtime.lastError) {
        console.error("Google Login Error: ", chrome.runtime.lastError);
        Swal.fire('Error', 'Google login failed', 'error');
      } else {
        console.log('Google Token: ', token);

        // You can send this token to your backend to validate and get user data if needed
        // For example, you could call an API service here to exchange the token for user info
        // Example:
        // const res = await apiService.googleLogin(token);

        Swal.fire('Success', 'Login successful!', 'success').then(() => {
          navigate('/home');
        });
      }
    });
  };

  // const handleGoogleSuccess = async (response) => {
  //   const { credential } = response; 
  //   console.log("Google Login Successful: ", credential);

  //   const res = await apiService.googleLogin(credential);
  //   console.log("response is   ", res)

  //   if (res.status === 200) {
  //     Swal.fire('Success', 'Login successful!', 'success').then(() => {
  //       navigate('/home');
  //     });
  //   } else {
  //     Swal.fire('Error', 'Google login failed', 'error');
  //   }
  // };

  // // Google login error handler
  // const handleGoogleError = (error) => {
  //   console.log('Google Login Error: ', error);
  //   Swal.fire('Error', 'Google login failed', 'error');
  // };



  return (
    <section className="login-area">
      <Container>
        <Row className="justify-content-center">
          <Col md={8} lg={8} xl={6}>
            <div className="login-inner">
              <img src={require("../assets/images/logo.png")} />

              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
                >
                {() => (

                  <FForm>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                      <Form.Label>Email</Form.Label>
                        <Field
                          type="email"
                          placeholder="Enter email"
                          id="email"
                          name="email"
                          className="form-control"
                        ></Field>

                      <ErrorMessage
                        name="email"
                        component="div"
                        className="text-danger"
                      />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                      <Form.Label>Password</Form.Label>
                        <Field
                          type={isPasswordVisible ? 'text' : 'password'}
                          placeholder="Password"
                          id="password"
                          name="password"                       
                          className="form-control"
                        />

                      <div className="password-area">
                        <i
                          className={`fas ${isPasswordVisible ? "fa-eye-slash" : "fa-eye"} password-toggle`}
                          onClick={togglePasswordVisibility}
                        ></i>
                      </div>
                                          
                      <ErrorMessage
                        name="password"
                        component="div"
                        className="text-danger"
                      />

                     
                    </Form.Group>
                    <Button variant="primary" type="submit">
                      Login
                    </Button>
                    <div className="or-text">
                      <h5>OR</h5>
                    </div>

                    {/* <GoogleLogin
                      onSuccess={handleGoogleSuccess}
                      onError={handleGoogleError}
                      useOneTap
                      flow="auth-code"
                    /> */}

                    <Button
                      type="button"
                      variant="unset"
                      className="google-btn"
                      onClick={handleGoogleLogin}
                    >
                      <img src={require("../assets/images/google-icon.png")} alt="google-icon" />
                      Continue with Google
                    </Button>
                    
                    <a href="javascript:void(0);" onClick={() => window.open(webUrl + "/forgot-password", "_blank")}>
                        <p> I forgot my password</p>
                    </a>
                    <h6>Don't have an account? 
                    <a href="javascript:void(0);" onClick={() => window.open(webUrl + "/sign-up", "_blank")}>
                        <span>Sign up</span>
                    </a>
                    </h6>
                  </FForm>
                )}
              </Formik>
            </div>
          </Col>
        </Row>
      </Container>
    </section>
  );
};

export default Login;

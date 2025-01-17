import React from 'react';
import * as Yup from 'yup';
import { Form as FForm, Field, Formik, ErrorMessage } from "formik";
import { Button, Form } from "react-bootstrap";
import { Link,useNavigate } from "react-router-dom";
import { apiService } from '../service/ApiService';
import Swal from 'sweetalert2';

const Signup = () => {
  const initialValues = { firstName: "", lastName: "", email: "", password: "", confirmPassword: "", role: "USER" };
  const navigate = useNavigate();
  const validationSchema = Yup.object({
    firstName: Yup.string().required('First Name is required'),
    lastName: Yup.string().required('Last Name is required'),
    email: Yup.string()
      .email('Email address is not valid')
      .required('Email is required'),
    password: Yup.string().max(10, 'Password max length is 10').required('Password is required'),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref('password'), null], 'Passwords must match')
      .required('Confirm Password is required'),
  });

  const handleSubmit = async(data) => {
    data.role = "USER"
    const res = await apiService.register(data);
    console.log("response is " ,res);
    if (res.status === 200) {
      localStorage.setItem("token",res.data.token)
      localStorage.setItem("user",JSON.stringify(res.data.user))
      Swal.fire('Success', 'Signup successful!', 'success').then(() => {
        navigate('/'); // Navigate to the home page after successful signup
      });
    } 
  };

  return (
    <div className="signup-container">
      <img
        src={require("../assets/demo.jpg")}
        alt="Background"
        className="fullscreen-image"
      />
      <div className="form-wrapper">
        <h2>Sign Up</h2>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {() => (
            <FForm>
              <Form.Group className="form-group">
                <Form.Label htmlFor="firstName">First Name</Form.Label>
                <Field
                  type="text"
                  placeholder="First Name"
                  id="firstName"
                  name="firstName"
                  className="form-control"
                />
                <ErrorMessage
                  name="firstName"
                  component="div"
                  className="text-danger"
                />
              </Form.Group>

              <Form.Group className="form-group">
                <Form.Label htmlFor="lastName">Last Name</Form.Label>
                <Field
                  type="text"
                  placeholder="Last Name"
                  id="lastName"
                  name="lastName"
                  className="form-control"
                />
                <ErrorMessage
                  name="lastName"
                  component="div"
                  className="text-danger"
                />
              </Form.Group>

              <Form.Group className="form-group">
                <Form.Label htmlFor="email">Email</Form.Label>
                <Field
                  type="email"
                  placeholder="Email"
                  id="email"
                  name="email"
                  className="form-control"
                />
                <ErrorMessage
                  name="email"
                  component="div"
                  className="text-danger"
                />
              </Form.Group>

              <Form.Group className="form-group">
                <Form.Label htmlFor="password">Password</Form.Label>
                <Field
                  type="password"
                  placeholder="Password"
                  id="password"
                  name="password"
                  className="form-control"
                />
                <ErrorMessage
                  name="password"
                  component="div"
                  className="text-danger"
                />
              </Form.Group>

              <Form.Group className="form-group">
                <Form.Label htmlFor="confirmPassword">Confirm Password</Form.Label>
                <Field
                  type="password"
                  placeholder="Confirm Password"
                  id="confirmPassword"
                  name="confirmPassword"
                  className="form-control"
                />
                <ErrorMessage
                  name="confirmPassword"
                  component="div"
                  className="text-danger"
                />
              </Form.Group>

              <Button type="submit" className="submit-button">Sign Up</Button>
              <p className="have-account-text">
                Already have an account? <Link to="/">Login</Link>
              </p>
            </FForm>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default Signup;

import React from 'react';
import * as Yup from 'yup';
import { Form as FForm, Field, Formik, ErrorMessage } from "formik";
import { Button, Form } from "react-bootstrap";
import { Link,useNavigate } from "react-router-dom";
import { apiService } from '../service/ApiService';
import Swal from 'sweetalert2';

const Login = () => {
  const initialValues = { email: "", password: "", role: "USER" };
  const navigate = useNavigate();
  const validationSchema = Yup.object({
    email: Yup.string()
      .email('Email address is not valid')
      .required('Email is required'),
    password: Yup.string().max(10, 'Password max length is 10').required('Password is required'),
  });

  const handleGoogleLogin = async () => {
      window.location.href = "http://localhost:8081/oauth2/authorization/google";
  };

  
  const handleGithubLogin = async () => {
      window.location.href = "http://localhost:8081/oauth2/authorization/github";
  };

  
  const handleSubmit = async(values) => {
    values.role = "USER"
    const res = await apiService.Login(values);
    console.log("response " , res)
    if(res.status === 200){
      localStorage.setItem("token",res.data.data.token)
      console.log("token " ,res.data.data.token )

      localStorage.setItem("user",JSON.stringify(res.data.user))
      Swal.fire('Success', 'Login successful!', 'success').then(() => {
      navigate('/home'); 
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
        <h2>Login</h2>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {() => (
            <FForm>
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

              <Button type="submit" className="submit-button">Login</Button>
              <Button onClick={handleGoogleLogin}>Login with Google</Button>
              <Button onClick={handleGithubLogin}>Login with Github</Button>
              <p className="have-account-text">
                 create account ?  <Link to="/signup">Sign up</Link>
              </p>
            </FForm>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default Login;

import React, { useState } from 'react';
import { Form as FForm, Field, Formik, ErrorMessage } from "formik";
import * as Yup from 'yup';

const Address = () => {
    const initialValues = {state : '',city:'', zipCode:'',name:'',phone:''}
    
    const validationSchema = Yup.object({
        state: Yup.string().required('State is required'),
        city: Yup.string().required('City is required'),
        zipCode: Yup.string().required('zip code is required'),
        name: Yup.string().required('name is required'),
        phone: Yup.string().required('phone is required'),
    });


    const handleSubmit = async (values) => {
        console.log("response is", values);
    };


    return (
        <div className="form-container">
            <h1>Add Address</h1>
            <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
            > 
            {() => (
            <FForm>
                 <div className="form-group">
                 <label htmlFor="state">State:</label>
                    <Field
                        type="text"
                        id="state"
                        name = "state"
                    />
                    <ErrorMessage
                        name="state"
                        component="div"
                        className="text-danger"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="city">City:</label>
                    <Field
                        type="text"
                        id="city"
                        name = "city"
                    />
                    <ErrorMessage
                        name="city"
                        component="div"
                        className="text-danger"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="zipCode">Zip Code:</label>
                    <Field
                        type="text"
                        id="zipCode"
                        name = "zipCode"
                    />
                    <ErrorMessage
                        name="zipCode"
                        component="div"
                        className="text-danger"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="name">Name:</label>
                    <Field
                        type="text"
                        id="name"
                        name = "name"
                    />
                    <ErrorMessage
                        name="name"
                        component="div"
                        className="text-danger"
                    />
                    
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Phone:</label>
                    <Field
                        type="tel"
                        id="phone"
                        name="phone"
                        
                    />
                    <ErrorMessage
                        name="phone"
                        component="div"
                        className="text-danger"
                    />
                </div>
                <button type="submit" className="submit-button">Submit</button>
            </FForm> 
        )}
        </Formik>
       </div>
    );
};

export default Address;

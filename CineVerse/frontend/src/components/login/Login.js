import React, { useState } from 'react';
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBRow,
  MDBCol,
  MDBInput,
  MDBCheckbox,
} from 'mdb-react-ui-kit';
import './Login.css';
import api from '../../api/axiosConfig';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState();
  const navigate = useNavigate();

  const handleLogin = async () => {
    const response = await api.post('/api/v1/login', { email: email, password: password });
    console.log(response.data);
    if (response.status == 200) {
      localStorage.setItem('token', response.data.token);
      navigate('/');
    } else {
      alert(response.data.message);
    }
  };

  return (
    <MDBContainer className="my-5">
      <MDBCard>
        <MDBRow className="g-0 d-flex align-items-center">
          <MDBCol md="4">
            <MDBCardImage
              src="https://mdbootstrap.com/img/new/ecommerce/vertical/004.jpg"
              alt="phone"
              className="rounded-t-5 rounded-tr-lg-0"
              fluid
            />
          </MDBCol>

          <MDBCol md="8">
            <MDBCardBody>
              <MDBInput
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
                wrapperClass="mb-4"
                label="Email address"
                id="form1"
                type="email"
              />
              <MDBInput
                onChange={(e) => {
                  setPassword(e.target.value);
                }}
                wrapperClass="mb-4"
                label="Password"
                id="form2"
                type="password"
              />

              <MDBBtn onClick={handleLogin} className="mb-4 w-100">
                Sign in
              </MDBBtn>
            </MDBCardBody>
          </MDBCol>
        </MDBRow>
      </MDBCard>
    </MDBContainer>
  );
};

export default Login;

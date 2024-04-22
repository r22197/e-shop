// SignupComponent.jsx
import React, { useState } from 'react';
import { register } from "../data/UserApi";
import { useNavigate } from 'react-router-dom';
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit';

const Register = () => {
    const [registerData, setRegisterData] = useState({ username: '', password: '', email: '' });
    const [error, setError] = useState('');
    const history = useNavigate();

    const handleSignup = async () => {
        try {
            const user = await register(registerData);
            console.log('Registered user:', user);
            localStorage.setItem('token', user.token);
            history('/');
        } catch (error) {
            console.error('Registration failed:', error.message);
            setError(error.response ? error.response.data : error.message);
        }
    };

    return (
        <div className="p-3">
            <h2 className="mb-4 text-center">Vytvořit účet</h2>
            {error && <p className="text-danger">{error}</p>}
            <MDBInput wrapperClass='mb-4' placeholder="Jméno" id='username' value={registerData.username} type='text'
                      onChange={(e) => setRegisterData({...registerData, username: e.target.value})}/>
            <MDBInput wrapperClass='mb-4' placeholder='Heslo' id='password' type='password'
                      value={registerData.password}
                      onChange={(e) => setRegisterData({...registerData, password: e.target.value})}/>
            <button className="mb-4 d-block btn-primary" style={{height: '50px', width: '100%'}}
                    onClick={handleSignup}>Zaregistrovat
            </button>

            <div className="text-center">
                <p>Už máš účet? <a href="/login">Přihlásit se</a></p>
            </div>
        </div>
    );
};

export default Register;

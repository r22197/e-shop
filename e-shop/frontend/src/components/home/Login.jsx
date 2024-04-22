// LoginComponent.jsx
import React, { useState } from 'react';
import { login } from "../data/UserApi";
import { useNavigate } from 'react-router-dom';
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit';

const Login = () => {
    const [loginData, setLoginData] = useState({ username: '', password: '' });
    const [error, setError] = useState('');
    const history = useNavigate();

    const handleLogin = async () => {
        try {
            const user = await login(loginData);
            console.log('Logged in user:', user);
            localStorage.setItem('token', user.token);
            history('/');
        } catch (error) {
            console.error('Login failed:', error.message);
            setError('Invalid username or password.');
        }
    };

    return (
        <MDBContainer className="p-3">
            <h2 className="mb-4 text-center">Přihlásit se</h2>
            {error && <p className="text-danger">{error}</p>}
            <MDBInput wrapperClass='mb-4' placeholder='Jméno' id='username' value={loginData.username} type='username' onChange={(e) => setLoginData({ ...loginData, username: e.target.value })} />
            <MDBInput wrapperClass='mb-4' placeholder='Heslo' id='password' type='password' value={loginData.password} onChange={(e) => setLoginData({ ...loginData, password: e.target.value })} />
            <button className="mb-4 d-block btn-primary" style={{ height:'50px',width: '100%' }} onClick={handleLogin}>Přihlásit</button>
            <div className="text-center">
                <p>Nemáš účet? <a href="/signup">Zaregistrovat se</a></p>
            </div>
        </MDBContainer>
    );
};

export default Login;

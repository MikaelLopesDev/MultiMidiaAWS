import React, { useState, useEffect } from 'react';
import authService from '../Services/authService';
import './Login.css';
import { useNavigate } from 'react-router-dom';

const Login = ({ onLogin }) => {
    const [credentials, setCredentials] = useState({ email: '', password: '' });
    const navigate = useNavigate();

    useEffect(() => {
        document.body.classList.add('login-page-body');
        return () => {
            document.body.classList.remove('login-page-body');
        };
    }, []);

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        authService.login(credentials)
            .then(response => {
                localStorage.setItem('user', JSON.stringify(response.data));
                onLogin();
                navigate('/dashboard');
            })
            .catch(err => {
                alert('Error logging in: ' + err.response.data.message);
            });
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-envelope"></i>
                        <input type="email" name="email" value={credentials.email} onChange={handleChange} placeholder="Email" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input type="password" name="password" value={credentials.password} onChange={handleChange} placeholder="Password" required />
                    </div>
                    <button type="submit" className="btn-login">Login</button>
                </form>
            </div>
        </div>
    );
};

export default Login;

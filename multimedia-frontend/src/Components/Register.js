import React, { useState, useEffect } from 'react';
import authService from '../Services/authService';
import './Register.css';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [form, setForm] = useState({
        fullName: '',
        username: '',
        password: '',
        email: '',
        profileImage: '',
        description: ''
    });
    const navigate = useNavigate();

    useEffect(() => {
        document.body.classList.add('register-page-body');
        return () => {
            document.body.classList.remove('register-page-body');
        };
    }, []);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        authService.register(form)
            .then(response => {
                alert('User registered successfully!');
                navigate('/login');
            })
            .catch(err => {
                alert('Error registering user: ' + err.response.data.message);
            });
    };

    return (
        <div className="register-container">
            <div className="register-box">
                <h2>Não tem cadastro ? Faça o seu agora</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="fullName" value={form.fullName} onChange={handleChange} placeholder="Full Name" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="username" value={form.username} onChange={handleChange} placeholder="Username" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="Password" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-envelope"></i>
                        <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="Email" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-image"></i>
                        <input type="text" name="profileImage" value={form.profileImage} onChange={handleChange} placeholder="Profile Image URL" />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-info-circle"></i>
                        <textarea name="description" value={form.description} onChange={handleChange} placeholder="Description"></textarea>
                    </div>
                    <button type="submit" className="btn-register">Confirmar</button>
                </form>
            </div>
        </div>
    );
};

export default Register;

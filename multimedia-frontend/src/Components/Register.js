import React, { useState, useEffect } from 'react';
import authService from '../Services/authService';
import './Register.css';
import { useNavigate, Link } from 'react-router-dom';  // Certifique-se de importar Link
import logo from '../Assets/nuvem-play-icon.png';

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
                <img src={logo} alt="Logo" className="login-logo" />  {/* Adicione esta linha */}
                <h2>Crie sua conta grátis</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="fullName" value={form.fullName} onChange={handleChange} placeholder="Nome Completo" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="username" value={form.username} onChange={handleChange} placeholder="Nome de Usuário" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="Senha" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-envelope"></i>
                        <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="Email" required />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-image"></i>
                        <input type="text" name="profileImage" value={form.profileImage} onChange={handleChange} placeholder="URL da Imagem de Perfil" />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-info-circle"></i>
                        <textarea name="description" value={form.description} onChange={handleChange} placeholder="Descrição"></textarea>
                    </div>
                    <button type="submit" className="btn-register">Confirmar</button>
                </form>
                {/* Adicionando o link para a tela de login */}
                <div className="login-link">
                    <Link to="/login">Fazer login</Link>
                </div>
            </div>
        </div>
    );
};

export default Register;

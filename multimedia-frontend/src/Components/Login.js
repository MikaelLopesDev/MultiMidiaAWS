import React, { useState, useEffect } from 'react';
import authService from '../Services/authService';
import './Login.css';
import { useNavigate, Link } from 'react-router-dom';
import logo from '../Assets/nuvem-play-icon.png';

const Login = ({ onLogin }) => {
    const [credentials, setCredentials] = useState({ email: '', password: '' });
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        document.body.classList.add('login-page-body');
        return () => {
            document.body.classList.remove('login-page-body');
        };
    }, []);

    const handleChange = (e) => {
        console.log(`Campo ${e.target.name} alterado para: ${e.target.value}`);
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Evita múltiplas submissões
        if (isSubmitting) return;

        setIsSubmitting(true);

        console.log('Tentando fazer login com as credenciais:', credentials);

        authService.login(credentials)
            .then(response => {
                console.log('Login bem-sucedido, resposta:', response);
                localStorage.setItem('user', JSON.stringify(response.data));
                onLogin();
                navigate('/dashboard');  // Navegação para o dashboard
            })
            .catch(err => {
                console.error('Erro ao tentar fazer login:', err);
                if (err.response && err.response.data && err.response.data.message) {
                    alert('Erro ao fazer login: ' + err.response.data.message);
                } else {
                    alert('Erro ao fazer login: Verifique sua conexão com a internet.');
                }
            })
            .finally(() => {
                setIsSubmitting(false);  // Permite submissões novamente
            });
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <img src={logo} alt="Logo" className="login-logo" />
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-envelope"></i>
                        <input 
                            type="email" 
                            name="email" 
                            value={credentials.email} 
                            onChange={handleChange} 
                            placeholder="Email" 
                            required 
                        />
                    </div>
                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input 
                            type="password" 
                            name="password" 
                            value={credentials.password} 
                            onChange={handleChange} 
                            placeholder="Password" 
                            required 
                        />
                    </div>
                    <div className="register-link">
                        <Link to="/register">Cadastre-se</Link>
                    </div>
                    <button type="submit" className="btn-login" disabled={isSubmitting}>
                        {isSubmitting ? 'Entrando...' : 'Confirmar'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;

import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, useLocation, useNavigate } from 'react-router-dom';
import Register from './Components/Register';
import Login from './Components/Login';
import Dashboard from './Components/Dashboard';
import Navbar from './Components/Navbar';
import Home from './Components/Home';
import authService from './Services/authService';

const App = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const user = authService.getCurrentUser();
        if (user) {
            setIsAuthenticated(true);
        } else {
            setIsAuthenticated(false);
        }
    }, []);

    const handleLogin = () => {
        setIsAuthenticated(true);
    };

    const handleLogout = () => {
        authService.logout();
        setIsAuthenticated(false);
        navigate('/');  // Redireciona para a página inicial após o logout
    };

    // Mostrar a Navbar em todas as rotas, exceto em /dashboard
    const shouldShowNavbar = location.pathname !== '/dashboard';

    return (
        <>
            {shouldShowNavbar && <Navbar />}
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login onLogin={handleLogin} />} />
                
                {/* Protege a rota /dashboard */}
                <Route 
                    path="/dashboard" 
                    element={isAuthenticated ? 
                        <Dashboard onLogout={handleLogout} /> : 
                        <Navigate to="/login" replace />} 
                />
            </Routes>
        </>
    );
};

const AppWrapper = () => (
    <Router>
        <App />
    </Router>
);

export default AppWrapper;

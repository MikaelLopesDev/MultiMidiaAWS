import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Register from './Components/Register';
import Login from './Components/Login';
import Dashboard from './Components/Dashboard';
import Navbar from './Components/Navbar';
import Home from './Components/Home';
import authService from './Services/authService';
import EditUser from "./Components/EditUser";

const App = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        // Deslogar o usuário na inicialização
        authService.logout();

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
    };

    return (
        <Router>
            {!isAuthenticated && <Navbar />}
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/register" element={<Register />} />
                <Route path="/edit-profile" element={<EditUser />} />
                <Route path="/login" element={<Login onLogin={handleLogin} />} />
                <Route path="/dashboard" element={<Dashboard onLogout={handleLogout} />} />
            </Routes>
        </Router>
    );
};

export default App;

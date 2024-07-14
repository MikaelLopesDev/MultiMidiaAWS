import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Adicionando o CSS para estilização

const Navbar = () => {
    return (
        <nav className="navbar">
            <Link to="/" className="nav-link">Home</Link>
            <Link to="/login" className="nav-link">Login</Link>
            <Link to="/register" className="nav-link">Cadastre-se</Link>
        </nav>
    );
};

export default Navbar;

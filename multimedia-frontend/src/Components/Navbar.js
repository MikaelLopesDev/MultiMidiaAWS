import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
    const location = useLocation();
    const isHome = location.pathname === '/';
    const isCentered = location.pathname === '/login' || location.pathname === '/register';

    return (
        <nav className={`navbar ${isHome ? 'navbar-right' : ''} ${isCentered ? 'navbar-center' : ''}`}>
            <ul className="navbar-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/login">Login</Link></li>
                <li><Link to="/register">Register</Link></li>
            </ul>
        </nav>
    );
};

export default Navbar;

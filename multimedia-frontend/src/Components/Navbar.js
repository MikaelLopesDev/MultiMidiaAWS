import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';
import logo from '../Assets/nuvem-play-icon.png';

const Navbar = () => {
    const location = useLocation();
    const currentPath = location.pathname;
    return (
        <nav className="navbar">
            <div className="navbar-left">
                <Link to="/">
                    <img src={logo} alt="Logo" className="navbar-logo"/>
                </Link>
                MediaCloud
            </div>
            <div className="navbar-right">
                <ul className="navbar-links">
                    {currentPath !== '/login' && (
                        <li><Link to="/login">Login</Link></li>
                    )}
                    {currentPath !== '/register' && (
                        <li><Link to="/register">Cadastrar</Link></li>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;

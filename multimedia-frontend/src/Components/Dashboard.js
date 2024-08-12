import React, { useState, useEffect } from 'react';
import MediaGallery from './MediaGallery';
import UploadMedia from './UploadMedia';
import Profile from './Profile';
import ViewMedia from './ViewMedia';
import './Dashboard.css';
import authService from '../Services/authService';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const [user, setUser] = useState(null);
    const [currentView, setCurrentView] = useState('viewMedia'); // 'profile', 'viewMedia', 'uploadMedia'
    const navigate = useNavigate();

    useEffect(() => {
        const loggedInUser = authService.getCurrentUser();
        if (loggedInUser) {
            authService.getProfile(loggedInUser.id)
                .then(response => {
                    setUser(response.data);
                })
                .catch(err => {
                    console.error('Failed to fetch profile data:', err);
                });
        }
    }, []);

    const handleLogout = () => {
        authService.logout();
        navigate('/'); // Redireciona para a tela Home após o logout
    };

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <div className="dashboard-header-logo">
                    <img src={require('../Assets/nuvem-play-icon.png')} alt="MediaCloud Logo" />
                </div>
                <div className="dashboard-header-text">
                    <h1>Bem-vindo ao seu Dashboard, {user?.username}!</h1>
                </div>
                <div className="dashboard-buttons">
                    <button onClick={() => setCurrentView('profile')}>Meu Perfil</button>
                    <button onClick={() => setCurrentView('viewMedia')}>Minhas Mídias</button>
                    <button onClick={() => setCurrentView('uploadMedia')}>Upload de Mídia</button>
                    <button onClick={handleLogout}>Logout</button>
                </div>
            </header>
            <div className="dashboard-content">
                {currentView === 'profile' && <Profile user={user} />}
                {currentView === 'viewMedia' && <ViewMedia userId={user?.id} />}
                {currentView === 'uploadMedia' && <UploadMedia userId={user?.id} onUploadSuccess={() => setCurrentView('viewMedia')} />}
            </div>
        </div>
    );
};

export default Dashboard;

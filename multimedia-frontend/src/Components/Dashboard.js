import React, { useEffect, useState } from 'react';
import authService from '../Services/authService';
import './Dashboard.css';
import { useNavigate } from 'react-router-dom';

const Dashboard = ({ onLogout }) => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const currentUser = authService.getCurrentUser();
        if (!currentUser) {
            navigate('/login');
        } else {
            setUser(currentUser);
        }
    }, [navigate]);

    if (!user) {
        return <div>Loading...</div>;
    }

    const handleLogout = () => {
        onLogout();
        navigate('/login');
    };

    return (
        <div className="dashboard-container">
            <div className="profile-section">
                <img src={user.profileImage} alt="Profile" className="profile-image" />
                <h2>{user.fullName}</h2>
                <p>{user.email}</p>
                <p>{user.description}</p>
            </div>
            <div className="media-library">
                <h3>Sua Biblioteca de Mídia</h3>
                <div className="media-items">
                    <div className="media-item">
                        <i className="fas fa-video"></i>
                        <p>Vídeos</p>
                    </div>
                    <div className="media-item">
                        <i className="fas fa-music"></i>
                        <p>Músicas</p>
                    </div>
                    <div className="media-item">
                        <i className="fas fa-image"></i>
                        <p>Fotos</p>
                    </div>
                </div>
            </div>
            <div className="shortcuts-section">
                <h3>Atalhos</h3>
                <button onClick={() => navigate('/upload')}>Adicionar Novo Conteúdo</button>
                <button onClick={() => navigate('/edit-profile')}>Editar Perfil</button>
            </div>
            <div className="activities-feed">
                <h3>Atividades Recentes</h3>
                <ul>
                    <li>Upload de vídeo: Vacation.mp4</li>
                    <li>Adicionada nova música: Favorite Song.mp3</li>
                    <li>Atualização do perfil</li>
                </ul>
            </div>
            <div className="settings-section">
                <h3>Configurações</h3>
                <button onClick={handleLogout}>Logout</button>
            </div>
        </div>
    );
};

export default Dashboard;

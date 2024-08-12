import React, { useState, useEffect } from 'react';
import authService from '../Services/authService';
import './Profile.css';

const Profile = ({ user }) => {
    const [profileData, setProfileData] = useState(user || {});
    const [editing, setEditing] = useState(false);

    const handleChange = (e) => {
        setProfileData({ ...profileData, [e.target.name]: e.target.value });
    };

    const handleSave = () => {
        // Chame um serviço para salvar as alterações do perfil
        console.log('Salvar alterações:', profileData);
        setEditing(false);
    };

    return (
        <div className="profile-container">
            <h2>Meu Perfil</h2>
            {editing ? (
                <div className="profile-edit-form">
                    <label>Nome Completo:</label>
                    <input 
                        type="text" 
                        name="fullName" 
                        value={profileData.fullName} 
                        onChange={handleChange} 
                    />
                    <label>Nome de Usuário:</label>
                    <input 
                        type="text" 
                        name="username" 
                        value={profileData.username} 
                        onChange={handleChange} 
                    />
                    <label>Email:</label>
                    <input 
                        type="email" 
                        name="email" 
                        value={profileData.email} 
                        onChange={handleChange} 
                    />
                    <label>Descrição:</label>
                    <textarea 
                        name="description" 
                        value={profileData.description} 
                        onChange={handleChange} 
                    />
                    <button onClick={handleSave}>Salvar</button>
                </div>
            ) : (
                <div className="profile-info">
                    <p><strong>Nome Completo:</strong> {profileData.fullName}</p>
                    <p><strong>Nome de Usuário:</strong> {profileData.username}</p>
                    <p><strong>Email:</strong> {profileData.email}</p>
                    <p><strong>Descrição:</strong> {profileData.description}</p>
                    <button onClick={() => setEditing(true)}>Editar Perfil</button>
                </div>
            )}
        </div>
    );
};

export default Profile;

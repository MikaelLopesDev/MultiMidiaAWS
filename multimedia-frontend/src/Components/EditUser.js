import React, {useEffect, useState} from "react";
import './EditUser.css'
import authService from "../Services/authService";
import { useNavigate } from 'react-router-dom';

const EditUser = () => {

    const [form, setForm] = useState({
        fullName: '',
        username: '',
        password: '',
        email: '',
        profileImage: '',
        description: ''
    });

    useEffect(() => {
        // Fetch user data when the component mounts
        fetchUserData();
    }, []);

    const fetchUserData = async () => {
        try {
            const response = await fetch(`/users/${authService.getCurrentUser().id}`);
            const data = await response.json();
            setForm({
                fullName: data.fullName,
                username: data.username,
                description: data.description,
                profileImage: data.profileImage,
                email: data.email,
                password: data.password,
            });
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    function handleChange(event) {
        const { name, value } = event.target;
        setForm((prevForm) => ({
            ...prevForm,
            [name]: value
        }));
    }

    const navigate = useNavigate();

    function handleSubmit(f) {
        f.preventDefault();
        form.email = authService.getCurrentUser().email;
        form.password = authService.getCurrentUser().password;
        authService.updateUser(authService.getCurrentUser().id, form).then(response => {
            alert('User updated successfully!');
            navigate('/dashboard');
        })
            .catch(err => {
                alert('Error updating user: ' + err.response.data.message);
            });
    }

    return (
        <div className="edit-container">
            <div className="edit-box">
                <h2>Editar usuário</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="fullName" value={form.fullName} onChange={handleChange}
                               placeholder="Full Name" required/>
                    </div>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="username" value={form.username} onChange={handleChange}
                               placeholder="Username" required/>
                    </div>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="description" value={form.description} onChange={handleChange}
                               placeholder="Decription" required/>
                    </div>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input type="text" name="profileImage" value={form.profileImage} onChange={handleChange}
                               placeholder="Profile Image" required/>
                    </div>
                    <button type="submit" className="btn-register">Confirmar</button>
                </form>
            </div>
        </div>
    );
}

export default EditUser;
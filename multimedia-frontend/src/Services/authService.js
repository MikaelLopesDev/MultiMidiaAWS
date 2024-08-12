import axios from 'axios';

const API_URL = 'http://3.22.248.41:8080/users';

const register = (user) => {
    return axios.post(API_URL, user);
};

const login = (credentials) => {
    return axios.post(`${API_URL}/login`, credentials);
};

const logout = () => {
    localStorage.removeItem('user');
};

const getCurrentUser = () => {
    const userStr = localStorage.getItem('user');
    if (!userStr) {
        return null;
    }
    try {
        return JSON.parse(userStr);
    } catch (e) {
        console.error('Error parsing user data:', e);
        return null;
    }
};

// Novo método para obter os dados do perfil
const getProfile = (userId) => {
    return axios.get(`${API_URL}/${userId}`);
};

const authService = {
    register,
    login,
    logout,
    getCurrentUser,
    getProfile  // Adiciona o novo método aqui
};

export default authService;

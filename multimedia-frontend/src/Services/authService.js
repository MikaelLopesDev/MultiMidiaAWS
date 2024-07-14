import axios from 'axios';

const API_URL = 'http://3.239.184.73:8080/users';

const register = (user) => {
    return axios.post(API_URL, user);
};

const login = (credentials) => {
    return axios.post(`${API_URL}/login`, credentials);
};

const loginWithUsername = (credentials) => {
    return axios.post(`${API_URL}/login/name`, credentials);
};

const logout = () => {
    localStorage.removeItem('user');
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem('user'));
};

const authService = {
    register,
    login,
    loginWithUsername,
    logout,
    getCurrentUser
};

export default authService;

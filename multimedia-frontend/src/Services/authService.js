import axios from 'axios';

const API_URL = 'http://3.86.238.87:8080/users';

const register = (user) => {
    return axios.post(API_URL, user);
};

const updateUser = (id, userData) => {
  return axios.put(`${API_URL}/${id}`, userData)
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
    getCurrentUser,
    updateUser
};

export default authService;

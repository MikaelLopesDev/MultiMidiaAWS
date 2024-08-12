import axios from 'axios';
import authService from './authService';

const MEDIA_API_URL = 'http://3.22.248.41:8080'; // Use sua URL de API

const getUserMedias = (idUser) => {
    return axios.get(`${MEDIA_API_URL}/users/data/${idUser}`);
};

const uploadImage = (mediaData) => {
    const token = authService.getCurrentUser()?.token;

    return axios.post(`${MEDIA_API_URL}/images`, mediaData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': `Bearer ${token}`
        }
    });
};

const uploadVideo = (mediaData) => {
    const token = authService.getCurrentUser()?.token;

    return axios.post(`${MEDIA_API_URL}/videos`, mediaData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': `Bearer ${token}`
        }
    });
};

const getMediaById = (mediaId) => {
    return axios.get(`${MEDIA_API_URL}/images/${mediaId}`);
};

const updateMedia = (mediaId, mediaData) => {
    return axios.put(`${MEDIA_API_URL}/images/${mediaId}`, mediaData);
};

const deleteMedia = (mediaId) => {
    return axios.delete(`${MEDIA_API_URL}/images/${mediaId}`);
};

const mediaService = {
    getUserMedias,
    uploadImage,
    uploadVideo,
    getMediaById,
    updateMedia,
    deleteMedia
};

export default mediaService;

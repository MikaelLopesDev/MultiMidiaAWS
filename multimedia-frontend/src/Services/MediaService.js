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

const deleteMedia = (mediaId, mediaType) => {
    const token = authService.getCurrentUser()?.token;

    let deleteUrl = `${MEDIA_API_URL}/images/${mediaId}`;
    if (mediaType === 'video') {
        deleteUrl = `${MEDIA_API_URL}/videos/${mediaId}`;
    } else if (mediaType === 'audio') {
        deleteUrl = `${MEDIA_API_URL}/audios/${mediaId}`;
    }

    return axios.delete(deleteUrl, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
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

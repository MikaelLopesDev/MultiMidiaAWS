import React, { useEffect, useState } from 'react';
import mediaService from '../Services/MediaService';
import MediaCard from './MediaCard';
import './ViewMedia.css';

const ViewMedia = ({ userId }) => {
    const [medias, setMedias] = useState([]);
    const [filteredMedias, setFilteredMedias] = useState([]);
    const [mediaType, setMediaType] = useState('all'); // Tipo de mídia selecionada
    const [searchTerm, setSearchTerm] = useState(''); // Termo de pesquisa

    useEffect(() => {
        mediaService.getUserMedias(userId)
            .then(response => {
                setMedias(response.data);
                setFilteredMedias(response.data); // Inicialmente mostra todas as mídias
            })
            .catch(err => console.error('Failed to fetch medias:', err));
    }, [userId]);

    // Função para filtrar as mídias por tipo
    const filterMedias = (type) => {
        setMediaType(type);
        if (type === 'all') {
            setFilteredMedias(medias);
        } else {
            setFilteredMedias(medias.filter(media => media.mime.includes(type)));
        }
    };

    // Função para lidar com a pesquisa
    const handleSearch = (e) => {
        const term = e.target.value.toLowerCase();
        setSearchTerm(term);
        setFilteredMedias(medias.filter(media => media.fileName.toLowerCase().includes(term)));
    };

    const handleDelete = (mediaId) => {
        // Atualiza a lista de mídias após uma exclusão
        const updatedMedias = medias.filter(media => media.id !== mediaId);
        setMedias(updatedMedias);
        setFilteredMedias(updatedMedias);
    };

    return (
        <div className="view-media-container">
            <h2>Minhas Mídias</h2>
            <div className="media-navigation">
                <button onClick={() => filterMedias('all')}>Todas</button>
                <button onClick={() => filterMedias('image')}>Imagens</button>
                <button onClick={() => filterMedias('video')}>Vídeos</button>
                <button onClick={() => filterMedias('audio')}>Áudios</button>
                <button onClick={() => filterMedias('application')}>Arquivos</button>
            </div>
            <div className="media-search">
                <input 
                    type="text" 
                    placeholder="Pesquisar por nome..." 
                    value={searchTerm} 
                    onChange={handleSearch} 
                />
            </div>
            <div className="media-gallery">
                {filteredMedias.map(media => (
                    <MediaCard key={media.id} media={media} onDelete={handleDelete} />
                ))}
            </div>
        </div>
    );
};

export default ViewMedia;

import React from 'react';
import './MediaCard.css';

const MediaCard = ({ media }) => {
    // Usando a URL completa retornada pelo backend para exibir a imagem
    const imageUrl = media.url;

    console.log("Generated Image URL:", imageUrl);  // Log para verificar a URL gerada

    return (
        <div className="media-card">
            <img 
                src={imageUrl} 
                alt={media.description} 
                className="media-image" 
                onError={(e) => { 
                    console.log("Erro ao carregar imagem:", e); 
                    e.target.onerror = null; 
                    e.target.src="https://via.placeholder.com/150";  // URL fixa para fallback
                }} 
            />
            <div className="media-info">
                <h3>{media.description}</h3>
                <p>Tags: {media.tags}</p>
            </div>
        </div>
    );
};

export default MediaCard;

import React from 'react';
import MediaCard from './MediaCard';
import './MediaGallery.css';

const MediaGallery = ({ medias }) => {
    return (
        <div className="media-gallery">
            {medias.map(media => (
                <MediaCard key={media.id} media={media} />
            ))}
        </div>
    );
};

export default MediaGallery;

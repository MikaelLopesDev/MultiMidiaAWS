import React, { useState, useEffect } from 'react';
import './MediaCard.css';
import holdVideoImage from '../Assets/hold-video.png';  // Substitua pelo caminho correto do seu asset
import holdAudioImage from '../Assets/hold-audio.png';  // Substitua pelo caminho correto do seu asset
import mediaService from '../Services/MediaService'; // Importe o serviço de mídia

const MediaCard = ({ media, onDelete }) => {
    const [showModal, setShowModal] = useState(false);

    const mediaUrl = media.url;
    debugger
    const thumbnailUrl = media.thumbnailUrl || media.url; // Usa o thumbnail fornecido pelo backend, ou a própria URL da mídia se não houver thumbnail

    const isImage = media.mime.includes('JPEG') || media.mime.includes('PNG');
    const isVideo = media.mime.includes('MP4') || media.mime.includes('QUICKTIME');
    const isAudio = media.mime.includes('MP3') || media.mime.includes('WAV');

    // Imprime o thumbnailUrl no console
    console.log("Thumbnail URL:", thumbnailUrl);

    const handleCardClick = () => {
        setShowModal(true);
    };

    const handleCloseModal = (e) => {
        e.stopPropagation(); // Evita que o clique no botão de fechar feche e abra o modal ao mesmo tempo
        setShowModal(false);
    };

    const handleEscKey = (e) => {
        if (e.key === 'Escape') {
            setShowModal(false);
        }
    };

    const handleDelete = (e) => {
        e.stopPropagation(); // Evita que o modal abra ao clicar na lixeira
        const mediaType = isVideo ? 'video' : isAudio ? 'audio' : 'image';
        if (window.confirm('Tem certeza que deseja excluir esta mídia?')) {
            mediaService.deleteMedia(media.id, mediaType)
                .then(() => {
                    alert('Mídia excluída com sucesso!');
                    if (onDelete) {
                        onDelete(media.id); // Chama a função de callback para atualizar a lista de mídias
                    }
                })
                .catch(err => {
                    console.error('Erro ao excluir mídia:', err);
                    alert('Erro ao excluir mídia. Tente novamente.');
                });
        }
    };

    useEffect(() => {
        if (showModal) {
            document.addEventListener('keydown', handleEscKey);
        } else {
            document.removeEventListener('keydown', handleEscKey);
        }

        return () => {
            document.removeEventListener('keydown', handleEscKey);
        };
    }, [showModal]);

    return (
        <div className="media-card" onClick={handleCardClick}>
            <div className="delete-icon" onClick={handleDelete}>
                🗑️
            </div>
            {isImage && (
                <img
                    src={thumbnailUrl}
                    alt={media.description}
                    className="media-thumbnail"
                    onError={(e) => {
                        console.log("Erro ao carregar imagem:", e);
                        e.target.onerror = null;
                        e.target.src = "https://via.placeholder.com/150";
                    }}
                />
            )}
            {isVideo && (
                <img
                    src={thumbnailUrl}
                    alt={media.description}
                    className="media-thumbnail"
                    onError={(e) => {
                        console.log("Erro ao carregar vídeo:", e);
                        e.target.onerror = null;
                        e.target.src = "https://via.placeholder.com/150";
                    }}
                />
            )}
            {isAudio && (
                <img
                    src={holdAudioImage}
                    alt={media.description}
                    className="media-thumbnail"
                    onError={(e) => {
                        console.log("Erro ao carregar áudio:", e);
                        e.target.onerror = null;
                        e.target.src = "https://via.placeholder.com/150";
                    }}
                />
            )}
            <div className="media-info">
                <h3>{media.description}</h3>
                <p>Tags: {media.tags}</p>
            </div>

            {showModal && (
                <div className="media-modal" onClick={handleCloseModal}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <span className="close-button" onClick={handleCloseModal}>&times;</span>
                        {isImage && (
                            <>
                                <img src={mediaUrl} alt={media.description} className="media-full" />
                                <a href={mediaUrl} download className="download-button">Download</a>
                            </>
                        )}
                        {isVideo && (
                            <video controls className="media-full">
                                <source src={mediaUrl} type="video/mp4" />
                                Seu navegador não suporta a reprodução deste vídeo.
                            </video>
                        )}
                        {isAudio && (
                            <audio controls className="media-full">
                                <source src={mediaUrl} type="audio/mp3" />
                                Seu navegador não suporta a reprodução deste áudio.
                            </audio>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default MediaCard;

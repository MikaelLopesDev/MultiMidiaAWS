import React, { useState } from 'react';
import mediaService from '../Services/MediaService';
import './UploadMedia.css';

const UploadMedia = ({ userId, onUploadSuccess }) => {
    const [media, setMedia] = useState(null);
    const [description, setDescription] = useState('');
    const [tags, setTags] = useState('');
    const [genre, setGenre] = useState(''); // Novo campo para o gênero
    const [isUploading, setIsUploading] = useState(false);

    const handleFileChange = (e) => {
        console.log(e.target.files[0]); // Verifica se o arquivo está sendo capturado
        setMedia(e.target.files[0]);
    };

    const handleUpload = (e) => {
        e.preventDefault();

        // Evita duplicação de upload
        if (isUploading) return;

        setIsUploading(true); // Desabilita o botão de upload para evitar cliques múltiplos

        console.log('Iniciando upload:', { media, userId });

        if (!media || !userId) {
            alert('Arquivo ou ID do usuário não encontrado!');
            setIsUploading(false); // Habilita o botão novamente
            return;
        }

        const formData = new FormData();
        formData.append('file', media);
        formData.append('ownerId', userId);
        formData.append('description', description);
        formData.append('tags', tags);
        
        // Formatando a data sem os milissegundos
        const uploadDate = new Date().toISOString().slice(0, 19); // Corta a parte dos milissegundos
        formData.append('uploadDate', uploadDate);

        const fileType = media.type.split('/')[0]; // 'image' ou 'video'
        let uploadFunction;

        if (fileType === 'image') {
            uploadFunction = mediaService.uploadImage;
        } else if (fileType === 'video') {
            if (!genre) {
                alert('Por favor, selecione o gênero do vídeo!');
                setIsUploading(false);
                return;
            }
            formData.append('genre', genre); // Adiciona o gênero ao FormData
            uploadFunction = mediaService.uploadVideo;
        } else {
            alert('Tipo de arquivo não suportado!');
            setIsUploading(false);
            return;
        }

        uploadFunction(formData)
            .then(() => {
                alert('Mídia enviada com sucesso!');
                onUploadSuccess();
            })
            .catch(err => {
                console.error('Falha ao enviar mídia:', err);
                console.error('Erro completo:', err.response ? err.response.data : 'Erro de rede');
            })
            .finally(() => {
                setIsUploading(false); // Habilita o botão após conclusão do upload
            });
    };

    return (
        <div className="upload-media">
            <h2>Upload de Mídia</h2>
            <form onSubmit={handleUpload}>
                <input type="file" onChange={handleFileChange} required />
                <input 
                    type="text" 
                    placeholder="Descrição" 
                    value={description} 
                    onChange={(e) => setDescription(e.target.value)} 
                    required 
                />
                <input 
                    type="text" 
                    placeholder="Tags (separadas por vírgula)" 
                    value={tags} 
                    onChange={(e) => setTags(e.target.value)} 
                    required 
                />
                {media?.type.split('/')[0] === 'video' && (
                    <select value={genre} onChange={(e) => setGenre(e.target.value)} required>
                        <option value="">Selecione o gênero</option>
                        <option value="MUSIC">Música</option>
                        <option value="PODCAST">Podcast</option>
                        <option value="AUDIO_BOOK">Audiobook</option>
                        <option value="SOUNDEFFECT">Efeito Sonoro</option>
                        <option value="OTHER">Outro</option>
                        <option value="FILM">Filme</option>
                        <option value="TV_SERIES">Série de TV</option>
                        <option value="TUTORIAL">Tutorial</option>
                        <option value="INTERVIEW">Entrevista</option>
                    </select>
                )}
                <button type="submit" disabled={isUploading}>
                    {isUploading ? 'Enviando...' : 'Enviar'}
                </button>
            </form>
        </div>
    );
};

export default UploadMedia;

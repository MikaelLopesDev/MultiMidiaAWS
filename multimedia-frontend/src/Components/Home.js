import React from 'react';
import './Home.css';
import ufpiImage from '../Assets/ufpi.jpg'; // Certifique-se de que a imagem está localizada corretamente

const Home = () => {
    return (
        <div className="home-container">
            <header className="hero-section">
                <img src={ufpiImage} alt="UFPI" className="hero-image" />
                <h1>Bem-vindo ao MultiMídia AWS</h1>
                <p>Armazene seus vídeos, músicas, fotos e muito mais com poucos cliques.</p>
                <p>Grande capacidade de armazenamento por um ótimo preço.</p>
                <p>De bônus RECEBA a aprovação na disciplina de Tópicos de Engenharia de Software.</p>
            </header>
            <section className="features-section">
                <div className="feature">
                    <i className="fas fa-video"></i>
                    <h2>Vídeos</h2>
                    <p>Armazene e compartilhe seus vídeos favoritos.</p>
                </div>
                <div className="feature">
                    <i className="fas fa-music"></i>
                    <h2>Músicas</h2>
                    <p>Salve suas músicas preferidas com facilidade.</p>
                </div>
                <div className="feature">
                    <i className="fas fa-images"></i>
                    <h2>Fotos</h2>
                    <p>Guarde suas memórias preciosas com segurança.</p>
                </div>
                <div className="feature">
                    <i className="fas fa-cloud"></i>
                    <h2>Armazenamento</h2>
                    <p>Espaço abundante para todos os seus arquivos.</p>
                </div>
            </section>
            <footer className="footer">
                <h2>Made by Grupo 4:</h2>
                <p>JOÃO VICTOR SANTOS ANDRADE - 20199005767</p>
                <p>MATHEUS VINICIUS LINHARES LEMOS DE OLIVEIRA - 20199013606</p>
                <p>ISAAC AUGUSTO SANTANA BRITO - 20189043610</p>
                <p>MIKAEL DA SILVA LOPES - 20199039120</p>
                <p>ALEXANDRE JOSÉ CANTUÁRIA MONTEIRO ROSA FILHO - 20199011094</p>
                <p>SAMUEL SILVESTRE SILVA BATISTA - 20199053773</p>
            </footer>
        </div>
    );
};

export default Home;

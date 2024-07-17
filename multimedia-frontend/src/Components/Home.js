import React from 'react';
import './Home.css';
import logo from '../Assets/media-cloud-logo.png'; // Certifique-se de que a imagem está na pasta assets

const Home = () => {
    return (
        <div className="home-container">
            <div className="hero-section">
                <div className="hero-text">
                    <h1>Bem-vindo ao MediaCloud</h1>
                    <p>Armazene seus vídeos, músicas, fotos e muito mais com poucos cliques.</p>
                    <p>Oferecemos uma grande quantidade de armazenamento por um ótimo preço.</p>
                    <p>Assine a plataforma e RECEBA a aprovação na disciplina de tópicos de engenharia de software.</p>
                    <div className="hero-buttons">
                        <a href="/register" className="button">Experimente grátis</a>
                        <a href="/pricing" className="button button-secondary">Comprar agora</a>
                    </div>
                </div>
                <div className="hero-image-container">
                    <img src={logo} alt="MediaCloud Service" className="hero-image" />
                </div>
            </div>
            <div className="features-section">
                <div className="feature">
                    <i className="fas fa-video"></i>
                    <h2>Vídeos</h2>
                    <p>Armazene e organize seus vídeos.</p>
                </div>
                <div className="feature">
                    <i className="fas fa-music"></i>
                    <h2>Músicas</h2>
                    <p>Gerencie suas músicas favoritas.</p>
                </div>
                <div className="feature">
                    <i className="fas fa-image"></i>
                    <h2>Fotos</h2>
                    <p>Guarde suas memórias em fotos.</p>
                </div>
            </div>
            <div className="pricing-section">
                <h2>Planos e Preços</h2>
                <div className="pricing-cards">
                    <div className="pricing-card">
                        <h3>Plano Básico</h3>
                        <p>R$ 19,99/mês</p>
                        <p>100 GB de Armazenamento</p>
                        <p>Suporte 24/7</p>
                    </div>
                    <div className="pricing-card">
                        <h3>Plano Padrão</h3>
                        <p>R$ 39,99/mês</p>
                        <p>500 GB de Armazenamento</p>
                        <p>Suporte 24/7</p>
                        <p>Acesso a funcionalidades avançadas</p>
                    </div>
                    <div className="pricing-card">
                        <h3>Plano Premium</h3>
                        <p>R$ 59,99/mês</p>
                        <p>2 TB de Armazenamento</p>
                        <p>Suporte 24/7</p>
                        <p>Funcionalidades exclusivas</p>
                    </div>
                </div>
            </div>
            <div className="footer">
                <p>Made by Grupo 4:</p>
                <p>JOÃO VICTOR SANTOS ANDRADE - 20199005767</p>
                <p>MATHEUS VINICIUS LINHARES LEMOS DE OLIVEIRA - 20199013606</p>
                <p>ISAAC AUGUSTO SANTANA BRITO - 20189043610</p>
                <p>MIKAEL DA SILVA LOPES - 20199039120</p>
                <p>ALEXANDRE JOSÉ CANTUÁRIA MONTEIRO ROSA FILHO - 20199011094</p>
                <p>SAMUEL SILVESTRE SILVA BATISTA - 20199053773</p>
            </div>
        </div>
    );
};

export default Home;

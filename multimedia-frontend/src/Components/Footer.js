import React from 'react';
import './Footer.css';
import logo from '../Assets/nuvem-play-icon.png'; // Certifique-se de que o caminho está correto

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-content">
                <div className="footer-logo-section">
                    <img src={logo} alt="MediaCloud Logo" className="footer-logo" />
                    <p className="footer-company-name">MediaCloud</p>
                    <p>Serviços em nuvem criptografados pelo usuário</p>
                    <p>© MediaCloud 2024. Todos os direitos reservados</p>
                    <div className="footer-socials">
                        <a href="#"><i className="fab fa-facebook-f"></i></a>
                        <a href="#"><i className="fab fa-instagram"></i></a>
                        <a href="#"><i className="fab fa-twitter"></i></a>
                        <a href="#"><i className="fab fa-linkedin-in"></i></a>
                        <a href="#"><i className="fab fa-github"></i></a>
                    </div>
                </div>
                <div className="footer-column">
                    <h3>Produtos</h3>
                    <ul>
                        <li><a href="#">Armazenamento em nuvem</a></li>
                        <li><a href="#">Object storage</a></li>
                        <li><a href="#">Sincronizar</a></li>
                        <li><a href="#">Backup</a></li>
                        <li><a href="#">Compartilhar</a></li>
                        <li><a href="#">Chats e reuniões</a></li>
                        <li><a href="#">Business</a></li>
                    </ul>
                </div>
                <div className="footer-column">
                    <h3>Plataformas</h3>
                    <ul>
                        <li><a href="#">Aplicativos móveis</a></li>
                        <li><a href="#">Aplicativo para desktop</a></li>
                        <li><a href="#">Extensões de navegador</a></li>
                        <li><a href="#">CMD</a></li>
                        <li><a href="#">CMD para NAS</a></li>
                    </ul>
                </div>
                <div className="footer-column">
                    <h3>Recursos</h3>
                    <ul>
                        <li><a href="#">Blog</a></li>
                        <li><a href="#">Segurança e privacidade</a></li>
                        <li><a href="#">Confiabilidade</a></li>
                        <li><a href="#">Programadores</a></li>
                        <li><a href="#">Revendedores</a></li>
                    </ul>
                </div>
                <div className="footer-column">
                    <h3>Empresa</h3>
                    <ul>
                        <li><a href="#">Sobre nós</a></li>
                        <li><a href="#">Trabalho</a></li>
                        <li><a href="#">Mídia</a></li>
                    </ul>
                </div>
                <div className="footer-column">
                    <h3>Legal</h3>
                    <ul>
                        <li><a href="#">Termos de serviço</a></li>
                        <li><a href="#">Política de privacidade</a></li>
                        <li><a href="#">Envio de direitos autorais</a></li>
                        <li><a href="#">Guia de remoção</a></li>
                    </ul>
                </div>
            </div>
        </footer>
    );
};

export default Footer;

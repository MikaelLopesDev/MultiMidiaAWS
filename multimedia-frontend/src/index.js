import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.css'; // Certifique-se de que o CSS global está sendo importado

ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById('root')
);

# Projeto de Upload de Multimídia
Este projeto é uma página web para uma bibliteca multimídia (fotos, músicas e vídeos), construída com Java usando Springboot, JavaScript usando react, HTML e CSS e integrada na AWS para armazenamento e processamento de arquivos.

- [Introdução](#introduçao)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalaçao)
- [Configuração da AWS](#configuraçao-da-aws)

## Introdução
Este projeto permite que os usuários façam upload e gerenciem de arquivos multimídia para um servidor web. Os arquivos são armazenados na AWS EC2 e podem ser acessados e gerenciados pelo usuário. O objetivo é demonstrar como integrar uma aplicação web com serviços de nuvem da AWS.

## Tecnologias Utilizadas
### Frontend
- **Linguagem de Programação:** JavaScript
- **Framework:** React
- **Gerenciador de Pacotes:** npm (Node Package Manager)
- **CSS:** Estilização customizada utilizando arquivos CSS

### Backend e API
- **Linguagem de Programação:** Java
- **Framework:** Spring Boot
- **Gerenciamento de Dependências:** Maven
- **Banco de Dados:** H2 (para desenvolvimento e testes) ou qualquer outro banco de dados configurado na aplicação


## Instalação
**Clone o repositório**:
Copiar código
```
git clone https://github.com/MikaelLopesDev/MultiMidiaAWS/tree/main
cd MultiMidiaAWS
```
### Instalação Frotend
1. **Instale as dependências do frontend**:
```sh
npm install
```

2. **Inicie o servidor de desenvolvimento**:
```sh
npm start
```

3. **Acesse a aplicação**:
Abra o navegador e vá para `http://localhost:3000`

### Instalação Backend e API

1. **Configure o banco de dados**:
- Verifique as configurações em `src/main/resources/application.properties`.

2. **Construa o projeto**:
```sh
./mvnw clean install
```

3. **Execute a aplicação**:
```sh
./mvnw spring-boot:run
```

4. **Acesse a aplicação**:
- A aplicação estará disponível em `http://3.239.184.73:8080`.



## Configuração da AWS
### Configurando a Instância EC2
#### Criar uma Instância EC2:
Acesse o console da AWS e vá para a seção EC2.
Clique em "Launch Instance".
Escolha uma AMI (Amazon Machine Image) apropriada, como Amazon Linux 2 AMI.
Selecione o tipo de instância desejado (por exemplo, t2.micro).
Configure as opções de instância, incluindo o armazenamento.
Configure o grupo de segurança para permitir o tráfego HTTP e HTTPS (portas 80 e 443) e SSH (porta 22).
Revise e inicie a instância.
#### Conectar à Instância EC2:

Use SSH para conectar-se à sua instância. Por exemplo:
```
ssh -i /path/to/your-key-pair.pem ec2-user@your-ec2-public-dns
```

Configurar o Ambiente:
Atualize os pacotes instalados na instância:
```
sudo yum update -y
```
Instale o Java:
```
sudo amazon-linux-extras install java-openjdk11 -y
```
Instale o Node.js e npm:
```
curl -sL https://rpm.nodesource.com/setup_14.x | sudo bash -
sudo yum install -y nodejs
```
Configurar o Servidor:

- Faça o upload do seu código para a instância EC2 usando SCP ou Git.
- Navegue até o diretório do projeto e inicie o backend e o frontend conforme descrito na seção de Instalação.

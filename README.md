# Projeto de Upload de Multimídia
Este projeto é uma página web para uma bibliteca multimídia (fotos, músicas e vídeos), construída com Java usando Springboot, JavaScript usando react, HTML e CSS e integrada na AWS para armazenamento e processamento de arquivos.

- [Introdução](#introdução)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Configuração da AWS](#configuração-da-aws)
- [Documentação e Funcionalidades](#documentação-e-funcionalidades)

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

## Documentação e Funcionalidades

### Funcionalidades

#### Gerenciamento de Usuário
Criação de contas de usuário.
Edição e atualização de informações de perfil. (Somente no backend por enquanto)

#### Autenticação
Login de usuário.
Logout de usuário.

#### Dashboard do Usuário
Exibição de informações relevantes para o usuário logado.
Acesso a funcionalidades específicas baseadas no perfil do usuário.

#### Upload e Armazenamento de Arquivos
Fazer upload de arquivos multi midia. (video, foto, audio)
Obter todos os seus arquivos salvos na AWS vinculados a sua conta.

#### Reprodução de Arquivos
Reproduzir arquivos de vídeo e áudio diretamente pela aplicação web.
Alterar a velocidade da reprodução do vídeo.

### Documentação da API

Este repositório contém uma API desenvolvida para gerenciar arquivos de mídia (imagens, áudios e vídeos) hospedados na AWS. Abaixo, você encontrará detalhes sobre as rotas disponíveis, métodos suportados e exemplos de retorno.

#### Endpoints

#### 1. Imagens
   
#### Listar todas as imagens

    Método: GET
    URL: {AWS}/images
    Corpo: Nenhum
    
Exemplo de Retorno:
```
[
    {
        "id": 1,
        "fileName": "chrono-trigger-cover-art.jpg",
        "fileSize": 62024,
        "uploadDate": "2024-08-03T12:34:56",
        "tags": "game",
        "height": 423,
        "width": 564,
        "colorDepth": 24,
        "exif": null,
        "description": "wallpaper chrono trigger",
        "mime": "JPEG"
    },
    ...
]
```

#### Obter imagem por ID

    Método: GET
    URL: {AWS}/images/{id}
    Corpo: Nenhum

Exemplo de Retorno:
```
{
    "id": 1,
    "fileName": "chrono-trigger-cover-art.jpg",
    "fileSize": 62024,
    "uploadDate": "2024-08-03T12:34:56",
    "tags": "game",
    "height": 423,
    "width": 564,
    "colorDepth": 24,
    "exif": null,
    "description": "wallpaper chrono trigger",
    "mime": "JPEG"
}
```

#### Criar nova imagem

    Método: POST
    URL: {AWS}/images

Corpo:
```
{
    "ownerId": 1,
    "uploadDate": "2024-08-03T12:34:56",
    "fileName": "festa-de-aniversario.jpg",
    "description": "Foto da minha festa de aniversário",
    "tags": "eu, aniversário, festa, irmãs"
}
```

#### Editar imagem existente

    Método: PUT
    URL: {AWS}/images/{id}

Corpo:
```
{
    "ownerId": 1,
    "uploadDate": "2024-08-03T12:34:56",
    "fileName": "festa-de-aniversario.jpg",
    "description": "Foto da minha festa de aniversário",
    "tags": "eu, aniversário, festa, irmãs"
}
```

Exemplo de Retorno:

```
{
    "id": 1,
    "fileName": "festa-de-aniversario.jpg",
    "fileSize": 62024,
    "uploadDate": "2024-08-03T12:34:56",
    "tags": "eu, aniversário, festa, irmãs",
    "height": 423,
    "width": 564,
    "colorDepth": 24,
    "exif": null,
    "description": "Foto da minha festa de aniversário",
    "mime": "JPEG"
}
```

#### Deletar imagem

    Método: DELETE
    URL: {AWS}/images/{id}
    Corpo: Nenhum
    Exemplo de Retorno: 204 No Content

#### Baixar imagem

    Método: POST
    URL: {AWS}/images/data
    Exemplo de Retorno: A imagem solicitada para download.
    
Corpo:
```
{
    "ownerId": 1,
    "fileName": "WhatsApp Image 2024-08-07 at 20.15.26.jpeg"
}
```

#### Listar imagens de um usuário

    Método: GET
    URL: {AWS}/users/login/name
    Corpo: Nenhum
    
Exemplo de Retorno:
```
[
    {
        "id": 1,
        "fileName": "chrono-trigger-cover-art.jpg",
        "fileSize": 62024,
        "uploadDate": "2024-08-03T12:34:56",
        "tags": "game",
        "height": 423,
        "width": 564,
        "colorDepth": 24,
        "exif": null,
        "description": "wallpaper chrono trigger",
        "mime": "JPEG"
    },
    ...
]
```

#### Áudios e Vídeos

Os endpoints para áudios e vídeos são similares aos de imagens, com a diferença de que eles possuem o campo adicional genre, que é um enum com as seguintes opções:

    MUSIC
    PODCAST
    AUDIO_BOOK
    SOUNDEFFECT
    OTHER
    FILM
    TV_SERIES
    TUTORIAL
    INTERVIEW

#### Usuários
   
#### Criar usuário

    Método: POST
    URL: {AWS}/users
    
Corpo:
```
{
    "username": "string",
    "email": "string",
    "password": "string"
}
```

#### Editar usuário

    Método: PUT
    URL: {AWS}/users/{id}
    
Corpo:
```
{
    "username": "string",
    "email": "string",
    "password": "string"
}
```

#### Deletar usuário

    Método: DELETE
    URL: {AWS}/users/{id}
    Corpo: Nenhum
    Exemplo de Retorno: 204 No Content

#### Observações

Substitua {AWS} pela URL base da sua API.
Para áudios e vídeos, o campo genre deve ser especificado de acordo com o tipo de mídia.

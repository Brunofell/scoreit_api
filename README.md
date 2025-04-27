# GUIA DE USO DA API SCOREIT

## CONFIGURAÇÃO DO BANCO DE DADOS

- A API utiliza MySQL conforme solicitado
- Passos para configuração:
    1. Instale ou abra o MySQL em seu ambiente
    2. Execute o comando SQL: `CREATE DATABASE scoreit`
    3. A API criará automaticamente as tabelas necessárias através do Hibernate quando executada

## CONFIGURAÇÃO DA API SPRING BOOT

1. Clone o repositório
2. Abra o projeto no IntelliJ IDEA (recomendado), Eclipse ou VS Code
3. Execute o arquivo `ScoreitApplication.java`
4. Aguarde a inicialização do servidor (porta padrão: 8080)

## TESTANDO AS REQUISIÇÕES HTTP

Para testar a API, utilize uma ferramenta de requisições HTTP como:
- Postman (recomendado)
- Insomnia
- API Dog

### Fluxo de autenticação:
1. Cadastre um novo usuário
2. Faça login para receber o token JWT
3. Utilize o token nas demais requisições através do cabeçalho "Authorization" (Bearer Token)

## ENDPOINTS DISPONÍVEIS

### Usuários

| Método | Endpoint                                                     | Autenticação | Descrição                                                           |
|--------|--------------------------------------------------------------|--------------|---------------------------------------------------------------------|
| POST   | `/member/post`                                               | Não | Cadastrar novo usuário                                              |
| POST   | `/member/login`                                              | Não | Autenticar usuário e obter token JWT                                |
| GET    | `/member/get`                                                | Sim | Obter dados do usuário logado                                       |
| PUT    | `/member/update`                                             | Sim | Atualizar dados do usuário                                          |
| POST   | `/api/change-email?email={EMAIL_ATUAL}`                      | Sim | Solicitar alteração de email                                        |
| POST   | `/api/reset-email?token={TOKEN}&newEmail={NOVO_EMAIL}`       | Não | Confirmar alteração de email                                        |
| DELETE | `/member/delete/{id}`                                        | Sim | Excluir conta de usuário                                            |
| POST   | `/api/forgot-password?email={EMAIL}`                         | Não | Solicitar redefinição de senha                                      |
| POST   | `/api/reset-password?token={TOKEN}&newPassword={NOVA_SENHA}` | Não | Confirmar nova senha                                                |
| POST   | `/api/images/upload/{USER_ID}`                               | Sim | Enviar foto de perfil                                               |
| POST   | `/member/favorites/{USER_ID}/{MEDIA_ID}/{MEDIA_TYPE}`        | Sim | Adicionar item aos favoritos                                        |
| GET    | `/member/favoritesList/{USER_ID}`                            | Sim | Obter lista de favoritos                                            |
| GET    | `/member/favoritesListContent/{USER_ID}`                     | Sim | Obter conteúdo da lista de favoritos                                |
| DELETE | `/member/favoritesDelete/{USER_ID}/{MEDIA_ID}/{MEDIA_TYPE}`  | Sim | Remover item dos favoritos                                          |
| GET    | `/member/is-favorited?memberId={USER_ID}&mediaId={MEDIA_ID}` | Sim | Verifica se uma obra específica está favoritada pelo usuário logado |

### Filmes

| Método | Endpoint                         | Autenticação | Descrição                                        |
|--------|----------------------------------|--------------|--------------------------------------------------|
| GET | `/movie/id/{id}`                 | Sim | GET do filme pelo id dele                        |
| GET | `/movie/get/page/{PAGE}`         | Sim | Listar filmes por popularidade                   |
| GET | `/movie/now/{PAGE}`              | Sim | Filmes em cartaz atualmente                      |
| GET | `/movie/upcoming/{PAGE}`         | Sim | Próximos lançamentos                             |
| GET | `/movie/media/{MOVIE_ID}`        | Sim | Obter mídias de um filme específico              |
| GET | `/movie/several?ids=id,id,id...` | Sim | Retorna mais de um filme por uma lista de ids    |
| GET | `/movie/favorites/{USER_ID}`     | Sim | Retorna os dados dos filmes favoritos do usuário |

### Séries

| Método | Endpoint | Autenticação | Descrição                                        |
|--------|----------|--------------|--------------------------------------------------|
| GET | `/series/get/{SERIES_ID}` | Sim | Buscar série por ID                              |
| GET | `/series/now/{PAGE}` | Sim | Séries em exibição atualmente                    |
| GET | `/series/get/page/{PAGE}` | Sim | Listar séries por página                         |
| GET | `/series/year/{ANO}/page/{PAGE}` | Sim | Séries populares por ano                         |
| GET | `/series/genre/{GENRE_ID}/page/{PAGE}` | Sim | Séries por gênero                                |
| GET | `/series/{SERIES_ID}/season/{SEASON_NUMBER}` | Sim | Detalhes de temporada específica                 |
| GET | `/series/several?ids=id,id,id...` | Sim | Retorna mais de uma série por uma lista de ids   |
| GET | `/series/favorites/{USER_ID}`| Sim | Retorna os dados das series favoritas do usuário |


### Músicas(Album)

| Método | Endpoint                                                                                                | Autenticação | Descrição                                                     |
|--------|---------------------------------------------------------------------------------------------------------|--------------|---------------------------------------------------------------|
| GET | `/spotify/api/newAlbumReleases?country=US&limit=<quantos vem por requisição>&offset=<pagina>&sort=desc` | Sim | Novos lançamentos (álbuns e EPs)                              |
| GET | `/lastfm/top-artists?page={PAGE}&limit={LIMIT}`                                                         | Sim | Artistas mais populares globalmente                           |
| GET | `/lastfm/albums-by-genre/pop?page={PAGE}&limit={LIMIT}`                                                 | Sim | RETORNA ALBUM POR GENERO MUSICAL (USA ESSE)                   |
| GET | `/spotify/api/album/{id do album}`                                                                      | Sim | Retorna album por id                                          |
| GET | `/spotify/api/albums?ids=<ID_DA_OBRA>,<ID_DA_OBRA>,<ID_DA_OBRA>,<ID_DA_OBRA>`                           | Sim | Retorna mais de um album por requisição, separe por vírgula   |
| GET | `/spotify/api/artists?ids=<ID_DO_ARTISTA>,<ID_DO_ARTISTA>,<ID_DO_ARTISTA>`                              | Sim | Retorna mais de um Artista por requisição, separe por vírgula |
| GET | `/spotify/api/favorites/{USER_ID}`                                                                      | Sim | Retorna os dados dos albums favoritos do usuário              |

## EXEMPLOS DE REQUISIÇÕES

### Cadastro de usuário
```json
POST /member/post
{
    "name": "Bruno Oliveira Faria Luz",
    "birthDate": "2000-05-20",
    "gender": "MASC",
    "email": "exemplo@email.com",
    "password": "senhaforte"
}
```

### Login
```json
POST /member/login
{
    "email": "exemplo@email.com",
    "password": "senhaforte"
}
```

### Atualização de perfil
```json
PUT /member/update
{
    "id": 5,
    "name": "Bruno Oliveira Faria Luz",
    "birthDate": "2004-06-24",
    "gender": "FEM",
    "password": "senha123",
    "bio": "Gosto de Rap | Rock | Pagode | Ação | Drama | Comédia"
}
```

## OBSERVAÇÕES IMPORTANTES

- O token JWT é necessário para todas as requisições exceto cadastro, login e redefinição de senha/email
- Após login bem-sucedido, armazene o token retornado para utilizar nas demais requisições
- A lista de favoritos é criada automaticamente durante o cadastro do usuário
- Para uploads de imagem e operações com arquivos, utilize requisições multipart/form-data
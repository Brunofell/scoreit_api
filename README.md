# PASSO A PASSO PARA USAR A API

## BANCO DE DADOS:

- Usei MySQL a pedido de uns ai
- Abre o Mysql de vcs ai ou instala
- Depois vocês vão usar esse comando sql `CREATE DATABASE scoreit`
- Dai é só rodar a api que ela já cria as tabelas pelo Hibernate

## API SPRING BOOT:

- Vocês vão dar clone do repositório e vão abrir no Intellij (ou no eclipse ou vscode, mas vscode não presta pra java não)
- Dai vocês vão dar run nesse arquivo: `ScoreitApplication`
- Daí pronto é pra rodar

## REQUISIÇÕES HTTP:

- Pra usar a API, vocês vão ter que usar uma plataforma para testar as requisições e ver como elas funcionam
- Dai tem o API Dog, Insomnia, mas eu uso o Postman que é melhor
- No Postman ou sla qual vcs vão usar, primeiramente vão ter que fazer a requisição do cadastro para criar um usuário para usar
- Depois vão ter que fazer o login com uma requisição
- Quando vcs fizerem login, ele vai retornar no corpo da resposta o token JWT
- Vocês vão precisar do token para fazer as outras requisições que não são do cadastro, isso serve para ter mais segurança
- Dai toda vez que vocês forem fazer uma requisição para resgatar filmes e tal, vão ter que colocar o token retornado na opção bearer token, dai vão conseguir fazer a requisição com sucesso

# METODOS HTTP:

- Usuário(tem que passar o token nesses, menos no cadastro e login - o login retorna o token) :
  - http://localhost:8080/member/post
  `
    {
    "name": "Bruno Oliveira Faria Luz",
    "email": "scoreit@gmail.com",
    "password": "senhaforte"
    }
`
  - http://localhost:8080/member/login
  `
  {
    "email": "scoreit@gmail.com",
    "password": "senhaforte"
}
`
  - http://localhost:8080/member/get
  - http://localhost:8080/member/update
`
    {
    "id": 1,
    "name": "Bruno Oliveira Faria Luz",
    "email": "scoreit@gmail.com",
    "password": "senha123",
    "bio": "Gosto de Rap | Rock | Pagode | Terror | Drama | Comédia"
    }
`
  - http://localhost:8080/member/delete/{id} - coloca um número de id

- FILMES (tem que passar o token nesses):
  - http://localhost:8080/movie/get/page/{page} - Retorna todos os filmes por popularidade, coloca um número ali no page, tipo 1
  - http://localhost:8080/movie/now/1 - retorna o que está passando no momento
  
- SÉRIES (tem que passar o token nesses):
  - http://localhost:8080/series/get/{id}  - coloca um número de id
  - http://localhost:8080/series/now/{page} - retorna séries que estão no ar, coloca um número ali no page, tipo 1



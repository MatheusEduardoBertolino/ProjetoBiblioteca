# Projeto Biblioteca: API Spring Boot (Parte 3)

Spring Boot 4.1 · Java 17+ · Spring Data JPA · SQLite

## Como rodar

No IntelliJ, abra a pasta `backend` (a que tem o `pom.xml`) e execute `BibliotecaApplication`.
Pelo terminal, dentro da pasta `backend`:

```bash
./mvnw spring-boot:run       # Windows: mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8081`.

## O banco

`data/biblioteca.sqlite` já vem pronto, com tudo das Partes 1 e 2:
categoria, editora, livro, leitor, emprestimo, log_auditoria, os 5 triggers e a view
vw_situacao_emprestimo.

Toda inclusão é feita pela API. Não abra o arquivo em outro programa enquanto a API estiver rodando.
Para recomeçar do zero, pare a API e substitua `data/biblioteca.sqlite` pela cópia original.

## Camadas

```
controller/   recebe a requisição HTTP e devolve JSON
service/      regras de negócio (RN01 a RN06)
repository/   consultas JPA (Spring Data)
model/        uma classe por tabela; SituacaoEmprestimo mapeia a view
dto/          formatos de entrada e saída da API
exception/    respostas de erro padronizadas
```

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| GET | `/api/categorias`, `/api/editoras` | acervo |
| GET | `/api/livros?idCategoria=&titulo=` | livros com categoria e editora |
| GET | `/api/livros/{id}` | um livro |
| GET | `/api/leitores?tipo=` | leitores |
| GET | `/api/leitores/{id}` e `/api/leitores/{id}/emprestimos` | leitor e histórico |
| POST | `/api/leitores` | inclui leitor (RN01, RN02) |
| GET | `/api/emprestimos?situacao=` | situação de cada empréstimo (view) |
| GET | `/api/emprestimos/{id}` | um empréstimo (view) |
| POST | `/api/emprestimos` | inclui empréstimo (RN03 a RN06) |
| PATCH | `/api/emprestimos/{id}/devolucao` | registra a devolução |
| GET | `/api/relatorios/atrasados` | atrasados, maior atraso primeiro |
| GET | `/api/relatorios/nunca-emprestados` | livros que nunca saíram |
| GET | `/api/relatorios/por-categoria` | total de empréstimos por categoria |
| GET | `/api/relatorios/disponibilidade` | exemplares disponíveis por livro |
| GET | `/api/auditoria` | linhas gravadas pelos triggers |

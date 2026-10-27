-- Testa se o email não pode ser duplicado
INSERT INTO leitor
    (nome, email, tipo, data_cadastro)
VALUES
    ('Teste Email', 'ana.silva@biblioteca.edu.br', 'aluno', '2026-11-16');

-- testa se o tipo de leitor é válido.
INSERT INTO leitor
    (nome, email, tipo, data_cadastro)
VALUES
    ('Teste Tipo', 'teste.tipo@biblioteca.edu.br', 'visitante', '2026-11-16');

-- Verifica dados na tabela emprestimo

SELECT COUNT(*) AS total_emprestimos
FROM emprestimo;

-- Verifica Emprestimos em aberto
SELECT
    e.id_emprestimo,
    l.nome AS leitor,
    li.titulo AS livro,
    e.data_emprestimo,
    e.data_prevista_devolucao
FROM emprestimo e
INNER JOIN leitor l
    ON e.id_leitor = l.id_leitor
INNER JOIN livro li
    ON e.id_livro = li.id_livro
WHERE e.data_devolucao IS NULL
ORDER BY e.data_prevista_devolucao;

-- Verifica emprestimos atrasados

SELECT
    e.id_emprestimo,
    l.nome AS leitor,
    li.titulo AS livro,
    e.data_emprestimo,
    e.data_prevista_devolucao,
    CAST(
        julianday('2026-11-16') -
        julianday(e.data_prevista_devolucao)
        AS INTEGER
    ) AS dias_atraso
FROM emprestimo e
INNER JOIN leitor l
    ON e.id_leitor = l.id_leitor
INNER JOIN livro li
    ON e.id_livro = li.id_livro
WHERE e.data_devolucao IS NULL
  AND e.data_prevista_devolucao < '2026-11-16'
ORDER BY dias_atraso DESC;

--Verifica os livros que nunca foram emprestados

SELECT
    li.id_livro,
    li.titulo
FROM livro li
LEFT JOIN emprestimo e
    ON li.id_livro = e.id_livro
WHERE e.id_emprestimo IS NULL
ORDER BY li.titulo;

--Total de emprestimo por categoria
SELECT
    c.id_categoria,
    c.nome AS categoria,
    COUNT(e.id_emprestimo) AS total_emprestimos
FROM categoria c
LEFT JOIN livro li
    ON c.id_categoria = li.id_categoria
LEFT JOIN emprestimo e
    ON li.id_livro = e.id_livro
GROUP BY
    c.id_categoria,
    c.nome
ORDER BY total_emprestimos DESC;

--Verifica os leitores com mais de 3 emprestimo em aberto


SELECT
    l.id_leitor,
    l.nome AS leitor,
    COUNT(e.id_emprestimo) AS emprestimos_abertos
FROM leitor l
INNER JOIN emprestimo e
    ON l.id_leitor = e.id_leitor
WHERE e.data_devolucao IS NULL
GROUP BY
    l.id_leitor,
    l.nome
HAVING COUNT(e.id_emprestimo) > 3
ORDER BY emprestimos_abertos DESC;

-- Mostra os exemplares disponiveis por livro

SELECT
    li.id_livro,
    li.titulo,
    li.qtd_exemplares,
    COUNT(e.id_emprestimo) AS exemplares_emprestados,
    li.qtd_exemplares - COUNT(e.id_emprestimo) AS exemplares_disponiveis
FROM livro li
LEFT JOIN emprestimo e
    ON li.id_livro = e.id_livro
    AND e.data_devolucao IS NULL
GROUP BY
    li.id_livro,
    li.titulo,
    li.qtd_exemplares
ORDER BY li.id_livro;	

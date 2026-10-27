CREATE TABLE emprestimo (
    id_emprestimo INTEGER PRIMARY KEY,
    id_livro INTEGER NOT NULL,
    id_leitor INTEGER NOT NULL,
    data_emprestimo TEXT NOT NULL,
    data_prevista_devolucao TEXT NOT NULL,
    data_devolucao TEXT,

    FOREIGN KEY (id_livro)
        REFERENCES livro(id_livro),

    FOREIGN KEY (id_leitor)
        REFERENCES leitor(id_leitor),

    CHECK (data_prevista_devolucao >= data_emprestimo),

    CHECK (
        data_devolucao IS NULL
        OR data_devolucao >= data_emprestimo
    )
);
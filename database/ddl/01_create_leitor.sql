PRAGMA FOREIGN_keys = ON;

CREATE TABLE leitor(
	id_leitor INTEGER PRIMARY KEY,
	nome TEXT NOT NULL,
	email TEXT NOT NULL UNIQUE,
	tipo TEXT NOT NULL
		CHECK (tipo IN ('aluno', 'professor','funcionario')),
		data_cadastro TEXT NOT NULL
);
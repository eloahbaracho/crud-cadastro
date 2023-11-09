CREATE TABLE transacoes (
	idTransacao TEXT PRIMARY KEY UNIQUE NOT NULL,
	dataTransacao DATE NOT NULL,
	valorTransacao DOUBLE NOT NULL,
	emissor TEXT,
	receptor TEXT,
	FOREIGN KEY (emissor) REFERENCES contacorrente (idConta),
	FOREIGN KEY (receptor) REFERENCES contacorrente (idConta)
);


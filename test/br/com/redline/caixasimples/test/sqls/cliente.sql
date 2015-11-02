USE Loja;
DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
	idCliente		INT				NOT NULL auto_increment,
    nome			VARCHAR(45) 	NOT NULL,
    sobrenome		VARCHAR(45) 	NOT NULL,
    rua				VARCHAR(45)		NOT NULL,
    numero			INT				NOT NULL,
    bairro			VARCHAR(45)		NOT NULL,
    telefone		VARCHAR(15) 	NOT NULL,
    email			VARCHAR(30) 	NOT NULL,
	CONSTRAINT pk_id_cliente PRIMARY KEY (idCliente)
);

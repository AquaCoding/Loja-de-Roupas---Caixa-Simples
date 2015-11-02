USE Loja;
DROP TABLE IF EXISTS produto;
CREATE TABLE produto (
	idProduto		INT				NOT NULL auto_increment,
    nomeProduto		VARCHAR(45)		NOT NULL,
	codigoBarras	VARCHAR(20)		NOT NULL,
    descricao		VARCHAR(200)	NOT NULL,
    qtd				INT				NOT NULL,
    precoVenda		DOUBLE			NOT NULL,
    CONSTRAINT pk_id_produto PRIMARY KEY (idProduto)
);
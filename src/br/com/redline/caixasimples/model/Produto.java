package br.com.redline.caixasimples.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.redline.caixasimples.util.DatabaseConnect;

import com.mysql.jdbc.PreparedStatement;

public class Produto {
	private int idProduto;
	private String nomeProduto;
	private String codigoBarras;
	private String descricao;
	private String fornecedor;
	private int qtd;
	private BigDecimal precoVenda, precoCusto;

	// Gets
	public int getIdProduto() {
		return idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQtd() {
		return qtd;
	}

	public String getPrecoVenda() {
		return precoVenda.toString();
	}

	// Sets
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		if (!nomeProduto.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de nome é inválido");
		this.nomeProduto = nomeProduto;
	}

	public void setCodigoBarras(String codigoBarras) {
		if(codigoBarras.length() != 13)
			throw new RuntimeException("Um código de barras (EAN-13) deve ter 13 caracteres");
		this.codigoBarras = codigoBarras;
	}

	public void setDescricao(String descricao) {
		if (descricao.length() > 200)
			throw new RuntimeException("O valor de descrição é muito grande");
		this.descricao = descricao;
	}

	public void setQtd(int qtd) {
		if (qtd < 0)
			throw new RuntimeException("O valor de quantidade é inválido");
		this.qtd = qtd;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		if(precoVenda.signum() == -1)
			throw new RuntimeException("O valor de preço de venda é inválido");
		this.precoVenda = precoVenda;
	}
	
	public void setPrecoCusto(BigDecimal precoVenda) {
		if(precoVenda.signum() == -1)
			throw new RuntimeException("O valor de preço de venda é inválido");
		this.precoCusto = precoVenda;
	}
	
	public void setFornecedor(String fornecedor) {
		if (!fornecedor.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de fornecedor é inválido");
		this.fornecedor = fornecedor;
	}

	public Produto(String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda) {
		setNomeProduto(nomeProduto);
		setCodigoBarras(codigoBarras);
		setDescricao(descricao);
		setQtd(qtd);
		setPrecoVenda(precoVenda);
	}
	
	public Produto(String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda, BigDecimal custo, String forncedor) {
		setCodigoBarras(codigoBarras);
		setQtd(qtd);
		setPrecoCusto(custo);
		setFornecedor(forncedor);
		setNomeProduto(nomeProduto);
		setPrecoVenda(precoVenda);
		setDescricao(descricao);
	}

	public Produto(int idProduto, String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda) {
		setIdProduto(idProduto);
		setNomeProduto(nomeProduto);
		setCodigoBarras(codigoBarras);
		setDescricao(descricao);
		setQtd(qtd);
		setPrecoVenda(precoVenda);
	}
	
	public boolean create() {
		try {
			// Verifica codigo de barras
			if(haveCodigoBarras(this.codigoBarras)) {
				Produto p = getByCodigoBarras(codigoBarras);
				setIdProduto(p.getIdProduto());
				p.setNomeProduto(this.nomeProduto);
				p.setDescricao(this.descricao);
				p.setQtd(this.qtd);
				p.setPrecoVenda(this.precoVenda);
				
				// Obtem uma conexão com o banco de dados
				Connection connect = DatabaseConnect.getInstance();

				// Cria um prepared statement
				PreparedStatement statement = (PreparedStatement) connect
						.prepareStatement("UPDATE Produto SET ativo = true WHERE idProduto = ?");
				
				statement.setInt(1, this.idProduto);
				
				// Executa o SQL
				int ret = statement.executeUpdate();

				// Retorna resultado
				if (ret == 1)
					return p.update();
				
				throw new RuntimeException("Um erro ocorreu");
			} else {
				// Obtem uma conexão com o banco de dados
				Connection connect = DatabaseConnect.getInstance();

				// Cria um prepared statement
				PreparedStatement statement = (PreparedStatement) connect
						.prepareStatement("INSERT INTO Produto (nomeProduto, codigoBarras, descricao, qtd, precoVenda) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

				// Realiza o bind dos valores
				statement.setString(1, this.nomeProduto);
				statement.setString(2, this.codigoBarras);
				statement.setString(3, this.descricao);
				statement.setInt(4, this.qtd);
				statement.setBigDecimal(5, this.precoVenda);
				
				// Executa o SQL
				int ret = statement.executeUpdate();

				// Retorna resultado
				if (ret == 1) {
					//Define o id a classe
					ResultSet id = statement.getGeneratedKeys();
					while(id.next())
						setIdProduto(id.getInt(1));
					
					// Encerra conexao
					connect.close();
					return true;
				} else {
					// Encerra conexao
					connect.close();
					return false;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu ao criar o produto");
		}
	}
	
	private Boolean haveCodigoBarras(String codigoBarras) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE codigoBarras = ?");
			
			statement.setString(1, codigoBarras);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public static Produto getByCodigoBarras(String codigoBarras) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE codigoBarras = ?");
			
			statement.setString(1, codigoBarras);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Produto p = new Produto(resultSet.getInt("idProduto"), 
						resultSet.getString("nomeProduto"), 
						resultSet.getString("codigoBarras"),
						resultSet.getString("descricao"), 
						resultSet.getInt("qtd"), 
						resultSet.getBigDecimal("precoVenda"));
				
				return p;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}

	public static ArrayList<Produto> getAll() {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			Statement statement = connect.createStatement();
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Produto WHERE ativo = true");
			
			ArrayList<Produto> produtoReturn = new ArrayList<Produto>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Produto p = new Produto(resultSet.getInt("idProduto"), 
										resultSet.getString("nomeProduto"), 
										resultSet.getString("codigoBarras"),
										resultSet.getString("descricao"), 
										resultSet.getInt("qtd"), 
										resultSet.getBigDecimal("precoVenda"));
				
				// Adiciona o cliente ao retorno
				produtoReturn.add(p);
			}
			
			// Retorna os clientes
			return produtoReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public static ArrayList<Produto> getAllWithFilters(String filter) {
		try{
			filter = "%" + filter + "%";
			
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE "
					+ "codigoBarras LIKE ? OR "
					+ "nomeProduto LIKE ? AND ativo = true");
			
			statement.setString(1, "%"+ filter +"%");
			statement.setString(2, "%"+ filter +"%");
			
			// Executa SQL
			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<Produto> produtoReturn = new ArrayList<Produto>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Produto p = new Produto(resultSet.getInt("idProduto"), 
										resultSet.getString("nomeProduto"), 
										resultSet.getString("codigoBarras"),
										resultSet.getString("descricao"), 
										resultSet.getInt("qtd"), 
										resultSet.getBigDecimal("precoVenda"));
				
				// Adiciona o cliente ao retorno
				produtoReturn.add(p);
			}
			
			// Retorna os clientes
			return produtoReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public boolean update() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("UPDATE Produto SET nomeProduto = ?, codigoBarras = ?, descricao = ?, qtd = ?, precoVenda = ? WHERE idProduto = ?");

			// Realiza o bind dos valores
			statement.setString(1, this.nomeProduto);
			statement.setString(2, this.codigoBarras);
			statement.setString(3, this.descricao);
			statement.setInt(4, this.qtd);
			statement.setBigDecimal(5, this.precoVenda);
			statement.setInt(6, this.idProduto);

			// Executa o SQL
			int ret = statement.executeUpdate();

			// Encerra conexao
			connect.close();

			// Retorna resultado
			if (ret == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Um erro ocorreu ao atualizar o produto");
		}
	}
	
	public boolean delete() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("UPDATE Produto SET ativo = false WHERE idProduto = ?");

			// Realiza o bind dos valores
			statement.setInt(1, this.idProduto);

			// Executa o SQL
			int ret = statement.executeUpdate();

			

			// Retorna resultado
			if (ret == 1) {
				// Deleta as entradas do produto
				statement = (PreparedStatement) connect
						.prepareStatement("DELETE FROM EntradaEstoque WHERE idProduto = ?");
				
				// Realiza o bind dos valores
				statement.setInt(1, this.idProduto);

				// Executa o SQL
				ret = statement.executeUpdate();
				
				// Encerra conexao
				connect.close();
				
				if (ret == 1)
					return true;
				
				return false;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Um erro ocorreu ao deletar o produto");
		}
	}
	
	public static boolean vender(ArrayList<Produto> venda) throws SQLException {
		// Obtem uma conexão com o banco de dados
		Connection connect = DatabaseConnect.getInstance();
		
		// Realiza a transaction de todos os itens vendas
		try {
			// Ativa a transaction
			connect.setAutoCommit(false);
			
			// Cria o registro da venda
			// Cria um prepared statement
			PreparedStatement statement;
					
			// Cria o registro da venda
			statement = (PreparedStatement) connect
					.prepareStatement("INSERT INTO Venda (dataVenda) VALUES (NOW())", Statement.RETURN_GENERATED_KEYS);
			
			// Executa o SQL
			int ret = statement.executeUpdate();
			ResultSet idReturn = statement.getGeneratedKeys();
			
			if (ret == 1 && idReturn.next()) {
				// Obtem o id da venda
				int id = idReturn.getInt(1);
				
				// Percorre todos os produtos, realizando a venda
				for(Produto p : venda) {
					if(!Produto.registraVenda(p, connect, id)){
						throw new RuntimeException("Um erro ocorreu ao realizar a venda");
					}
				}
			} else {
				throw new RuntimeException();
			}
			
			// Finaliza a transacition
			connect.commit();
						
			// Retorna sucesso
			return true;
		} catch (SQLException e) {
			// Desfaz as modificações no BD
			connect.rollback();
						
			// Retorna erro
			throw new RuntimeException("Um erro ocorreu ao realizar a venda");
		} finally {
			// Encerra conexão com BD
			connect.close();
		}
	}
	
	private static boolean registraVenda(Produto itemVenda, Connection connect, int idVenda) {
		try {			
			// Cria um prepared statement
			PreparedStatement statement;
						
			// Registra um item venda
			statement = (PreparedStatement) connect.prepareStatement("INSERT INTO VendaItem (idVenda, idProduto, qtd, precoVenda) VALUES (?, ?, ?, ?)");
			statement.setInt(1, idVenda);
			statement.setInt(2, itemVenda.getIdProduto());
			statement.setInt(3, itemVenda.getQtd());
			statement.setBigDecimal(4, new BigDecimal(itemVenda.getPrecoVenda()));
			
			// Executa o registro do item venda
			int ret = statement.executeUpdate();
			
			// Verifica o resultado
			if(ret == 1) {
				// Atualiza estoque e retorna o resultado
				return baixaEstoque(itemVenda, connect);
			} else {
				// Erro em item venda
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Um erro ocorreu a realizar uma venda");
		}
	}
	
	public static boolean baixaEstoque(Produto itemVenda, Connection connect) {
		Produto p = Produto.getByCodigoBarras(itemVenda.getCodigoBarras());

		if(p.getQtd() >= itemVenda.getQtd()) {
			// Registra um item venda
			try {
				// Cria um prepared statement
				PreparedStatement statement;
				statement = (PreparedStatement) connect.prepareStatement("UPDATE Produto SET qtd = ? WHERE idProduto = ?");
				statement.setInt(1, p.getQtd() - itemVenda.getQtd());
				statement.setInt(2, p.getIdProduto());
				
				int ret = statement.executeUpdate();
				
				// Verifica o resultado
				if(ret == 1) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				throw new RuntimeException("Um erro ocorreu a dar baixa no estoque");
			}
		} else {
			return false;
		}
	}

	public boolean createEntradaEstoque() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("INSERT INTO entradaestoque (idProduto, dataEntrada, qtd, precoEntrada, fornecedor) VALUES (?, NOW(), ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// Realiza o bind dos valores
			statement.setInt(1, this.idProduto);
			statement.setInt(2, this.qtd);
			statement.setBigDecimal(3, this.precoCusto);
			statement.setString(4, this.fornecedor);
			
			// Executa o SQL
			int ret = statement.executeUpdate();

			// Retorna resultado
			if (ret == 1) {
				//Define o id a classe
				ResultSet id = statement.getGeneratedKeys();
				while(id.next())
					setIdProduto(id.getInt(1));
				
				// Encerra conexao
				connect.close();
				return true;
			} else {
				// Encerra conexao
				connect.close();
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Um erro ocorreu ao criar a entrada de estoque");
		}
	}

	public static ArrayList<EntradaProduto> getAllEntradasByCodigoBarras(String codigoBarras) {
		try{
			ArrayList<EntradaProduto> entradaReturn = new ArrayList<EntradaProduto>();
			
			if(codigoBarras != null) {
				// Obtem uma conexão com o banco de dados
				Connection connect = DatabaseConnect.getInstance();
				
				// Cria um statement
				PreparedStatement statement = (PreparedStatement) connect
						.prepareStatement("SELECT * FROM EntradaEstoque WHERE idProduto = ?");
				
				Produto p = getByCodigoBarras(codigoBarras);
				statement.setInt(1, p.getIdProduto());
				
				// Executa query
				ResultSet resultSet = statement.executeQuery();
				
				
				while(resultSet.next()) {
					EntradaProduto e = new EntradaProduto(resultSet.getInt("idEntrada"), 
															resultSet.getInt("idProduto"), 
															resultSet.getDate("dataEntrada"), 
															resultSet.getInt("qtd"), 
															resultSet.getBigDecimal("precoEntrada"), 
															resultSet.getString("fornecedor"));
					
					// Adiciona o cliente ao retorno
					entradaReturn.add(e);
				}
			}
			
			// Retorna os clientes
			return entradaReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
}
